package com.epam.hospital.command.impl.user;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.UserService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.MAIN;
import static com.epam.hospital.command.constant.Command.PATIENTS_LIST;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class LoginCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(LoginCommand.class);
    private final UserService userService;

    public LoginCommand() {
        this.userService = ApplicationContext.getInstance().getUserService();
    }

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserTo user = userService.getAuthorizedUser(request.getParameter(EMAIL), request.getParameter(PASSWORD));
            HttpSession session = request.getSession();
            session.setAttribute(USER, user);
            return new CommandResult(getPageToRedirect(MAIN), true);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing login command, message = {}", e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
            return new CommandResult(Page.LOGIN);
        }
    }
}
