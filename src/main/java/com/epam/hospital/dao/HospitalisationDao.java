package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.util.pagination.Pageable;

import java.util.List;
import java.util.Optional;

public interface HospitalisationDao {

    Optional<Hospitalisation> get(long id) throws DBException;

    List<Hospitalisation> getAll() throws DBException;

    Optional<Hospitalisation> save(Hospitalisation t) throws DBException;

    Optional<Hospitalisation> update(Hospitalisation t) throws DBException;

    List<Hospitalisation> getAll(Pageable pageable) throws DBException;

    List<Hospitalisation> getAllHospitalisationsOfPatient(int patientId, Pageable pageable) throws DBException;

    int getAllHospitalisationsOfPatientCount(int patientId) throws DBException;

    Optional<Hospitalisation> getPatientCurrentHospitalisation(long patientId) throws DBException;
}