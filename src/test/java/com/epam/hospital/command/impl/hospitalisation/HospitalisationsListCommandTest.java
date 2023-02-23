package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HospitalisationsListCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final HospitalisationService hospitalisationService = mock(HospitalisationService.class);
    private final PatientService patientService = mock(PatientService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    public void testExecute() {
        when(applicationContext.getHospitalisationService()).thenReturn(hospitalisationService);
        when(applicationContext.getPatientService()).thenReturn(patientService);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getPatientUserTo());
        when(patientService.getPatient(getPatientUserTo())).thenReturn(getPatientTo());

        CommandResult result = new HospitalisationsListCommand(applicationContext).execute(request, response);
        assertEquals(new CommandResult(Page.HOSPITALISATIONS).getPage(), result.getPage());
    }
}
