package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.DIRECTION;
import static com.epam.hospital.util.RequestUtil.*;

public class AppointmentsListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentsListCommand.class);
    private final AppointmentService service;

    public AppointmentsListCommand() {
        this.service = ApplicationContext.getInstance().getAppointmentService();
    }

    public AppointmentsListCommand(AppointmentService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<AppointmentTo> appointments;
        int totalCount;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        try {
            Map<Integer, List<AppointmentTo>> appointmentsTos = service
                    .getAllAppointments(user, (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(LIMIT),
                            (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
            appointments = appointmentsTos.values().stream().findFirst().orElse(null);
            totalCount = appointmentsTos.keySet().stream().findFirst().orElse(0);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing create appointment command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
            return new CommandResult(Page.APPOINTMENTS);
        }
        setRequestAttributes(request, appointments, totalCount, paginationAttributes);
        return new CommandResult(Page.APPOINTMENTS);
    }

    private void setRequestAttributes(HttpServletRequest request, List<AppointmentTo> appointments,
                                      int totalCount, Map<String, Object> paginationAttributes) {
        request.setAttribute(APPOINTMENTS, appointments);
        setPaginationAttributes(request, totalCount, (int) paginationAttributes.get(LIMIT),
                (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(CURRENT_PAGE),
                (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
    }
}
