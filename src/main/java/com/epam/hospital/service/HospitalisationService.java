package com.epam.hospital.service;

import com.epam.hospital.dao.impl.AppointmentDaoImpl;
import com.epam.hospital.dao.impl.HospitalisationDaoImpl;
import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.exception.IllegalRequestDataException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.enums.HospitalisationStatus;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.pagination.Pageable;
import com.epam.hospital.pagination.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.APP_ERROR;
import static com.epam.hospital.exception.ErrorType.WRONG_REQUEST;
import static com.epam.hospital.util.HospitalisationUtil.createHospitalisationTo;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class HospitalisationService {
    private static final Logger LOG = LoggerFactory.getLogger(HospitalisationService.class);
    private final HospitalisationDaoImpl hospitalisationDao = new HospitalisationDaoImpl();
    private final PatientDaoImpl patientDao = new PatientDaoImpl();
    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

    public Map<Integer, List<HospitalisationTo>> getAllHospitalisations(UserTo user, String offset, String limit, String orderBy,
                                                                        String direction) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.PATIENT.name())) {
            try {
                Patient patient = patientDao.getPatientByUserId(user.getId()).orElse(null);
                if (patient != null){
                    List<HospitalisationTo> hospitalisationTos = new ArrayList<>();
                    List<Hospitalisation> hospitalisations = hospitalisationDao.getAllHospitalisationsOfPatient(patient.getId(),
                            new Pageable(offset, limit, new Sort(orderBy, direction)));

                    for (Hospitalisation hospitalisation : hospitalisations) {
                        HospitalisationTo hospitalisationTo = createHospitalisationTo(hospitalisation, patient);
                        hospitalisationTos.add(hospitalisationTo);
                    }
                    Map<Integer, List<HospitalisationTo>> hospitalisationsMap = new HashMap<>();
                    int hospitalisationsCount = hospitalisationDao.getAllHospitalisationsOfPatientCount(patient.getId());
                    hospitalisationsMap.put(hospitalisationsCount, hospitalisationTos);
                    return hospitalisationsMap;
                } else {
                    LOG.error("Patient must not be null, current user role {}", user.getRole());
                    throw new IllegalRequestDataException(WRONG_REQUEST);
                }

            } catch (DBException e) {
                LOG.error("Exception has occurred during executing getAllHospitalisations() method", e);
                throw new IllegalRequestDataException(APP_ERROR);
            }
        } else {
            LOG.error("Only PATIENT can get list of all hospitalisations, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    public int getHospitalisationsCount() {
        try {
            return hospitalisationDao.getHospitalisationsCount();
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getHospitalisationsCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public int getAllHospitalisationsOfPatientCount(int patientId) {
        try {
            return hospitalisationDao.getAllHospitalisationsOfPatientCount(patientId);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllHospitalisationsOfPatientCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public List<HospitalisationTo> getAllHospitalisationsOfPatient(int patientId, String offset, String limit, String orderBy,
                                                                   String direction) {
        try {
            List<HospitalisationTo> hospitalisationTos = new ArrayList<>();
            List<Hospitalisation> hospitalisations = hospitalisationDao.getAllHospitalisationsOfPatient(patientId,
                    new Pageable(offset, limit, new Sort(orderBy, direction)));

            for (Hospitalisation hospitalisation : hospitalisations) {
                Patient patient = patientDao.get(patientId).orElseThrow();
                HospitalisationTo hospitalisationTo = createHospitalisationTo(hospitalisation, patient);
                hospitalisationTos.add(hospitalisationTo);
            }
            return hospitalisationTos;
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllHospitalisationsOfPatient() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public List<String> getHospitalisationStatuses() {
        return Arrays
                .stream(HospitalisationStatus.values())
                .map(HospitalisationStatus::name)
                .collect(Collectors.toList());
    }

    public void saveHospitalisation(UserTo user, Hospitalisation hospitalisation) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN.name())) {
            try {
                Hospitalisation checkIfExist = hospitalisationDao.getPatientCurrentHospitalisation(hospitalisation.getPatientId()).orElse(null);
                if (checkIfExist != null) {
                    LOG.error("Can't add new hospitalisation for undischarged user");
                    throw new IllegalRequestDataException(WRONG_REQUEST);
                }
                hospitalisationDao.save(hospitalisation);
            } catch (DBException e) {
                LOG.error("Exception has occurred during executing saveHospitalisation() method", e);
                throw new ApplicationException(APP_ERROR);
            }
        } else {
            LOG.error("Only ADMIN can get list of all hospitalisations, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
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
        if (user.getRole().equals(Role.DOCTOR.name())) {
            try {
                Hospitalisation hospitalisation = hospitalisationDao.get(hospitalisationId).orElse(null);
                LOG.info("HOSPITALISATION {}", hospitalisation);
                if (hospitalisation != null) {
                    if (hospitalisation.getDiagnosis() != null && !hospitalisation.getDiagnosis().isEmpty()) {
                        hospitalisation.setEndDate(endDate);
                        hospitalisation.setStatus(HospitalisationStatus.DISCHARGED.name());
                        hospitalisationDao.update(hospitalisation);
                    } else {
                        LOG.error("Only patients with determined diagnosis can be discharged");
                        throw new IllegalRequestDataException(WRONG_REQUEST);
                    }
                }
            } catch (DBException e) {
                LOG.error("Exception has occurred during executing dischargePatient() method", e);
                throw new ApplicationException(APP_ERROR);
            }
        } else {
            LOG.error("Only DOCTOR can discharge patient, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    public void determinePatientDiagnosis(UserTo user, int hospitalisationId, String diagnosis) {
        if (user.getRole().equals(Role.DOCTOR.name())) {
            try {
                Hospitalisation hospitalisation = hospitalisationDao.get(hospitalisationId).orElse(null);
                if (hospitalisation != null) {
                    hospitalisation.setDiagnosis(diagnosis);
                    hospitalisationDao.update(hospitalisation);
                }
            } catch (DBException e) {
                LOG.error("Exception has occurred during executing dischargePatient() method", e);
                throw new ApplicationException(APP_ERROR);
            }
        } else {
            LOG.error("Only DOCTOR can discharge patient, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }
}
