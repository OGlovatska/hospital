package com.epam.hospital.service;

import com.epam.hospital.dao.impl.*;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.exception.IllegalRequestDataException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.enums.HospitalisationStatus;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.AppointmentUtil;
import com.epam.hospital.util.pagination.Pageable;
import com.epam.hospital.util.pagination.Sort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.AppointmentUtil.createAppointmentTo;
import static com.epam.hospital.util.HospitalisationUtil.createHospitalisationTo;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class HospitalisationService {
    private final HospitalisationDaoImpl hospitalisationDao;
    private final PatientDaoImpl patientDao;
    private final StaffPatientDaoImpl staffPatientDao;
    private final AppointmentDaoImpl appointmentDao;
    private final StaffDaoImpl staffDao;

    public HospitalisationService(HospitalisationDaoImpl hospitalisationDao, PatientDaoImpl patientDao,
                                  StaffPatientDaoImpl staffPatientDao, AppointmentDaoImpl appointmentDao, StaffDaoImpl staffDao) {
        this.hospitalisationDao = hospitalisationDao;
        this.patientDao = patientDao;
        this.staffPatientDao = staffPatientDao;
        this.appointmentDao = appointmentDao;
        this.staffDao = staffDao;
    }

    public int getAllHospitalisationsOfPatientCount(int patientId) {
        try {
            return hospitalisationDao.getAllHospitalisationsOfPatientCount(patientId);
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public List<HospitalisationTo> getAllHospitalisationsOfPatient(int patientId, int offset, int limit, String orderBy,
                                                                   String direction) {
        try {
            List<HospitalisationTo> hospitalisationTos = new ArrayList<>();
            List<Hospitalisation> hospitalisations = hospitalisationDao.getAllHospitalisationsOfPatient(patientId,
                    new Pageable(offset, limit, new Sort(orderBy, direction)));

            for (Hospitalisation hospitalisation : hospitalisations) {
                Patient patient = patientDao.get(patientId).orElse(null);
                if (patient != null) {
                    HospitalisationTo hospitalisationTo = createHospitalisationTo(hospitalisation, patient);
                    hospitalisationTos.add(hospitalisationTo);
                }
            }
            return hospitalisationTos;
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        } catch (NoSuchElementException e) {
            throw new IllegalRequestDataException(PATIENT_NOT_FOUND);
        }
    }

    public List<HospitalisationTo> getAllHospitalisationsWithAppointments(int patientId) {
        List<HospitalisationTo> hospitalisations = getAllHospitalisationsOfPatient(patientId, 0, 0,
                "id", Sort.Direction.ASC.name());
        if (hospitalisations != null && !hospitalisations.isEmpty()){
            for (HospitalisationTo hospitalisation : hospitalisations){
                try {
                    List<Appointment> appointments = appointmentDao.getAllAppointmentsByHospitalisation(hospitalisation.getId(),
                            new Pageable());
                    List<AppointmentTo> appointmentTos = new ArrayList<>();
                    if (appointments != null && !appointments.isEmpty()){
                        for (Appointment appointment : appointments){
                            staffDao.get(appointment.getStaffId())
                                    .ifPresent(staff -> appointmentTos.add(createAppointmentTo(appointment, staff)));
                        }
                    }
                    hospitalisation.setAppointments(appointmentTos);
                } catch (DBException e) {
                    throw new ApplicationException(e.getMessage(), APP_ERROR);
                }
            }
        }
        return hospitalisations;
    }

    public List<String> getHospitalisationStatuses() {
        return Arrays
                .stream(HospitalisationStatus.values())
                .map(HospitalisationStatus::name)
                .collect(Collectors.toList());
    }

    public void saveHospitalisation(UserTo user, Hospitalisation hospitalisation) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN)) {
            try {
                Hospitalisation checkIfExist = hospitalisationDao.getPatientCurrentHospitalisation(hospitalisation.getPatientId()).orElse(null);
                if (checkIfExist != null) {
                    throw new IllegalRequestDataException(NOT_DISCHARGED);
                }
                hospitalisationDao.save(hospitalisation);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            }
        } else {
            throw new IllegalRequestDataException(NOT_ADMIN);
        }
    }

    public HospitalisationTo getPatientCurrentHospitalisation(long patientId) {
        try {
            Hospitalisation hospitalisation = hospitalisationDao.getPatientCurrentHospitalisation(patientId).orElse(null);
            return hospitalisation != null ? createHospitalisationTo(hospitalisation) : null;
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public void dischargePatient(UserTo user, int hospitalisationId, LocalDate endDate) {
        if (user.getRole().equals(Role.DOCTOR)) {
            try {
                Hospitalisation hospitalisation = hospitalisationDao.get(hospitalisationId).orElseThrow();
                if (hospitalisation != null) {
                    if (hospitalisation.getDiagnosis() != null && !hospitalisation.getDiagnosis().isEmpty()) {
                        hospitalisation.setEndDate(endDate);
                        hospitalisation.setStatus(HospitalisationStatus.DISCHARGED);
                        hospitalisationDao.update(hospitalisation);

                        staffPatientDao.delete(hospitalisation.getPatientId());
                    } else {
                        throw new IllegalRequestDataException(NO_DIAGNOSIS);
                    }
                }
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            } catch (NoSuchElementException e) {
                throw new IllegalRequestDataException(PATIENT_NOT_HOSPITALISED);
            }
        } else {
            throw new IllegalRequestDataException(NOT_DOCTOR);
        }
    }

    public void determinePatientDiagnosis(UserTo user, int hospitalisationId, String diagnosis) {
        if (user.getRole().equals(Role.DOCTOR)) {
            try {
                Hospitalisation hospitalisation = hospitalisationDao.get(hospitalisationId).orElseThrow();
                hospitalisation.setDiagnosis(diagnosis);
                hospitalisationDao.update(hospitalisation);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            } catch (NoSuchElementException e) {
                throw new IllegalRequestDataException(PATIENT_NOT_HOSPITALISED);
            }
        } else {
            throw new IllegalRequestDataException(NOT_DOCTOR);
        }
    }

    public HospitalisationTo getHospitalisation(long hospitalisationId) {
        try {
            Hospitalisation hospitalisation = hospitalisationDao.get(hospitalisationId).orElseThrow();
            return createHospitalisationTo(hospitalisation);
        } catch (DBException | NoSuchElementException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }
}
