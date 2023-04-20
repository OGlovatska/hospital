package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.util.pagination.Pageable;

import java.util.List;
import java.util.Optional;

public interface AppointmentDao extends Dao<Appointment> {

    List<Appointment> getAll(Pageable pageable) throws DBException;

    List<Appointment> getAllAppointmentsOfStaff(int staffId, Pageable pageable) throws DBException;

    List<Appointment> getAllAppointmentsByHospitalisation(long hospitalisationId, Pageable pageable) throws DBException;

    List<Appointment> getAllAppointmentsOfPatient(int patientId, Pageable pageable) throws DBException;

    List<Appointment> getAllByDate(Pageable pageable, String orderBy) throws DBException;

    int appointmentsCount() throws DBException;

    int appointmentsCountForStaff(int staffId) throws DBException;

    int appointmentsCountForPatient(int patientId) throws DBException;

    int appointmentsCountForHospitalisation(int hospitalisationId) throws DBException;

    int appointmentsForDateCount(String date) throws DBException;
}