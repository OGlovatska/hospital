package com.epam.hospital.repository;

import com.epam.hospital.dao.impl.StaffDaoImpl;
import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.listener.DBContextListener;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class StaffRepository {
    private final UserDaoImpl userDao;
    private final StaffDaoImpl staffDao;
    private final DBManager dbManager;

    public StaffRepository(UserDaoImpl userDao, StaffDaoImpl staffDao) {
        ServletContext servletContext = DBContextListener.getServletContext();
        this.dbManager = (DBManager) servletContext.getAttribute("dbManager");
        this.userDao = userDao;
        this.staffDao = staffDao;
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
