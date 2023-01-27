package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.StaffTo;
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
import static com.epam.hospital.util.PaginationUtil.getPaginationAttributes;

public class CreateAppointmentCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(CreateAppointmentCommand.class);
    private final PatientService patientService = new PatientService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final StaffService staffService = new StaffService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<PatientTo> assignedPatients = new ArrayList<>();
        List<String> appointmentTypes = new ArrayList<>();
        List<String> appointmentStatuses = new ArrayList<>();
        StaffTo staff = null;
        Map<String, String> attributes = getPaginationAttributes(request);

        try {
            staff = staffService.getStaff(user);
            assignedPatients = patientService.getAllPatientsOfStaff(staff.getId(), attributes.get(OFFSET),
                    attributes.get(LIMIT), attributes.get(ORDER_BY), attributes.get(DIRECTION));
            appointmentTypes = appointmentService.getAppointmentTypes(user);
            appointmentStatuses = appointmentService.getAppointmentStatuses();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        request.setAttribute(STAFF_ID, staff.getId());
        request.setAttribute(ASSIGNED_PATIENTS, assignedPatients);
        request.setAttribute(APPOINTMENT_TYPES, appointmentTypes);
        request.setAttribute(APPOINTMENT_STATUSES, appointmentStatuses);
        return new CommandResult(Page.ADD_APPOINTMENT);
    }
}
