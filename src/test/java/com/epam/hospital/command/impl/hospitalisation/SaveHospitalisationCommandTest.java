package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.TestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Command.PATIENT_DETAILS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.HOSPITALISATIONS_TAB;
import static com.epam.hospital.TestData.HOSPITALISATION_DATE;
import static com.epam.hospital.TestData.getDoctorUserTo;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveHospitalisationCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final HospitalisationService service = mock(HospitalisationService.class);
    private final SaveHospitalisationCommand command = new SaveHospitalisationCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getDoctorUserTo());
        when(request.getParameter(PATIENT_ID)).thenReturn(String.valueOf(TestData.PATIENT_ID));
        when(request.getParameter(HOSPITALISATION_START_DATE)).thenReturn(String.valueOf(HOSPITALISATION_DATE));

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(PATIENT_DETAILS,
                getParameter(PATIENT_ID, String.valueOf(TestData.PATIENT_ID)),
                getParameter(ACTIVE_TAB, HOSPITALISATIONS_TAB)), result.getPage());
    }
}
