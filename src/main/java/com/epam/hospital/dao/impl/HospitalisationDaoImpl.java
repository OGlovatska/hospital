package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.Dao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.pagination.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.query.HospitalisationQueries.*;
import static com.epam.hospital.dao.constant.field.HospitalisationFields.*;
import static com.epam.hospital.dao.constant.field.CommonFields.*;

public class HospitalisationDaoImpl implements Dao<Hospitalisation> {
    private static final Logger LOG = LoggerFactory.getLogger(HospitalisationDaoImpl.class);
    private final DBManager dbManager = MySQLDBManager.getInstance();

    @Override
    public Optional<Hospitalisation> get(long id) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_HOSPITALISATION)) {
            statement.setInt(1, (int) id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getHospitalisation(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET HOSPITALISATION BY ID query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    @Override
    public List<Hospitalisation> getAll() throws DBException {
        return get(String.format(GET_ALL_HOSPITALISATIONS, new Pageable().query()));
    }

    public List<Hospitalisation> getAll(Pageable pageable) throws DBException {
        return get(String.format(GET_ALL_HOSPITALISATIONS, pageable.query()));
    }

    public List<Hospitalisation> getAllHospitalisationsOfPatient(int patientId, Pageable pageable) throws DBException {
        return get(String.format(GET_ALL_HOSPITALISATIONS_OF_PATIENT, patientId, pageable.query()));
    }

    private List<Hospitalisation> get(String query) throws DBException {
        List<Hospitalisation> hospitalisations = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hospitalisations.add(getHospitalisation(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET ALL HOSPITALISATIONS query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return hospitalisations;
    }

    @Override
    public Optional<Hospitalisation> save(Hospitalisation hospitalisation) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_HOSPITALISATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, hospitalisation.getPatientId());
            statement.setDate(2, hospitalisation.getStartDate() != null ? Date.valueOf(hospitalisation.getStartDate()) : null);
            statement.setDate(3, hospitalisation.getEndDate() != null ? Date.valueOf(hospitalisation.getEndDate()) : null);
            statement.setString(4, hospitalisation.getStatus());
            statement.setString(5, hospitalisation.getDiagnosis());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                hospitalisation.setId(resultSet.getInt(1));
                return Optional.of(hospitalisation);
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing SAVE HOSPITALISATION query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Hospitalisation> update(Hospitalisation hospitalisation) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_HOSPITALISATION)) {
            statement.setDate(1, hospitalisation.getStartDate() != null ? Date.valueOf(hospitalisation.getStartDate()) : null);
            statement.setDate(2, hospitalisation.getEndDate() != null ? Date.valueOf(hospitalisation.getEndDate()) : null);
            statement.setString(3, hospitalisation.getStatus());
            statement.setString(4, hospitalisation.getDiagnosis());
            statement.setInt(5, hospitalisation.getId());
            statement.executeUpdate();
            return get(hospitalisation.getId());
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing UPDATE HOSPITALISATION query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
    }

    public int getHospitalisationsCount() throws DBException {
        return getCount(GET_HOSPITALISATIONS_COUNT);
    }

    public int getAllHospitalisationsOfPatientCount(int patientId) throws DBException {
        return getCount(String.format(GET_HOSPITALISATIONS_OF_PATIENT_COUNT, patientId));
    }

    private int getCount(String query) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(HOSPITALISATIONS);
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET HOSPITALISATION COUNT query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return 0;
    }

    public Optional<Hospitalisation> getPatientCurrentHospitalisation(long patientId) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PATIENT_CURRENT_HOSPITALISATION)) {
            statement.setInt(1, (int) patientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getHospitalisation(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Exception has occurred during executing GET PATIENT CURRENT HOSPITALISATION query, error code={}, message={}",
                    e.getErrorCode(), e.getMessage());
            throw new DBException();
        }
        return Optional.empty();
    }

    private Hospitalisation getHospitalisation(ResultSet resultSet) throws SQLException {
        Hospitalisation hospitalisation = new Hospitalisation();
        hospitalisation.setId(resultSet.getInt(ID));
        hospitalisation.setPatientId(resultSet.getInt(PATIENT_ID));
        hospitalisation.setStartDate(resultSet.getDate(START_DATE).toLocalDate());
        hospitalisation.setStatus(resultSet.getString(STATUS));
        hospitalisation.setDiagnosis(resultSet.getString(DIAGNOSIS));

        Date endDate = resultSet.getDate(END_DATE);
        hospitalisation.setEndDate(endDate != null ? endDate.toLocalDate() : null);
        return hospitalisation;
    }
}