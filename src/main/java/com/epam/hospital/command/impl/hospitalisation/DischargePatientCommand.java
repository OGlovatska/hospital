package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static com.epam.hospital.command.constant.Command.PATIENTS_LIST;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class DischargePatientCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(DischargePatientCommand.class);
    private final HospitalisationService service;

    public DischargePatientCommand() {
        this.service = ApplicationContext.getInstance().getHospitalisationService();
    }

    public DischargePatientCommand(HospitalisationService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        int hospitalisationId = Integer.parseInt(request.getParameter(HOSPITALISATION_ID));
        LocalDate endDate = LocalDate.parse(request.getParameter(HOSPITALISATION_END_DATE));
        try {
            service.dischargePatient(user, hospitalisationId, endDate);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing discharge patient command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        return new CommandResult(getPageToRedirect(PATIENTS_LIST), true);
    }
}
