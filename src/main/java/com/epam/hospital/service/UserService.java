package com.epam.hospital.service;

import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.User;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.UserUtil;
import com.epam.hospital.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static com.epam.hospital.util.ValidationUtil.*;

public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserDaoImpl userDao = new UserDaoImpl();

    public UserTo getAuthorizedUser(String email, String password) throws ApplicationException {
        LOG.info("Authorization of user with email {}", email);
        checkEmptyString(email, password);
        try {
            User user = userDao.getByEmail(email).orElseThrow();
            validatePassword(password,  user.getPassword());
            return UserUtil.getUserTo(user);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ApplicationException(ErrorType.APP_ERROR);
        } catch (NoSuchElementException e) {
            LOG.error("Exception has occurred during executing getByEmail() method in UserService", e);
            throw new IllegalRequestDataException(ErrorType.DATA_NOT_FOUND);
        }
    }
}
