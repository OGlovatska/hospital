package com.epam.hospital.command.impl.staff;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.User;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.AppointmentTo;
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

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.PaginationUtil.*;
import static com.epam.hospital.util.ValidationUtil.validateCurrentPageValue;
import static com.epam.hospital.util.ValidationUtil.validateLimitValue;

public class StaffDetailsCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(StaffDetailsCommand.class);
    private final PatientService patientService = new PatientService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final StaffService staffService = new StaffService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int staffId = Integer.parseInt(request.getParameter(STAFF_ID));
        request.setAttribute(STAFF_ID, staffId);

        StaffTo staff = null;
        try {
            staff = staffService.getStaff(staffId);
        } catch (ApplicationException e){
            e.printStackTrace();
        }
        request.setAttribute(CURRENT_STAFF, staff);

        String activeTab = request.getParameter(ACTIVE_TAB);
        request.setAttribute(ACTIVE_TAB, activeTab == null || activeTab.isEmpty() ? "nav-patients" : activeTab);


        setPatientsTabAttributes(request, staffId);
        setAppointmentsTabAttributes(request, staffId);
        return new CommandResult(Page.STAFF_DETAILS);
    }

    private void setPatientsTabAttributes(HttpServletRequest request, int staffId){
        List<PatientTo> assignedPatients = new ArrayList<>();
        List<PatientTo> notAssignedPatients = new ArrayList<>();
        int assignedPatientsCount = 0;

        int page = validateCurrentPageValue(request.getParameter(CURRENT_PATIENTS_PAGE));
        int limit = validateLimitValue(request.getParameter(PATIENTS_LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(PATIENTS_ORDER_BY);
        String direction = request.getParameter(PATIENTS_SORT_DIRECTION);

        try {
            assignedPatients = patientService.getAllPatientsOfStaff(staffId, String.valueOf(offset),
                    String.valueOf(limit), orderBy,direction);
            assignedPatientsCount = patientService.getPatientsOfStaffCount(staffId);
            notAssignedPatients = patientService.getAllPatientsNotAssignedToStaff(staffId);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        request.setAttribute(ASSIGNED_PATIENTS, assignedPatients);
        request.setAttribute(NOT_ASSIGNED_PATIENTS, notAssignedPatients);
        request.setAttribute(PATIENTS_LIMIT, limit);
        request.setAttribute(PATIENTS_OFFSET, offset);
        request.setAttribute(CURRENT_PATIENTS_PAGE, page);
        request.setAttribute(PATIENTS_ORDER_BY, orderBy);
        request.setAttribute(PATIENTS_SORT_DIRECTION, direction);
        request.setAttribute(PATIENTS_COUNT, assignedPatientsCount);
        request.setAttribute(PATIENTS_NUMBER_OF_PAGES, numberOfPages(assignedPatientsCount, limit));
    }

    private void setAppointmentsTabAttributes(HttpServletRequest request, int staffId){
        List<AppointmentTo> appointments = new ArrayList<>();
        int appointmentsCount = 0;

        int page = validateCurrentPageValue(request.getParameter(CURRENT_APPOINTMENTS_PAGE));
        int limit = validateLimitValue(request.getParameter(APPOINTMENTS_LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(APPOINTMENTS_ORDER_BY);
        String direction = request.getParameter(APPOINTMENTS_SORT_DIRECTION);

        try {
            appointments = appointmentService.getAllAppointmentsOfStaff(staffId, String.valueOf(offset),
                    String.valueOf(limit), orderBy,direction);
            appointmentsCount = appointmentService.getAllAppointmentsCount(staffId);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        request.setAttribute(APPOINTMENTS, appointments);
        request.setAttribute(APPOINTMENTS_LIMIT, limit);
        request.setAttribute(APPOINTMENTS_OFFSET, offset);
        request.setAttribute(CURRENT_APPOINTMENTS_PAGE, page);
        request.setAttribute(APPOINTMENTS_ORDER_BY, orderBy);
        request.setAttribute(APPOINTMENTS_SORT_DIRECTION, direction);
        request.setAttribute(APPOINTMENTS_COUNT, appointmentsCount);
        request.setAttribute(APPOINTMENTS_NUMBER_OF_PAGES, numberOfPages(appointmentsCount, limit));
    }
}