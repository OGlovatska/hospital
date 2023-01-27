package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.Dao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.pagination.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.query.AppointmentQueries.*;
import static com.epam.hospital.dao.constant.field.AppointmentFields.*;
import static com.epam.hospital.dao.constant.field.CommonFields.*;

public class AppointmentDaoImpl implements Dao<Appointment> {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentDaoImpl.class);
    private final DBManager dbManager = MySQLDBManager.getInstance();

    @Override
    public Optional<Appointment> get(long id) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_APPOINTMENT)) {
            statement.setInt(1, (int) id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getAppointment(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET APPOINTMENT BY ID query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    @Override
    public List<Appointment> getAll() throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS, new Pageable().query()));
    }

    public List<Appointment> getAll(Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS, pageable.query()));
    }

    public List<Appointment> getAllAppointmentsOfStaff(int staffId, Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS_FOR_STAFF, staffId, pageable.query()));
    }

    public List<Appointment> getAllAppointmentsByHospitalisation(long hospitalisationId) throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS_FOR_HOSPITALISATION, hospitalisationId));
    }

    private List<Appointment> getAll(String query) throws DBException {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                appointments.add(getAppointment(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET ALL APPOINTMENTS query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return appointments;
    }

    public List<Appointment> getAllByDate(Pageable pageable, String orderBy) throws DBException{
        return getAll(String.format(GET_ALL_APPOINTMENTS_BY_DATE, orderBy, pageable.query()));
    }

    @Override
    public Optional<Appointment> save(Appointment appointment) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_APPOINTMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, appointment.getHospitalisationId());
            statement.setInt(2, appointment.getPatientId());
            statement.setInt(3, appointment.getStaffId());
            statement.setObject(4, appointment.getDateTime());
            statement.setString(5, appointment.getType());
            statement.setString(6, appointment.getDescription());
            statement.setString(7, appointment.getConclusion());
            statement.setString(8, appointment.getStatus());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                appointment.setId(resultSet.getInt(1));
                return Optional.of(appointment);
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing SAVE APPOINTMENT query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Appointment> update(Appointment appointment) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_APPOINTMENT)) {
            statement.setInt(1, appointment.getStaffId());
            statement.setDate(2, Date.valueOf(String.valueOf(appointment.getDateTime())));
            statement.setString(3, appointment.getType());
            statement.setString(4, appointment.getDescription());
            statement.setString(5, appointment.getConclusion());
            statement.setString(6, appointment.getStatus());
            statement.setInt(7, appointment.getId());
            statement.executeUpdate();
            return get(appointment.getId());
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing UPDATE USER query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
    }

    public int appointmentsCount() throws DBException {
        return getCount(GET_APPOINTMENTS_COUNT);
    }

    public int appointmentsCount(int staffId) throws DBException {
        return getCount(String.format(GET_APPOINTMENTS_OF_STAFF_COUNT, staffId));
    }

    public int appointmentsForDateCount(String date) throws DBException{
        return getCount(String.format(GET_APPOINTMENTS_FOR_DATE_COUNT, date));
    }

    private int getCount(String query) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(APPOINTMENTS);
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET APPOINTMENTS COUNT query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return 0;
    }

    private Appointment getAppointment(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getInt(ID));
        appointment.setHospitalisationId(resultSet.getInt(HOSPITALISATION_ID));
        appointment.setPatientId(resultSet.getInt(PATIENT_ID));
        appointment.setStaffId(resultSet.getInt(STAFF_ID));
        appointment.setDateTime(Timestamp.valueOf(resultSet.getString(DATE)).toLocalDateTime());
        appointment.setType(resultSet.getString(TYPE));
        appointment.setDescription(resultSet.getString(DESCRIPTION));
        appointment.setConclusion(resultSet.getString(CONCLUSION));
        appointment.setStatus(resultSet.getString(STATUS));
        return appointment;
    }
}
