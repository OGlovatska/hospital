package com.epam.hospital.command.impl.patient;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.TestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.PATIENT_ID;
import static com.epam.hospital.command.constant.Parameter.USER;
import static com.epam.hospital.TestData.getAdminUserTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatientDetailsCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final StaffService staffService = mock(StaffService.class);
    private final HospitalisationService hospitalisationService = mock(HospitalisationService.class);
    private final PatientService patientService = mock(PatientService.class);
    private final PatientDetailsCommand command = new PatientDetailsCommand(staffService, hospitalisationService, patientService);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getAdminUserTo());
        when(request.getParameter(PATIENT_ID)).thenReturn(String.valueOf(TestData.PATIENT_ID));

        CommandResult result = command.execute(request, response);
        assertEquals(new CommandResult(Page.PATIENT_DETAILS).getPage(), result.getPage());
    }
}
