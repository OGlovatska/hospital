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

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.PATIENT_ID;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class DetermineDiagnosisCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(DetermineDiagnosisCommand.class);
    private final HospitalisationService service;

    public DetermineDiagnosisCommand() {
        this.service = ApplicationContext.getInstance().getHospitalisationService();
    }

    public DetermineDiagnosisCommand(HospitalisationService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        int hospitalisationId = Integer.parseInt(request.getParameter(HOSPITALISATION_ID));
        String diagnosis = request.getParameter(DIAGNOSIS);
        try {
            service.determinePatientDiagnosis(user, hospitalisationId, diagnosis);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing determine diagnosis command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        return new CommandResult(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(patientId))), true);
    }
}
