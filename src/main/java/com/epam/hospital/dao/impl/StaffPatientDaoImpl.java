package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.StaffPatientDao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.StaffPatient;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.field.CommonFields.*;
import static com.epam.hospital.dao.constant.query.StaffPatientQueries.*;

public class StaffPatientDaoImpl implements StaffPatientDao {
    private final DBManager dbManager;

    public StaffPatientDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public Optional<StaffPatient> get(long id) throws DBException {
        return Optional.empty();
    }

    @Override
    public List<StaffPatient> getAll() throws DBException {
        return null;
    }

    @Override
    public Optional<StaffPatient> save(StaffPatient assignment) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_STAFF_PATIENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, assignment.getStaffId());
            statement.setInt(2, assignment.getPatientId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                assignment.setId(resultSet.getInt(1));
                return Optional.of(assignment);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<StaffPatient> update(StaffPatient assignment) throws DBException {
        return Optional.empty();
    }

    public int patientsOfStaffCount(long staffId) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNT_PATIENTS_OF_STAFF)) {
            statement.setLong(1, staffId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(PATIENTS);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return 0;
    }

    public int staffOfPatientCount(long patientId) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNT_STAFF_OF_PATIENT)) {
            statement.setLong(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(STAFF);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return 0;
    }

    public void delete(long patientId) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STAFF_PATIENT)) {
            statement.setLong(1, patientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    private StaffPatient getStaffPatient(ResultSet resultSet) throws SQLException {
        StaffPatient assignment = new StaffPatient();
        assignment.setId(resultSet.getInt(ID));
        assignment.setStaffId(resultSet.getInt(STAFF_ID));
        assignment.setPatientId(resultSet.getInt(PATIENT_ID));
        return assignment;
    }
}