package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.UserTo;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.PdfUtil.getHospitalCardPdf;

public class ExportHospitalCardCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ExportHospitalCardCommand.class);
    private final HospitalisationService hospitalisationService;

    public ExportHospitalCardCommand() {
        this.hospitalisationService = ApplicationContext.getInstance().getHospitalisationService();
    }

    public ExportHospitalCardCommand(HospitalisationService hospitalisationService) {
        this.hospitalisationService = hospitalisationService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);
        String locale = (String) request.getSession().getAttribute(LANGUAGE);

        try {
            setResponseParameters(response, locale);

            Map<PatientTo, List<HospitalisationTo>> hospitalisations = hospitalisationService.getAllHospitalisationsWithAppointments(user);

            try (OutputStream out = response.getOutputStream()) {
                List<HospitalisationTo> hospitalisationsList = hospitalisations.values().stream().findFirst().orElse(null);
                PatientTo patient = hospitalisations.keySet().stream().findFirst().orElse(null);
                byte[] pdfDocument = getHospitalCardPdf(patient, hospitalisationsList, locale, request.getServletContext());
                out.write(pdfDocument);
            } catch (IOException | URISyntaxException | DocumentException e) {
                LOG.error("Exception has occurred during executing export hospital card command, message = {}",
                        e.getMessage());
            }
        } catch (ApplicationException | UnsupportedEncodingException e) {
            LOG.error("Exception has occurred during executing export hospital card command, message = {}",
                    e.getMessage());
        }
        return new CommandResult(Page.HOSPITALISATIONS);
    }

    private void setResponseParameters(HttpServletResponse response, String locale) throws UnsupportedEncodingException {
        String fileName = "Hospital_card";
        if (locale.equals("uk-UA")){
            fileName = "Лікарняна_карта";
        }
        response.setContentType("application/pdf");
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.displayName());
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.pdf", fileName));
    }
}