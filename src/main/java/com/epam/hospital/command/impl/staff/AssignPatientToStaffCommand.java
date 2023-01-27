package com.epam.hospital.command.impl.staff;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.STAFF_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class AssignPatientToStaffCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(AssignPatientToStaffCommand.class);
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
        return new CommandResult(getPageToRedirect(STAFF_DETAILS,
                getParameter(STAFF_ID, String.valueOf(staffId)),
                getParameter(ACTIVE_TAB, "nav-patients")), true);
    }
}
