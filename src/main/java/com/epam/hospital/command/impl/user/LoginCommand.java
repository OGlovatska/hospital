package com.epam.hospital.command.impl.user;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.UserService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.epam.hospital.command.constant.Parameter.*;

public class LoginCommand implements Command {
    private final UserService userService = new UserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            UserTo user = userService.getAuthorizedUser(request.getParameter(EMAIL), request.getParameter(PASSWORD));
            HttpSession session = request.getSession();
            session.setAttribute(USER, user);
        } catch (ApplicationException e){
            e.printStackTrace();
        }
        return new CommandResult(Page.MAIN, true);
    }
}
