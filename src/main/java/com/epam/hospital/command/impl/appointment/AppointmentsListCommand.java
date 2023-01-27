package com.epam.hospital.command.impl.appointment;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.DIRECTION;
import static com.epam.hospital.util.PaginationUtil.getPaginationAttributes;
import static com.epam.hospital.util.PaginationUtil.setPaginationAttributes;

public class AppointmentsListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentsListCommand.class);
    private final AppointmentService service = new AppointmentService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<AppointmentTo> appointments = new ArrayList<>();
        int totalCount = 0;
        Map<String, String> attributes = getPaginationAttributes(request);
        try {
            Map<Integer, List<AppointmentTo>> appointmentsTos = service.getAllAppointments(user, attributes.get(OFFSET),
                    attributes.get(LIMIT), attributes.get(ORDER_BY), attributes.get(DIRECTION));
            appointments = appointmentsTos.values().stream().findFirst().orElseThrow();
            totalCount = appointmentsTos.keySet().stream().findFirst().orElseThrow();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        request.setAttribute(APPOINTMENTS, appointments);
        setPaginationAttributes(request, totalCount, attributes.get(LIMIT), attributes.get(OFFSET),
                attributes.get(CURRENT_PAGE), attributes.get(ORDER_BY), attributes.get(DIRECTION));
        return new CommandResult(Page.APPOINTMENTS);
    }
}
