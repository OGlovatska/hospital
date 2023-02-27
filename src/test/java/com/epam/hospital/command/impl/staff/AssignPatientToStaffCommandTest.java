package com.epam.hospital.command.impl.staff;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.TestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Command.STAFF_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.TestData.getDoctorUserTo;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssignPatientToStaffCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final PatientService service = mock(PatientService.class);
    private final AssignPatientToStaffCommand command = new AssignPatientToStaffCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getDoctorUserTo());
        when(request.getParameter(PATIENT_ID)).thenReturn(String.valueOf(TestData.PATIENT_ID));
        when(request.getParameter(STAFF_ID)).thenReturn(String.valueOf(TestData.DOCTOR_STAFF_ID));

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(STAFF_DETAILS,
                getParameter(STAFF_ID, String.valueOf(TestData.DOCTOR_STAFF_ID)),
                getParameter(ACTIVE_TAB, PATIENTS_TAB)), result.getPage());
    }
}
