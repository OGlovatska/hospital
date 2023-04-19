package com.epam.hospital.repository;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;

import java.util.Optional;

public interface PatientRepository {

    Optional<Patient> save(User user, Patient patient) throws DBException;
}