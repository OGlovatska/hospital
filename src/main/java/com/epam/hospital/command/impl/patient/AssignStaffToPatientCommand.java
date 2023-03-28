package com.epam.hospital.command.impl.patient;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.enums.MessageType;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class AssignStaffToPatientCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(AssignStaffToPatientCommand.class);
    private final PatientService service;

    public AssignStaffToPatientCommand() {
        this.service = ApplicationContext.getInstance().getPatientService();
    }

    public AssignStaffToPatientCommand(PatientService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);
        int staffId = Integer.parseInt(request.getParameter(STAFF_ID));
        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        try {
            service.assignPatientToStaff(user, staffId, patientId);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing assign staff to patient command, message = {}",
                    e.getMessage());
            request.getSession().setAttribute(MESSAGE, e.getType().getErrorCode());
            request.getSession().setAttribute(IS_ERROR, true);

            return new CommandResult(getPageToRedirect(PATIENT_DETAILS,
                    getParameter(PATIENT_ID, String.valueOf(patientId)),
                    getParameter(ACTIVE_TAB, STAFF_TAB)), true);
        }

        request.getSession().setAttribute(MESSAGE, MessageType.SUCCESS_ASSIGNED_STAFF_TO_PATIENT.getMessageCode());
        return new CommandResult(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(patientId)),
                getParameter(ACTIVE_TAB, STAFF_TAB)), true);
    }
}
