package com.epam.hospital.service;

import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.User;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.UserUtil;
import com.epam.hospital.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static com.epam.hospital.exception.ErrorType.APP_ERROR;
import static com.epam.hospital.exception.ErrorType.USER_NOT_FOUND;
import static com.epam.hospital.util.ValidationUtil.*;

public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserDaoImpl userDao;

    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public UserTo getAuthorizedUser(String email, String password) throws ApplicationException {
        LOG.info("Authorization of user with email {}", email);
        checkEmptyString(email, password);
        try {
            User user = userDao.getByEmail(email).orElseThrow();
            validatePassword(password, user.getPassword());
            return UserUtil.getUserTo(user);
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        } catch (NoSuchElementException e) {
            throw new IllegalRequestDataException(USER_NOT_FOUND);
        }
    }
}
