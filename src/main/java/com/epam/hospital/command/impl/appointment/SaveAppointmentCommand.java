package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.enums.MessageType;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.APPOINTMENTS;
import static com.epam.hospital.command.constant.Command.CREATE_APPOINTMENT;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.AppointmentUtil.createAppointment;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class SaveAppointmentCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SaveAppointmentCommand.class);
    private final AppointmentService service;

    public SaveAppointmentCommand() {
        this.service = ApplicationContext.getInstance().getAppointmentService();
    }

    public SaveAppointmentCommand(AppointmentService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        Appointment appointment = createAppointment(request);
        try {
            service.saveAppointment(user, appointment);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing create appointment command, message = {}",
                    e.getMessage());
            request.getSession().setAttribute(MESSAGE, e.getType().getErrorCode());
            request.getSession().setAttribute(IS_ERROR, true);
            return new CommandResult(getPageToRedirect(CREATE_APPOINTMENT), true);
        }
        request.getSession().setAttribute(MESSAGE, MessageType.SUCCESS_SAVE_APPOINTMENT.getMessageCode());
        return new CommandResult(getPageToRedirect(APPOINTMENTS), true);
    }
}
