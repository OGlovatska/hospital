package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.Dao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.pagination.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.field.CommonFields.*;
import static com.epam.hospital.dao.constant.field.PatientFields.*;
import static com.epam.hospital.dao.constant.field.UserFields.*;
import static com.epam.hospital.dao.constant.query.PatientQuery.*;

public class PatientDaoImpl implements Dao<Patient> {
    private static final Logger LOG = LoggerFactory.getLogger(PatientDaoImpl.class);
    private final DBManager dbManager = MySQLDBManager.getInstance();

    @Override
    public Optional<Patient> get(long id) throws DBException {
        return get(String.format(GET_PATIENT_BY_ID, id));
    }

    public Optional<Patient> getPatientByUserId(long userId) throws DBException {
        return get(String.format(GET_PATIENT_BY_USER_ID, userId));
    }

    private Optional<Patient> get(String query) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getPatient(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET PATIENT query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> getAll() throws DBException {
        return null;
    }

    @Override
    public Optional<Patient> save(Patient patient) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            return save(patient, connection);
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing SAVE PATIENT query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
    }

    @Override
    public Optional<Patient> update(Patient patient) throws DBException {
        return Optional.empty();
    }

    public List<Patient> getAllPatients(Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_PATIENTS, pageable.query()));
    }

    public List<Patient> getAllPatients(long staffId, Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_PATIENTS_OF_STAFF, staffId, pageable.query()));
    }

    public List<Patient> getAllPatientsNotAssignedToStaff(long staffId) throws DBException {
        return getAll(String.format(GET_ALL_PATIENTS_NOT_ASSIGNED_TO_STAFF, staffId));
    }

    public Optional<Patient> save(Patient patient, Connection connection) throws DBException {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_PATIENT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, patient.getUserId());
            statement.setDate(2, Date.valueOf(patient.getDateOfBirth()));
            statement.setString(3, patient.getGender());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                patient.setId(resultSet.getInt(1));
                return Optional.of(patient);
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing SAVE STAFF query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    private List<Patient> getAll(String query) throws DBException {
        List<Patient> users = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getPatient(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET ALL PATIENTS query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return users;
    }

    public int patientsCount() throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PATIENTS_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(PATIENTS);
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET STAFF COUNT query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return 0;
    }

    private Patient getPatient(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt(ID));
        patient.setUserId(resultSet.getInt(USER_ID));
        patient.setFirstName(resultSet.getString(FIRST_NAME));
        patient.setLastName(resultSet.getString(LAST_NAME));
        patient.setEmail(resultSet.getString(EMAIL));
        patient.setRole(Role.valueOf(resultSet.getString(ROLE)));
        patient.setDateOfBirth(resultSet.getDate(DATE_OF_BIRTH).toLocalDate());
        patient.setGender(resultSet.getString(GENDER));
        return patient;
    }
}
