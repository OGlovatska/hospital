package com.epam.hospital.command.impl.patient;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Attribute;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.command.constant.Parameter;
import com.epam.hospital.exception.ApplicationException;
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
import static com.epam.hospital.util.RequestUtil.numberOfPages;
import static com.epam.hospital.util.RequestUtil.setRequestAttributes;
import static com.epam.hospital.util.ValidationUtil.validateCurrentPageValue;
import static com.epam.hospital.util.ValidationUtil.validateLimitValue;

public class PatientDetailsCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(PatientDetailsCommand.class);
    private final StaffService staffService;
    private final HospitalisationService hospitalisationService;
    private final PatientService patientService;

    public PatientDetailsCommand(ApplicationContext applicationContext) {
        this.staffService = applicationContext.getStaffService();
        this.hospitalisationService = applicationContext.getHospitalisationService();
        this.patientService = applicationContext.getPatientService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        request.setAttribute(PATIENT_ID, patientId);

        PatientTo patient = null;
        HospitalisationTo hospitalisation = null;
        try {
            patient = patientService.getPatient(patientId);
            hospitalisation = hospitalisationService.getPatientCurrentHospitalisation(patientId);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing patient details command, message = {}",
                    e.getType().getErrorMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }
        request.setAttribute(CURRENT_PATIENT, patient);
        request.setAttribute(HOSPITALISATION, hospitalisation);

        String activeTab = request.getParameter(ACTIVE_TAB);
        setActiveTab(request, user, activeTab);

        setStaffTabAttributes(request, patientId);
        setHospitalisationsTabAttributes(request, patientId);
        return new CommandResult(Page.PATIENT_DETAILS);
    }

    private void setActiveTab(HttpServletRequest request, UserTo user, String activeTab) {
        if (activeTab == null || activeTab.isEmpty()) {
            if (user.getRole().equals(Role.ADMIN)) {
                request.setAttribute(ACTIVE_TAB, STAFF_TAB);
            } else {
                request.setAttribute(ACTIVE_TAB, HOSPITALISATIONS_TAB);
            }
        } else {
            request.setAttribute(ACTIVE_TAB, activeTab);
        }
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
            hospitalisations = hospitalisationService.getAllHospitalisationsOfPatient(patientId, offset,
                    limit, orderBy, direction);
            hospitalisationsCount = hospitalisationService.getAllHospitalisationsOfPatientCount(patientId);
            hospitalisationStatuses = hospitalisationService.getHospitalisationStatuses();
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing create patient details command, message = {}",
                    e.getType().getErrorMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(HOSPITALISATIONS_COUNT, hospitalisationsCount),
                new Attribute(HOSPITALISATIONS_NUMBER_OF_PAGES, numberOfPages(hospitalisationsCount, limit)),
                new Attribute(CURRENT_HOSPITALISATIONS_PAGE, page), new Attribute(HOSPITALISATIONS_OFFSET, offset),
                new Attribute(HOSPITALISATIONS_LIMIT, limit), new Attribute(HOSPITALISATIONS_ORDER_BY, orderBy),
                new Attribute(HOSPITALISATIONS_SORT_DIRECTION, direction), new Attribute(HOSPITALISATIONS, hospitalisations),
                new Attribute(HOSPITALISATION_STATUSES, hospitalisationStatuses));
    }

    private void setStaffTabAttributes(HttpServletRequest request, int patientId) {
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
            LOG.error("Exception has occurred during executing create patient details command, message = {}",
                    e.getType().getErrorMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(STAFF_COUNT, assignedStaffCount),
                new Attribute(STAFF_NUMBER_OF_PAGES, numberOfPages(assignedStaffCount, limit)),
                new Attribute(CURRENT_STAFF_PAGE, page), new Attribute(STAFF_OFFSET, offset),
                new Attribute(STAFF_LIMIT, limit), new Attribute(STAFF_ORDER_BY, orderBy),
                new Attribute(STAFF_SORT_DIRECTION, direction), new Attribute(ASSIGNED_STAFF, assignedStaff),
                new Attribute(NOT_ASSIGNED_STAFF, notAssignedStaff));
    }
}
