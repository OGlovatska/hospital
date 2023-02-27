package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.TestData;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.TestData.getDoctorUserTo;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DetermineDiagnosisCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final HospitalisationService service = mock(HospitalisationService.class);
    private final DetermineDiagnosisCommand command = new DetermineDiagnosisCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getDoctorUserTo());
        when(request.getParameter(HOSPITALISATION_ID)).thenReturn(String.valueOf(TestData.HOSPITALISATION_ID));
        when(request.getParameter(PATIENT_ID)).thenReturn(String.valueOf(TestData.PATIENT_ID));
        when(request.getParameter(DIAGNOSIS)).thenReturn(TestData.DIAGNOSIS);

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(TestData.PATIENT_ID))), result.getPage());
        Mockito.verify(service, Mockito.times(1))
                .determinePatientDiagnosis(any(UserTo.class), any(int.class), any(String.class));
    }
}