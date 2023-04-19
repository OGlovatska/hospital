package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.StaffPatient;

import java.util.List;
import java.util.Optional;

public interface StaffPatientDao {

    Optional<StaffPatient> get(long id) throws DBException;

    List<StaffPatient> getAll() throws DBException;

    Optional<StaffPatient> save(StaffPatient assignment) throws DBException;

    Optional<StaffPatient> update(StaffPatient assignment) throws DBException;

    int patientsOfStaffCount(long staffId) throws DBException;

    int staffOfPatientCount(int patientId) throws DBException;

    void delete(int patientId) throws DBException;
}