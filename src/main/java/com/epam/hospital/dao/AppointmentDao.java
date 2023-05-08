package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.util.pagination.Pageable;

import java.util.List;

public interface AppointmentDao extends Dao<Appointment> {

    List<Appointment> getAll(Pageable pageable) throws DBException;

    List<Appointment> getAllAppointmentsOfStaff(long staffId, Pageable pageable) throws DBException;

    List<Appointment> getAllAppointmentsByHospitalisation(long hospitalisationId, Pageable pageable) throws DBException;

    List<Appointment> getAllAppointmentsOfPatient(long patientId, Pageable pageable) throws DBException;

    int appointmentsCount() throws DBException;

    int appointmentsCountForStaff(long staffId) throws DBException;

    int appointmentsCountForPatient(long patientId) throws DBException;

    int appointmentsCountForHospitalisation(long hospitalisationId) throws DBException;
}