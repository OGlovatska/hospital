package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.Dao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.util.pagination.Pageable;

import java.sql.*;
import java.util.*;

import static com.epam.hospital.dao.constant.field.CommonFields.*;
import static com.epam.hospital.dao.constant.field.StaffFields.*;
import static com.epam.hospital.dao.constant.field.UserFields.*;
import static com.epam.hospital.dao.constant.query.StaffQueries.*;

public class StaffDaoImpl implements Dao<Staff> {
    private final DBManager dbManager;

    public StaffDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public Optional<Staff> get(long id) throws DBException {
        return get(String.format(GET_STAFF_BY_ID, id));
    }

    public Optional<Staff> getByUserId(long userId) throws DBException {
        return get(String.format(GET_STAFF_BY_USER_ID, userId));
    }

    private Optional<Staff> get(String query) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getStaff(resultSet));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Staff> getAll() throws DBException {
        return null;
    }

    @Override
    public Optional<Staff> save(Staff staff) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            return save(staff, connection);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Optional<Staff> save(Staff staff, Connection connection) throws DBException {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_STAFF, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, staff.getUserId());
            statement.setString(2, staff.getSpecialisation());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                staff.setId(resultSet.getInt(1));
                return Optional.of(staff);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Staff> update(Staff staff) throws DBException {
        return Optional.empty();
    }

    public Map<Staff, Integer> getAllStaff(Pageable pageable) throws DBException {
        String query = String.format(GET_ALL_STAFF, pageable.query());
        Map<Staff, Integer> staff = new LinkedHashMap<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                staff.put(getStaff(resultSet), resultSet.getInt(PATIENTS));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return staff;
    }

    public List<Staff> getAllStaff(int patientId, Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_STAFF_OF_PATIENT, patientId, pageable.query()));
    }

    public List<Staff> getAllStaffNotAssignedToPatient(int patientId) throws DBException{
         return getAll(String.format(GET_ALL_STAFF_NOT_ASSIGNED_TO_PATIENT, patientId));
    }

    private List<Staff> getAll(String query) throws DBException{
        List<Staff> staff = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                staff.add(getStaff(resultSet));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return staff;
    }

    public int staffCount() throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STAFF_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(STAFF);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return 0;
    }

    private Staff getStaff(ResultSet resultSet) throws SQLException {
        return new Staff.Builder().id(resultSet.getInt(ID)).userId(resultSet.getInt(USER_ID))
                .firstName(resultSet.getString(FIRST_NAME)).lastName(resultSet.getString(LAST_NAME))
                .email(resultSet.getString(EMAIL)).role(Role.valueOf(resultSet.getString(ROLE)))
                .specialisation(resultSet.getString(SPECIALISATION)).build();
    }
}