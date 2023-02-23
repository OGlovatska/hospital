package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Attribute;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.command.constant.Parameter;
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

    public AppointmentsListCommand(ApplicationContext applicationContext) {
        this.service = applicationContext.getAppointmentService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<AppointmentTo> appointments;
        int totalCount;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        int offset = (int) paginationAttributes.get(OFFSET);
        int limit = (int) paginationAttributes.get(LIMIT);
        String orderBy = (String) paginationAttributes.get(ORDER_BY);
        String direction = (String) paginationAttributes.get(DIRECTION);
        try {
            Map<Integer, List<AppointmentTo>> appointmentsTos = service
                    .getAllAppointments(user, offset, limit, orderBy, direction);
            appointments = appointmentsTos.values().stream().findFirst().orElse(null);
            totalCount = appointmentsTos.keySet().stream().findFirst().orElse(0);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing create appointment command, message = {}",
                    e.getType().getErrorMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
            return new CommandResult(Page.APPOINTMENTS);
        }
        request.setAttribute(APPOINTMENTS, appointments);

        setRequestAttributes(request, new Attribute(TOTAL_COUNT, totalCount), new Attribute(LIMIT, limit),
                new Attribute(OFFSET, offset), new Attribute(CURRENT_PAGE, paginationAttributes.get(CURRENT_PAGE)),
                new Attribute(ORDER_BY, orderBy), new Attribute(DIRECTION, direction),
                new Attribute(NUMBER_OF_PAGES, numberOfPages(totalCount, limit)));
        return new CommandResult(Page.APPOINTMENTS);
    }
}
