package com.epam.hospital.service;

import com.epam.hospital.dao.impl.AppointmentDaoImpl;
import com.epam.hospital.dao.impl.HospitalisationDaoImpl;
import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.dao.impl.StaffDaoImpl;
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
import com.epam.hospital.pagination.Pageable;
import com.epam.hospital.pagination.Sort;
import com.epam.hospital.util.AppointmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.AppointmentUtil.createAppointmentTo;
import static com.epam.hospital.util.HospitalisationUtil.createHospitalisationTo;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class HospitalisationService {
    private static final Logger LOG = LoggerFactory.getLogger(HospitalisationService.class);
    private final HospitalisationDaoImpl hospitalisationDao;
    private final PatientDaoImpl patientDao;
    private final StaffDaoImpl staffDao;
    private final AppointmentDaoImpl appointmentDao;

    public HospitalisationService(HospitalisationDaoImpl hospitalisationDao, PatientDaoImpl patientDao,
                                  StaffDaoImpl staffDao, AppointmentDaoImpl appointmentDao) {
        this.hospitalisationDao = hospitalisationDao;
        this.patientDao = patientDao;
        this.staffDao = staffDao;
        this.appointmentDao = appointmentDao;
    }

    public int getAllHospitalisationsOfPatientCount(int patientId) {
        try {
            return hospitalisationDao.getAllHospitalisationsOfPatientCount(patientId);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllHospitalisationsOfPatientCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
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
                List<AppointmentTo> appointmentTos = getAppointmentTos(hospitalisation, patient);
                if (patient != null){
                    HospitalisationTo hospitalisationTo = createHospitalisationTo(hospitalisation, patient, appointmentTos);
                    hospitalisationTos.add(hospitalisationTo);
                }
            }
            return hospitalisationTos;
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllHospitalisationsOfPatient() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        } catch (NoSuchElementException e){
            LOG.error("Patient not found, message = {}", e.getMessage());
            throw new IllegalRequestDataException(PATIENT_NOT_FOUND);
        }
    }

    private List<AppointmentTo> getAppointmentTos(Hospitalisation hospitalisation, Patient patient) throws DBException {
        List<Appointment> appointments = appointmentDao.getAllAppointmentsByHospitalisation(hospitalisation.getId());
        List<AppointmentTo> appointmentTos = new ArrayList<>();
        if (appointments != null && !appointments.isEmpty()){
            for (Appointment appointment : appointments){
                Staff staff = staffDao.get(appointment.getStaffId()).orElse(null);
                if (patient != null && staff != null){
                    appointmentTos.add(createAppointmentTo(appointment, patient, staff));
                }
            }
        }
        return appointmentTos;
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
                    LOG.error("Can't add new hospitalisation for undischarged patient");
                    throw new IllegalRequestDataException(NOT_DISCHARGED);
                }
                hospitalisationDao.save(hospitalisation);
            } catch (DBException e) {
                LOG.error("Exception has occurred during executing saveHospitalisation() method", e);
                throw new ApplicationException(APP_ERROR);
            }
        } else {
            LOG.error("Only ADMIN can get list of all hospitalisations, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(NOT_ADMIN);
        }
    }

    public HospitalisationTo getPatientCurrentHospitalisation(long patientId) {
        try {
            Hospitalisation hospitalisation = hospitalisationDao.getPatientCurrentHospitalisation(patientId).orElse(null);
            return hospitalisation != null ? createHospitalisationTo(hospitalisation) : null;
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getPatientCurrentHospitalisation() method", e);
            throw new ApplicationException(APP_ERROR);
        }
    }

    public void dischargePatient(UserTo user, int hospitalisationId, LocalDate endDate) {
        if (user.getRole().equals(Role.DOCTOR)) {
            try {
                Hospitalisation hospitalisation = hospitalisationDao.get(hospitalisationId).orElseThrow();
                if (hospitalisation != null) {
                    if (hospitalisation.getDiagnosis() != null && !hospitalisation.getDiagnosis().isEmpty()) {
                        hospitalisation.setEndDate(endDate);
                        hospitalisation.setStatus(HospitalisationStatus.DISCHARGED.name());
                        hospitalisationDao.update(hospitalisation);
                    } else {
                        LOG.error("Only patients with determined diagnosis can be discharged");
                        throw new IllegalRequestDataException(NO_DIAGNOSIS);
                    }
                }
            } catch (DBException e) {
                LOG.error("Exception has occurred during executing dischargePatient() method", e);
                throw new ApplicationException(APP_ERROR);
            } catch (NoSuchElementException e){
                LOG.error("Can't add diagnosis to not hospitalised patient");
                throw new IllegalRequestDataException(PATIENT_NOT_HOSPITALISED);
            }
        } else {
            LOG.error("Only DOCTOR can discharge patient, current user role is {}", user.getRole());
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
                LOG.error("Exception has occurred during executing dischargePatient() method, message = {}", e.getMessage());
                throw new ApplicationException(APP_ERROR);
            } catch (NoSuchElementException e) {
                LOG.error("Can't add diagnosis to not hospitalised patient");
                throw new IllegalRequestDataException(PATIENT_NOT_HOSPITALISED);
            }
        } else {
            LOG.error("Only DOCTOR can discharge patient, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(NOT_DOCTOR);
        }
    }
}
