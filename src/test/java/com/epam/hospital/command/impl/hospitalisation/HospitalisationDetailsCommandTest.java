package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.TestData;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.service.HospitalisationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.TestData.getPatientUserTo;
import static com.epam.hospital.command.constant.Parameter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HospitalisationDetailsCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final HospitalisationService hospitalisationService = mock(HospitalisationService.class);
    private final AppointmentService appointmentService = mock(AppointmentService.class);
    private final HospitalisationDetailsCommand command = new HospitalisationDetailsCommand(hospitalisationService, appointmentService);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getPatientUserTo());
        when(request.getParameter(HOSPITALISATION_ID)).thenReturn(String.valueOf(TestData.HOSPITALISATION_ID));

        CommandResult result = command.execute(request, response);
        assertEquals(new CommandResult(Page.HOSPITALISATION_DETAILS).getPage(), result.getPage());
    }
}
