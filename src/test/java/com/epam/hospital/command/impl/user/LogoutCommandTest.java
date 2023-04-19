package com.epam.hospital.command.impl.user;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.epam.hospital.TestData.LOCALE;
import static com.epam.hospital.command.constant.Parameter.LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogoutCommandTest {
    private final HttpSession session = mock(HttpSession.class);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @InjectMocks
    private final LogoutCommand command = new LogoutCommand();

    @Test
    void testExecute(){
        when(request.getSession()).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        CommandResult result = command.execute(request, response);
        assertEquals(new CommandResult(Page.LOGIN).getPage(), result.getPage());
    }
}