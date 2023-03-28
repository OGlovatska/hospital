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

    public CreateAppointmentCommand() {
        this.patientService = ApplicationContext.getInstance().getPatientService();
        this.appointmentService = ApplicationContext.getInstance().getAppointmentService();
        this.staffService = ApplicationContext.getInstance().getStaffService();
    }

    public CreateAppointmentCommand(PatientService patientService, AppointmentService appointmentService,
                                    StaffService staffService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.staffService = staffService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<PatientTo> assignedPatients;
        StaffTo staff;

        try {
            staff = staffService.getStaff(user);
            assignedPatients = patientService.getAllPatientsOfStaff(staff.getId(), 0,
                    0, null, null);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing create appointment command, message = {}",
                    e.getMessage());
            request.getSession().setAttribute(MESSAGE, e.getType().getErrorCode());
            request.getSession().setAttribute(IS_ERROR, true);
            return new CommandResult(Page.APPOINTMENTS);
        }

        request.setAttribute(STAFF_ID, staff.getId());
        request.setAttribute(ASSIGNED_PATIENTS, assignedPatients);
        return new CommandResult(Page.ADD_APPOINTMENT);
    }
}
