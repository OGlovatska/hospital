package com.epam.hospital.controller;

import java.io.*;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandFactory;
import com.epam.hospital.command.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.COMMAND;

@WebServlet(urlPatterns = {"/api"})
public class Controller extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.info("Command {} called", request.getParameter(COMMAND));
        Command action = CommandFactory.getCommand(request.getParameter(COMMAND));
        CommandResult result = action.execute(request, response);
        if (result != null) {
            if (result.isRedirect()) {
                response.sendRedirect(result.getPage());
            } else {
                request.getRequestDispatcher(result.getPage()).forward(request, response);
            }
        }
    }
}