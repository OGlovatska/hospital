package com.epam.hospital.command.impl.staff;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.StaffTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.RequestUtil.*;

public class StaffDetailsCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(StaffDetailsCommand.class);
    private final PatientService patientService;
    private final StaffService staffService;

    public StaffDetailsCommand() {
        this.patientService = ApplicationContext.getInstance().getPatientService();
        this.staffService = ApplicationContext.getInstance().getStaffService();
    }

    public StaffDetailsCommand(PatientService patientService, StaffService staffService) {
        this.patientService = patientService;
        this.staffService = staffService;
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

        setPatientsTabAttributes(request, staffId);
        return new CommandResult(Page.STAFF_DETAILS);
    }

    private void setPatientsTabAttributes(HttpServletRequest request, int staffId) {
        List<PatientTo> assignedPatients = new ArrayList<>();
        List<PatientTo> notAssignedPatients = new ArrayList<>();
        int assignedPatientsCount = 0;

        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        try {
            assignedPatients = patientService.getAllPatientsOfStaff(staffId, (int) paginationAttributes.get(OFFSET),
                    (int) paginationAttributes.get(LIMIT), (String) paginationAttributes.get(ORDER_BY),
                    (String) paginationAttributes.get(DIRECTION));
            assignedPatientsCount = patientService.getPatientsOfStaffCount(staffId);
            notAssignedPatients = patientService.getAllPatientsNotAssignedToStaff(staffId);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing staff details command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }
        getRequestAttributes(request, assignedPatients, notAssignedPatients, assignedPatientsCount, paginationAttributes);
    }

    private void getRequestAttributes(HttpServletRequest request, List<PatientTo> assignedPatients,
                                      List<PatientTo> notAssignedPatients, int assignedPatientsCount,
                                      Map<String, Object> paginationAttributes) {
        request.setAttribute(ASSIGNED_PATIENTS, assignedPatients);
        request.setAttribute(NOT_ASSIGNED_PATIENTS, notAssignedPatients);
        setPaginationAttributes(request, assignedPatientsCount, (int) paginationAttributes.get(LIMIT),
                (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(CURRENT_PAGE),
                (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
    }
}