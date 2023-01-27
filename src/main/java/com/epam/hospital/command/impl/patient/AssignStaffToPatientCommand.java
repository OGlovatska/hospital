package com.epam.hospital.command.impl.patient;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class AssignStaffToPatientCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(AssignStaffToPatientCommand.class);
    private final PatientService service = new PatientService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int staffId = Integer.parseInt(request.getParameter(STAFF_ID));
        int patientId = Integer.parseInt(request.getParameter(PATIENT_ID));
        try {
            service.assignPatientToStaff(staffId, patientId);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        return new CommandResult(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(patientId)),
                getParameter(ACTIVE_TAB, "nav-staff")), true);
    }
}
