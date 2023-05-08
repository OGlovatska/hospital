package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.AppointmentDao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.enums.AppointmentType;
import com.epam.hospital.util.pagination.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.query.AppointmentQueries.*;
import static com.epam.hospital.dao.constant.field.AppointmentFields.*;
import static com.epam.hospital.dao.constant.field.CommonFields.*;

public class AppointmentDaoImpl implements AppointmentDao {
    private final DBManager dbManager;

    public AppointmentDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

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
            throw new DBException(e.getMessage());
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

    public List<Appointment> getAllAppointmentsOfStaff(long staffId, Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS_FOR_STAFF, staffId, pageable.query()));
    }

    public List<Appointment> getAllAppointmentsByHospitalisation(long hospitalisationId, Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS_FOR_HOSPITALISATION, hospitalisationId, pageable.query()));
    }

    public List<Appointment> getAllAppointmentsOfPatient(long patientId, Pageable pageable) throws DBException {
        return getAll(String.format(GET_ALL_APPOINTMENTS_FOR_PATIENT, patientId, pageable.query()));
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
            throw new DBException(e.getMessage());
        }
        return appointments;
    }

    @Override
    public Optional<Appointment> save(Appointment appointment) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_APPOINTMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, appointment.getHospitalisationId());
            statement.setInt(2, appointment.getPatientId());
            statement.setInt(3, appointment.getStaffId());
            statement.setObject(4, appointment.getDateTime());
            statement.setString(5, appointment.getType().toString());
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
            throw new DBException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Appointment> update(Appointment appointment) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_APPOINTMENT)) {
            statement.setInt(1, appointment.getStaffId());
            statement.setDate(2, Date.valueOf(String.valueOf(appointment.getDateTime())));
            statement.setString(3, appointment.getType().toString());
            statement.setString(4, appointment.getDescription());
            statement.setString(5, appointment.getConclusion());
            statement.setString(6, appointment.getStatus());
            statement.setInt(7, appointment.getId());
            statement.executeUpdate();
            return get(appointment.getId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public int appointmentsCount() throws DBException {
        return getCount(GET_APPOINTMENTS_COUNT);
    }

    public int appointmentsCountForStaff(long staffId) throws DBException {
        return getCount(String.format(GET_APPOINTMENTS_OF_STAFF_COUNT, staffId));
    }

    public int appointmentsCountForPatient(long patientId) throws DBException {
        return getCount(String.format(GET_APPOINTMENTS_OF_PATIENT_COUNT, patientId));
    }

    public int appointmentsCountForHospitalisation(long hospitalisationId) throws DBException {
        return getCount(String.format(GET_APPOINTMENTS_OF_HOSPITALISATION_COUNT, hospitalisationId));
    }

    private int getCount(String query) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(APPOINTMENTS);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return 0;
    }

    private Appointment getAppointment(ResultSet resultSet) throws SQLException {
        return new Appointment.Builder().id(resultSet.getInt(ID))
                .hospitalisationId(resultSet.getInt(HOSPITALISATION_ID))
                .patientId(resultSet.getInt(PATIENT_ID)).staffId(resultSet.getInt(STAFF_ID))
                .dateTime(Timestamp.valueOf(resultSet.getString(DATE)).toLocalDateTime())
                .type(AppointmentType.valueOf(resultSet.getString(TYPE))).description(resultSet.getString(DESCRIPTION))
                .conclusion(resultSet.getString(CONCLUSION)).status(resultSet.getString(STATUS))
                .build();
    }
}