package com.epam.hospital.command.impl.patient;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Command.PATIENTS_LIST;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.EMAIL;
import static com.epam.hospital.TestData.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class SavePatientCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final PatientService service = mock(PatientService.class);
    private final SavePatientCommand command = new SavePatientCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getAdminUserTo());
        when(request.getParameter(FIRST_NAME)).thenReturn(PATIENT_FIRST_NAME);
        when(request.getParameter(LAST_NAME)).thenReturn(PATIENT_LAST_NAME);
        when(request.getParameter(EMAIL)).thenReturn(PATIENT_EMAIL);
        when(request.getParameter(ROLE)).thenReturn(PATIENT_ROLE.name());
        when(request.getParameter(DATE_OF_BIRTH)).thenReturn(String.valueOf(PATIENT_DATE_OF_BIRTH));
        when(request.getParameter(GENDER)).thenReturn(String.valueOf(PATIENT_GENDER));

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(PATIENTS_LIST), result.getPage());
    }
}