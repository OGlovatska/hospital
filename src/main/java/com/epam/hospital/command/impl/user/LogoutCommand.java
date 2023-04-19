package com.epam.hospital.command.impl.user;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.epam.hospital.command.constant.Parameter.LANGUAGE;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String selectedLanguage = String.valueOf(session.getAttribute(LANGUAGE));
        session.invalidate();

        request.getSession(true).setAttribute(LANGUAGE, selectedLanguage);
        return new CommandResult(Page.LOGIN);
    }
}