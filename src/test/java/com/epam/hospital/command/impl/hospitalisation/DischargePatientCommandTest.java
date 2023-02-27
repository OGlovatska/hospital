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

import java.time.LocalDate;

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.HOSPITALISATION_ID;
import static com.epam.hospital.command.constant.Parameter.PATIENT_ID;
import static com.epam.hospital.TestData.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DischargePatientCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final HospitalisationService service = mock(HospitalisationService.class);
    private final DischargePatientCommand command = new DischargePatientCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getDoctorUserTo());
        when(request.getParameter(HOSPITALISATION_ID)).thenReturn(String.valueOf(TestData.HOSPITALISATION_ID));
        when(request.getParameter(PATIENT_ID)).thenReturn(String.valueOf(TestData.PATIENT_ID));
        when(request.getParameter(HOSPITALISATION_END_DATE)).thenReturn(String.valueOf(DISCHARGING_DATE));

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(TestData.PATIENT_ID))), result.getPage());
        Mockito.verify(service, Mockito.times(1))
                .dischargePatient(any(UserTo.class), any(int.class), any(LocalDate.class));
    }
}
