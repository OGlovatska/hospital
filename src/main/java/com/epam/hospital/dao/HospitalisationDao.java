package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.util.pagination.Pageable;

import java.util.List;
import java.util.Optional;

public interface HospitalisationDao extends Dao<Hospitalisation> {

    List<Hospitalisation> getAll(Pageable pageable) throws DBException;

    List<Hospitalisation> getAllHospitalisationsOfPatient(int patientId, Pageable pageable) throws DBException;

    int getAllHospitalisationsOfPatientCount(int patientId) throws DBException;

    Optional<Hospitalisation> getPatientCurrentHospitalisation(long patientId) throws DBException;
}