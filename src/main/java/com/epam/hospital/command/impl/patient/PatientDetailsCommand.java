package com.epam.hospital.command.impl.patient;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.HospitalisationTo;
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
import static com.epam.hospital.command.constant.Parameter.HOSPITALISATIONS_LIMIT;
import static com.epam.hospital.util.PaginationUtil.numberOfPages;
import static com.epam.hospital.util.ValidationUtil.validateCurrentPageValue;
import static com.epam.hospital.util.ValidationUtil.validateLimitValue;

public class PatientDetailsCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(PatientDetailsCommand.class);
    private final StaffService staffService = new StaffService();
    private final HospitalisationService hospitalisationService = new HospitalisationService();
    private final PatientService patientService = new PatientService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        request.setAttribute(PATIENT_ID, patientId);

        PatientTo patient = null;
        HospitalisationTo hospitalisation = new HospitalisationTo();
        try {
            patient = patientService.getPatient(patientId);
            hospitalisation = hospitalisationService.getPatientCurrentHospitalisation(patientId);
        } catch (ApplicationException e){
            e.printStackTrace();
        }
        request.setAttribute(CURRENT_PATIENT, patient);
        request.setAttribute(HOSPITALISATION, hospitalisation);

        String activeTab = request.getParameter(ACTIVE_TAB);
        if (activeTab == null || activeTab.isEmpty()){
            if (user.getRole().equals(Role.ADMIN.name())){
                request.setAttribute(ACTIVE_TAB, "nav-staff");
            } else {
                request.setAttribute(ACTIVE_TAB, "nav-hospitalisations");
            }
        } else {
            request.setAttribute(ACTIVE_TAB, activeTab);
        }

        setStaffTabAttributes(request, patientId);
        setHospitalisationsTabAttributes(request, patientId);
        return new CommandResult(Page.PATIENT_DETAILS);
    }

    private void setHospitalisationsTabAttributes(HttpServletRequest request, int patientId) {
        List<HospitalisationTo> hospitalisations = new ArrayList<>();
        List<String> hospitalisationStatuses = new ArrayList<>();
        int hospitalisationsCount = 0;

        int page = validateCurrentPageValue(request.getParameter(CURRENT_HOSPITALISATIONS_PAGE));
        int limit = validateLimitValue(request.getParameter(HOSPITALISATIONS_LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(HOSPITALISATIONS_ORDER_BY);
        String direction = request.getParameter(HOSPITALISATIONS_SORT_DIRECTION);

        try {
            hospitalisations = hospitalisationService.getAllHospitalisationsOfPatient(patientId, String.valueOf(offset),
                    String.valueOf(limit), orderBy,direction);
            hospitalisationsCount = hospitalisationService.getAllHospitalisationsOfPatientCount(patientId);
            hospitalisationStatuses = hospitalisationService.getHospitalisationStatuses();
        } catch (ApplicationException e){
            e.printStackTrace();
        }

        request.setAttribute(HOSPITALISATIONS, hospitalisations);
        request.setAttribute(HOSPITALISATIONS_COUNT, hospitalisationsCount);
        request.setAttribute(HOSPITALISATIONS_LIMIT, limit);
        request.setAttribute(HOSPITALISATIONS_OFFSET, offset);
        request.setAttribute(CURRENT_HOSPITALISATIONS_PAGE, page);
        request.setAttribute(HOSPITALISATIONS_ORDER_BY, orderBy);
        request.setAttribute(HOSPITALISATIONS_SORT_DIRECTION, direction);
        request.setAttribute(HOSPITALISATIONS_NUMBER_OF_PAGES, numberOfPages(hospitalisationsCount, limit));
        request.setAttribute(HOSPITALISATION_STATUSES, hospitalisationStatuses);
    }

    private void setStaffTabAttributes(HttpServletRequest request, int patientId){
        List<StaffTo> assignedStaff = new ArrayList<>();
        List<StaffTo> notAssignedStaff = new ArrayList<>();
        int assignedStaffCount = 0;

        int page = validateCurrentPageValue(request.getParameter(CURRENT_STAFF_PAGE));
        int limit = validateLimitValue(request.getParameter(STAFF_LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(STAFF_ORDER_BY);
        String direction = request.getParameter(STAFF_SORT_DIRECTION);

        try {
            assignedStaff = staffService.getAllStaffOfPatient(patientId, String.valueOf(offset),
                    String.valueOf(limit), orderBy, direction);
            assignedStaffCount = staffService.getStaffOfPatientCount(patientId);
            notAssignedStaff = staffService.getAllStaffNotAssignedToPatient(patientId);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        request.setAttribute(ASSIGNED_STAFF, assignedStaff);
        request.setAttribute(NOT_ASSIGNED_STAFF, notAssignedStaff);
        request.setAttribute(STAFF_LIMIT, limit);
        request.setAttribute(STAFF_OFFSET, offset);
        request.setAttribute(CURRENT_STAFF_PAGE, page);
        request.setAttribute(STAFF_ORDER_BY, orderBy);
        request.setAttribute(STAFF_SORT_DIRECTION, direction);
        request.setAttribute(STAFF_COUNT, assignedStaffCount);
        request.setAttribute(STAFF_NUMBER_OF_PAGES, numberOfPages(assignedStaffCount, limit));
    }
}
