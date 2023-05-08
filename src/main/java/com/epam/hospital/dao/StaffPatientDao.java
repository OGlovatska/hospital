package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.StaffPatient;

public interface StaffPatientDao extends Dao<StaffPatient> {

    int patientsOfStaffCount(long staffId) throws DBException;

    int staffOfPatientCount(long patientId) throws DBException;

    void delete(long patientId) throws DBException;
}