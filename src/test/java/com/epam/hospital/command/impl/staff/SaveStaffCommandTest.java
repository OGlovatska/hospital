package com.epam.hospital.command.impl.staff;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.TestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Command.STAFF_LIST;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.EMAIL;
import static com.epam.hospital.command.constant.Parameter.SPECIALISATION;
import static com.epam.hospital.TestData.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveStaffCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final StaffService service = mock(StaffService.class);
    private final SaveStaffCommand command = new SaveStaffCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getAdminUserTo());
        when(request.getParameter(FIRST_NAME)).thenReturn(DOCTOR_FIRST_NAME);
        when(request.getParameter(LAST_NAME)).thenReturn(DOCTOR_LAST_NAME);
        when(request.getParameter(EMAIL)).thenReturn(DOCTOR_EMAIL);
        when(request.getParameter(ROLE)).thenReturn(String.valueOf(DOCTOR_ROLE));
        when(request.getParameter(SPECIALISATION)).thenReturn(TestData.SPECIALISATION);

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(STAFF_LIST), result.getPage());
    }
}
