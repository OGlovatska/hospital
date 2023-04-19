package com.epam.hospital.repository.impl;

import com.epam.hospital.dao.StaffDao;
import com.epam.hospital.dao.UserDao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.repository.StaffRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class StaffRepositoryImpl implements StaffRepository {
    private final UserDao userDao;
    private final StaffDao staffDao;
    private final DBManager dbManager;

    public StaffRepositoryImpl(UserDao userDao, StaffDao staffDao, DBManager dbManager) {
        this.userDao = userDao;
        this.staffDao = staffDao;
        this.dbManager = dbManager;
    }

    public Optional<Staff> save(User user, Staff staff) throws DBException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            userDao.save(user, connection);

            if (user.getId() != 0) {
                staff.setUserId(user.getId());
                staffDao.save(staff, connection);
            }
            connection.commit();
            return Optional.of(staff);
        } catch (SQLException e) {
            dbManager.rollbackConnection(connection);
            throw new DBException(e.getMessage());
        } finally {
            dbManager.closeConnection(connection);
        }
    }
}