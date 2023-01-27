package com.epam.hospital.repository;

import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class PatientRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PatientRepository.class);
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final PatientDaoImpl patientDao = new PatientDaoImpl();
    private final DBManager dbManager = MySQLDBManager.getInstance();

    public Optional<Patient> save(User user, Patient patient) throws DBException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            userDao.save(user, connection);

            if (user.getId() != 0) {
                patient.setUserId(user.getId());
                patientDao.save(patient, connection);
            }
            connection.commit();
            return Optional.of(patient);
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing SAVE USER PATIENT query", e);
            dbManager.rollbackConnection(connection);
            throw new DBException();
        } finally {
            dbManager.closeConnection(connection);
        }
    }
}
