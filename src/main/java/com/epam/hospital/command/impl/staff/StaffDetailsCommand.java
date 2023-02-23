package com.epam.hospital.command.impl.staff;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Attribute;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.command.constant.Parameter;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.StaffTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.RequestUtil.*;
import static com.epam.hospital.util.ValidationUtil.validateCurrentPageValue;
import static com.epam.hospital.util.ValidationUtil.validateLimitValue;

public class StaffDetailsCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(StaffDetailsCommand.class);
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final StaffService staffService;

    public StaffDetailsCommand(ApplicationContext applicationContext) {
        this.patientService = applicationContext.getPatientService();
        this.appointmentService = applicationContext.getAppointmentService();
        this.staffService = applicationContext.getStaffService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int staffId = Integer.parseInt(request.getParameter(STAFF_ID));
        request.setAttribute(STAFF_ID, staffId);

        StaffTo staff = null;
        try {
            staff = staffService.getStaff(staffId);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing staff details command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }
        request.setAttribute(CURRENT_STAFF, staff);

        String activeTab = request.getParameter(ACTIVE_TAB);
        request.setAttribute(ACTIVE_TAB, activeTab == null || activeTab.isEmpty() ? PATIENTS_TAB : activeTab);

        setPatientsTabAttributes(request, staffId);
        setAppointmentsTabAttributes(request, staffId);
        return new CommandResult(Page.STAFF_DETAILS);
    }

    private void setPatientsTabAttributes(HttpServletRequest request, int staffId) {
        List<PatientTo> assignedPatients = new ArrayList<>();
        List<PatientTo> notAssignedPatients = new ArrayList<>();
        int assignedPatientsCount = 0;

        int page = validateCurrentPageValue(request.getParameter(CURRENT_PATIENTS_PAGE));
        int limit = validateLimitValue(request.getParameter(PATIENTS_LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(PATIENTS_ORDER_BY);
        String direction = request.getParameter(PATIENTS_SORT_DIRECTION);

        try {
            assignedPatients = patientService.getAllPatientsOfStaff(staffId, offset, limit,
                    orderBy, direction);
            assignedPatientsCount = patientService.getPatientsOfStaffCount(staffId);
            notAssignedPatients = patientService.getAllPatientsNotAssignedToStaff(staffId);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing staff details command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(ASSIGNED_PATIENTS, assignedPatients),
                new Attribute(NOT_ASSIGNED_PATIENTS, notAssignedPatients), new Attribute(PATIENTS_LIMIT, limit),
                new Attribute(PATIENTS_OFFSET, offset), new Attribute(CURRENT_PATIENTS_PAGE, page),
                new Attribute(PATIENTS_ORDER_BY, orderBy), new Attribute(PATIENTS_SORT_DIRECTION, direction),
                new Attribute(PATIENTS_COUNT, assignedPatientsCount),
                new Attribute(PATIENTS_NUMBER_OF_PAGES, numberOfPages(assignedPatientsCount, limit)));
    }

    private void setAppointmentsTabAttributes(HttpServletRequest request, int staffId) {
        List<AppointmentTo> appointments = new ArrayList<>();
        int appointmentsCount = 0;

        int page = validateCurrentPageValue(request.getParameter(CURRENT_APPOINTMENTS_PAGE));
        int limit = validateLimitValue(request.getParameter(APPOINTMENTS_LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(APPOINTMENTS_ORDER_BY);
        String direction = request.getParameter(APPOINTMENTS_SORT_DIRECTION);

        try {
            appointments = appointmentService.getAllAppointmentsOfStaff(staffId, offset,
                    limit, orderBy, direction);
            appointmentsCount = appointmentService.getAllAppointmentsCount(staffId);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing staff details command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(APPOINTMENTS, appointments), new Attribute(APPOINTMENTS_LIMIT, limit),
                new Attribute(APPOINTMENTS_OFFSET, offset), new Attribute(CURRENT_APPOINTMENTS_PAGE, page),
                new Attribute(APPOINTMENTS_ORDER_BY, orderBy), new Attribute(APPOINTMENTS_SORT_DIRECTION, direction),
                new Attribute(APPOINTMENTS_COUNT, appointmentsCount),
                new Attribute(APPOINTMENTS_NUMBER_OF_PAGES, numberOfPages(appointmentsCount, limit)));
    }
}