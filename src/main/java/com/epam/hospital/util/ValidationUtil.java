package com.epam.hospital.util;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.exception.*;
import com.epam.hospital.model.User;
import com.epam.hospital.pagination.Sort;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.encoding.PasswordEncoder;
import com.epam.hospital.encoding.PasswordEncoderFactories;

import java.util.Arrays;

import static com.epam.hospital.exception.ErrorType.APP_ERROR;
import static com.epam.hospital.exception.ErrorType.DUPLICATE_EMAIL;

public class ValidationUtil {
    public static void checkUserNotNull(UserTo user) {
        if (user == null) {
            throw new NotFoundException(ErrorType.USER_NOT_FOUND);
        }
    }

    public static void checkEmptyString(String... strings) {
        Arrays.stream(strings).filter(s -> s == null || s.isEmpty()).forEach(s -> {
            throw new IllegalRequestDataException(ErrorType.EMPTY_FIELDS);
        });
    }

    public static int validateLimitValue(String limit) {
        if (limit != null && !limit.isEmpty()) {
            try {
                return Integer.parseInt(limit);
            } catch (NumberFormatException e) {
                return 10;
            }
        }
        return 10;
    }

    public static int validateCurrentPageValue(String page) {
        if (page != null && !page.isEmpty()) {
            try {
                return Integer.parseInt(page);
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }

    public static String validateOrderByValue(String orderBy) {
        if (orderBy != null && !orderBy.isEmpty()) {
            return orderBy;
        }
        return "id";
    }

    public static String validateDirectionValue(String direction) {
        if (direction != null && !direction.isEmpty()) {
            try {
                return Sort.Direction.valueOf(direction).name();
            } catch (IllegalArgumentException e) {
                return "ASC";
            }
        }
        return "ASC";
    }

    public static void validatePassword(String password, String encodedPassword) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (!encoder.matches(password, encodedPassword)) {
            throw new IllegalRequestDataException(ErrorType.INVALID_PASSWORD);
        }
    }

    public static void validateUniqueEmail(String email) {
        UserDaoImpl userDao = ApplicationContext.getInstance().getUserDao();
        try {
            User user = userDao.getByEmail(email).orElse(null);
            if (user != null) {
                throw new IllegalRequestDataException(DUPLICATE_EMAIL);
            }
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }
}
