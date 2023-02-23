package com.epam.hospital.command.impl.user;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ErrorType;
import com.epam.hospital.exception.IllegalRequestDataException;
import com.epam.hospital.service.UserService;
import com.epam.hospital.TestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.TestData.getAdminUserTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final UserService service = mock(UserService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    void testExecute(){
        when(applicationContext.getUserService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(EMAIL)).thenReturn(TestData.EMAIL);
        when(request.getParameter(PASSWORD)).thenReturn(TestData.PASSWORD);
        when(service.getAuthorizedUser(TestData.EMAIL, TestData.PASSWORD)).thenReturn(getAdminUserTo());
        CommandResult result = new LoginCommand(applicationContext).execute(request, response);

        assertEquals(new CommandResult(Page.MAIN).getPage(), result.getPage());
    }

    @Test
    void testExecuteIncorrectEmail(){
        when(applicationContext.getUserService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(EMAIL)).thenReturn(TestData.INCORRECT_EMAIL);
        when(request.getParameter(PASSWORD)).thenReturn(TestData.PASSWORD);
        when(service.getAuthorizedUser(TestData.INCORRECT_EMAIL, TestData.PASSWORD))
                .thenThrow(new IllegalRequestDataException(ErrorType.USER_NOT_FOUND));
        CommandResult result = new LoginCommand(applicationContext).execute(request, response);

        assertEquals(new CommandResult(Page.LOGIN).getPage(), result.getPage());
    }

    @Test
    void testExecuteIncorrectPassword(){
        when(applicationContext.getUserService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(EMAIL)).thenReturn(TestData.EMAIL);
        when(request.getParameter(PASSWORD)).thenReturn(TestData.INCORRECT_PASSWORD);
        when(service.getAuthorizedUser(TestData.EMAIL, TestData.INCORRECT_PASSWORD))
                .thenThrow(new IllegalRequestDataException(ErrorType.INVALID_PASSWORD));
        CommandResult result = new LoginCommand(applicationContext).execute(request, response);

        assertEquals(new CommandResult(Page.LOGIN).getPage(), result.getPage());
    }

    @Test
    void testExecuteEmptyFields(){
        when(applicationContext.getUserService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(EMAIL)).thenReturn("");
        when(request.getParameter(PASSWORD)).thenReturn("");
        when(service.getAuthorizedUser("", ""))
                .thenThrow(new IllegalRequestDataException(ErrorType.EMPTY_FIELDS));
        CommandResult result = new LoginCommand(applicationContext).execute(request, response);

        assertEquals(new CommandResult(Page.LOGIN).getPage(), result.getPage());
    }
}