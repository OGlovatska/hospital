package com.epam.hospital.command.impl.patient;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.USER;
import static com.epam.hospital.TestData.getPatientUserTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatientsListCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final PatientService service = mock(PatientService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    public void testExecute() {
        when(applicationContext.getPatientService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getPatientUserTo());

        CommandResult result = new PatientsListCommand(applicationContext).execute(request, response);
        assertEquals(new CommandResult(Page.PATIENTS).getPage(), result.getPage());
    }
}
