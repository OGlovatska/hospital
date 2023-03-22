package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.Dao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.listener.DBContextListener;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.enums.Gender;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.util.pagination.Pageable;
import jakarta.servlet.ServletContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.field.CommonFields.*;
import static com.epam.hospital.dao.constant.field.PatientFields.*;
import static com.epam.hospital.dao.constant.field.UserFields.*;
import static com.epam.hospital.dao.constant.query.PatientQuery.*;

public class PatientDaoImpl implements Dao<Patient> {
    private final DBManager dbManager;

    public PatientDaoImpl() {
        ServletContext servletContext = DBContextListener.getServletContext();
        this.dbManager = (DBManager) servletContext.getAttribute("dbManager");
    }

    @Override
    public Optional<Patient> get(long id) throws DBException {
        return get(String.format(GET_PATIENT_BY_ID, id));
    }

    public Optional<Patient> getByUserId(long userId) throws DBException {
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
            throw new DBException(e.getMessage());
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
            throw new DBException(e.getMessage());
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
            statement.setObject(3, patient.getGender().getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                patient.setId(resultSet.getInt(1));
                return Optional.of(patient);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
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
            throw new DBException(e.getMessage());
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
            throw new DBException(e.getMessage());
        }
        return 0;
    }

    private Patient getPatient(ResultSet resultSet) throws SQLException {
        return new Patient.Builder().id(resultSet.getInt(ID))
                .userId(resultSet.getInt(USER_ID)).firstName(resultSet.getString(FIRST_NAME))
                .lastName(resultSet.getString(LAST_NAME)).email(resultSet.getString(EMAIL))
                .role(Role.valueOf(resultSet.getString(ROLE)))
                .dateOfBirth(resultSet.getDate(DATE_OF_BIRTH).toLocalDate())
                .gender(Gender.valueOf(resultSet.getString(GENDER).toUpperCase()))
                .build();
    }
}
