package com.epam.hospital.util;

import com.epam.hospital.exception.*;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.encoding.PasswordEncoder;
import com.epam.hospital.encoding.PasswordEncoderFactories;

import java.util.Arrays;

public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    private static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException(ErrorType.DATA_NOT_FOUND);
        }
    }

    public static void checkUserNotNull(UserTo user){
        if (user == null) {
            throw new IllegalRequestDataException(ErrorType.DATA_ERROR);
        }
    }

    public static void checkEmptyString(String...strings){
        Arrays.stream(strings).filter(s -> s == null || s.isEmpty()).forEach(s -> {
            throw new IllegalRequestDataException(ErrorType.DATA_ERROR);
        });
    }

    public static int validateLimitValue(String limit){
        if (limit != null && !limit.isEmpty()){
            try {
                return Integer.parseInt(limit);
            } catch (NumberFormatException e) {
                return 10;
            }
        }
        return 10;
    }

    public static int validateCurrentPageValue(String page){
        if (page != null && !page.isEmpty()){
            try {
                return Integer.parseInt(page);
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }

    public static void validatePassword(String password, String encodedPassword){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (!encoder.matches(password, encodedPassword)){
            throw new IllegalRequestDataException(ErrorType.VALIDATION_ERROR);
        }
    }
}
