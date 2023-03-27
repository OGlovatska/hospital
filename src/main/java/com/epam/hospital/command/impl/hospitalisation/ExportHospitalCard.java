package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

import static com.epam.hospital.command.constant.Command.HOSPITALISATIONS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static com.epam.hospital.util.PdfUtil.getHospitalCardPdf;

public class ExportHospitalCard implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ExportHospitalCard.class);
    private final PatientService patientService;
    private final HospitalisationService hospitalisationService;

    public ExportHospitalCard() {
        this.patientService = ApplicationContext.getInstance().getPatientService();
        this.hospitalisationService = ApplicationContext.getInstance().getHospitalisationService();
    }

    public ExportHospitalCard(PatientService patientService, HospitalisationService hospitalisationService) {
        this.patientService = patientService;
        this.hospitalisationService = hospitalisationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);
        String locale = (String) request.getSession().getAttribute(LANGUAGE);

        PatientTo patient = patientService.getPatient(user);
        List<HospitalisationTo> hospitalisations = hospitalisationService.getAllHospitalisationsWithAppointments(patient.getId());
        try(OutputStream out = response.getOutputStream()) {
            byte[] pdfDocument = getHospitalCardPdf(patient, hospitalisations, locale);
            out.write(pdfDocument != null ? pdfDocument : new byte[0]);
        } catch (IOException | URISyntaxException e) {
            LOG.error("Exception has occurred during executing export hospital card command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getMessage());
        }

        setResponseParameters(response);
        return new CommandResult(getPageToRedirect(HOSPITALISATIONS), true);
    }

    private void setResponseParameters(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");
    }
}
