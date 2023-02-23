package com.epam.hospital.util;

import com.epam.hospital.model.User;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.hospital.command.constant.Parameter.*;

public class UserUtil {

    public static UserTo getUserTo(User user) {
        return new UserTo.Builder<>().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).email(user.getEmail()).role(user.getRole()).build();
    }

    public static User createUser(HttpServletRequest request, String password) {
        User user = new User.Builder<>().firstName(request.getParameter(FIRST_NAME))
                .lastName(request.getParameter(LAST_NAME)).email(request.getParameter(EMAIL))
                .password(password).role(Role.valueOf(request.getParameter(ROLE))).build();
        return prepareToSaveUser(user);
    }

    private static User prepareToSaveUser(User user) {
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
