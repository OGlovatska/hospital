package com.epam.hospital.command.impl.user;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class EmptyCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @InjectMocks
    private final EmptyCommand command = new EmptyCommand();

    @Test
    void testExecute(){
        CommandResult result = command.execute(request, response);
        assertEquals(new CommandResult(Page.LOGIN).getPage(), result.getPage());
    }
}
