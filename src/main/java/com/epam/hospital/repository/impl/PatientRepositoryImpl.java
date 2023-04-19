package com.epam.hospital.repository.impl;

import com.epam.hospital.dao.PatientDao;
import com.epam.hospital.dao.UserDao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.repository.PatientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class PatientRepositoryImpl implements PatientRepository {
    private final UserDao userDao;
    private final PatientDao patientDao;
    private final DBManager dbManager;

    public PatientRepositoryImpl(UserDao userDao, PatientDao patientDao, DBManager dbManager) {
        this.userDao = userDao;
        this.patientDao = patientDao;
        this.dbManager = dbManager;
    }

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
            dbManager.rollbackConnection(connection);
            throw new DBException(e.getMessage());
        } finally {
            dbManager.closeConnection(connection);
        }
    }
}