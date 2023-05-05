package com.epam.hospital.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.epam.hospital.command.constant.Command.COMMAND;
import static com.epam.hospital.command.constant.Command.LOGIN;
import static com.epam.hospital.command.constant.Page.ACCESS_DENIED;
import static com.epam.hospital.command.constant.Parameter.*;

@WebFilter(urlPatterns = "/*",
        initParams = {@WebInitParam(name = EXCLUDED_COMMAND, value = LOGIN)})
public class AuthenticationFilter extends HttpFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
    private String excludedCommand;

    @Override
    public void init(FilterConfig config) {
        excludedCommand = config.getInitParameter(EXCLUDED_COMMAND);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (isCommand(request) && session.getAttribute(USER) == null) {
            LOG.error("User in session is null, access denied");
            request.getRequestDispatcher(ACCESS_DENIED).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isCommand(HttpServletRequest request) {
        String commandName = request.getParameter(COMMAND);
        return commandName != null && !commandName.isEmpty() && !commandName.equals(excludedCommand);
    }
}