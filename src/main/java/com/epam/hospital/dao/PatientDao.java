package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.util.pagination.Pageable;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface PatientDao extends Dao<Patient> {

    Optional<Patient> getByUserId(long userId) throws DBException;

    List<Patient> getAllPatients(Pageable pageable) throws DBException;

    List<Patient> getAllPatients(long staffId, Pageable pageable) throws DBException;

    List<Patient> getAllPatientsNotAssignedToStaff(long staffId) throws DBException;

    Optional<Patient> save(Patient patient, Connection connection) throws DBException;

    int patientsCount() throws DBException;
}