package com.epam.hospital.command.impl.staff;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.USER;
import static com.epam.hospital.TestData.getAdminUserTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StaffListCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final StaffService service = mock(StaffService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    public void testExecute() {
        when(applicationContext.getStaffService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getAdminUserTo());

        CommandResult result = new StaffListCommand(applicationContext).execute(request, response);
        assertEquals(new CommandResult(Page.STAFF).getPage(), result.getPage());
    }
}
