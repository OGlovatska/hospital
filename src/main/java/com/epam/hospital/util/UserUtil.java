package com.epam.hospital.util;

import com.epam.hospital.model.User;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.hospital.command.constant.Parameter.*;

public class UserUtil {

    public static UserTo getUserTo(User user) {
        return new UserTo(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getRole().name());
    }

    public static User createUser(HttpServletRequest request, String password) {
        User user = new User();
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setEmail(request.getParameter(EMAIL));
        user.setPassword(password);
        user.setRole(Role.valueOf(request.getParameter(ROLE)));
        return prepareToSaveUser(user);
    }

    private static User prepareToSaveUser(User user) {
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
