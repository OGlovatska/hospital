package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
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

import java.util.List;

import static com.epam.hospital.command.constant.Parameter.*;

public class CreateAppointmentCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(CreateAppointmentCommand.class);
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final StaffService staffService;

    public CreateAppointmentCommand(ApplicationContext applicationContext) {
        this.patientService = applicationContext.getPatientService();
        this.appointmentService = applicationContext.getAppointmentService();
        this.staffService = applicationContext.getStaffService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<PatientTo> assignedPatients;
        List<String> appointmentTypes;
        List<String> appointmentStatuses;
        StaffTo staff;

        try {
            staff = staffService.getStaff(user);
            assignedPatients = patientService.getAllPatientsOfStaff(staff.getId(), 0,
                    0, null, null);
            appointmentTypes = appointmentService.getAppointmentTypes(user);
            appointmentStatuses = appointmentService.getAppointmentStatuses();
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing create appointment command, message = {}",
                    e.getType().getErrorMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
            return new CommandResult(Page.APPOINTMENTS);
        }

        request.setAttribute(STAFF_ID, staff.getId());
        request.setAttribute(ASSIGNED_PATIENTS, assignedPatients);
        request.setAttribute(APPOINTMENT_TYPES, appointmentTypes);
        request.setAttribute(APPOINTMENT_STATUSES, appointmentStatuses);
        return new CommandResult(Page.ADD_APPOINTMENT);
    }
}
