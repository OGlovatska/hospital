package com.epam.hospital.service;

import com.epam.hospital.dao.impl.HospitalisationDaoImpl;
import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.dao.impl.StaffPatientDaoImpl;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.exception.IllegalRequestDataException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.enums.HospitalisationStatus;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.pagination.Pageable;
import com.epam.hospital.util.pagination.Sort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.HospitalisationUtil.createHospitalisationTo;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class HospitalisationService {
    private final HospitalisationDaoImpl hospitalisationDao;
    private final PatientDaoImpl patientDao;
    private final StaffPatientDaoImpl staffPatientDao;

    public HospitalisationService(HospitalisationDaoImpl hospitalisationDao, PatientDaoImpl patientDao, StaffPatientDaoImpl staffPatientDao) {
        this.hospitalisationDao = hospitalisationDao;
        this.patientDao = patientDao;
        this.staffPatientDao = staffPatientDao;
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
