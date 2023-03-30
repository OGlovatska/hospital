package com.epam.hospital.command.impl.staff;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.TestData.DOCTOR_STAFF_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StaffDetailsCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final PatientService patientService = mock(PatientService.class);
    private final StaffService staffService = mock(StaffService.class);
    private final StaffDetailsCommand command = new StaffDetailsCommand(patientService, staffService);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(STAFF_ID)).thenReturn(String.valueOf(DOCTOR_STAFF_ID));

        CommandResult result = command.execute(request, response);
        assertEquals(new CommandResult(Page.STAFF_DETAILS).getPage(), result.getPage());
    }
}
