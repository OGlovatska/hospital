package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.enums.MessageType;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.ACTIVE_TAB;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static com.epam.hospital.util.HospitalisationUtil.createHospitalisation;

public class SaveHospitalisationCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SaveHospitalisationCommand.class);
    private final HospitalisationService service;

    public SaveHospitalisationCommand() {
        this.service = ApplicationContext.getInstance().getHospitalisationService();
    }

    public SaveHospitalisationCommand(HospitalisationService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        Hospitalisation hospitalisation = createHospitalisation(request);
        try {
            service.saveHospitalisation(user, hospitalisation);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing save hospitalisation command, message = {}",
                    e.getMessage());
            request.getSession().setAttribute(MESSAGE, e.getType().getErrorCode());
            request.getSession().setAttribute(IS_ERROR, true);

            return new CommandResult(getPageToRedirect(PATIENT_DETAILS,
                    getParameter(PATIENT_ID, String.valueOf(patientId)),
                    getParameter(ACTIVE_TAB, HOSPITALISATIONS_TAB)), true);
        }

        request.getSession().setAttribute(MESSAGE, MessageType.SUCCESS_SAVE_HOSPITALISATION.getMessageCode());
        return new CommandResult(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(patientId)),
                getParameter(ACTIVE_TAB, HOSPITALISATIONS_TAB)), true);
    }
}
