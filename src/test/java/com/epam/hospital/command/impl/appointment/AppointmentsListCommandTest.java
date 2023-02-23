package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class AppointmentsListCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppointmentService service = mock(AppointmentService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    public void testExecute() {
        when(applicationContext.getAppointmentService()).thenReturn(service);
        when(session.getAttribute(USER)).thenReturn(getAdminUserTo());
        when(request.getSession()).thenReturn(session);
        CommandResult result = new AppointmentsListCommand(applicationContext).execute(request, response);

        assertEquals(new CommandResult(Page.APPOINTMENTS).getPage(), result.getPage());
    }
}