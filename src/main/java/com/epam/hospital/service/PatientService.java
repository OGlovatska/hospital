package com.epam.hospital.service;

import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.dao.impl.StaffDaoImpl;
import com.epam.hospital.dao.impl.StaffPatientDaoImpl;
import com.epam.hospital.exception.*;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.StaffPatient;
import com.epam.hospital.model.User;
import com.epam.hospital.model.enums.Gender;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.util.pagination.Pageable;
import com.epam.hospital.util.pagination.Sort;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.UserTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.EmailUtil.sendRegistrationEmail;
import static com.epam.hospital.util.PatientUtil.*;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;
import static com.epam.hospital.util.ValidationUtil.validateUniqueEmail;

public class PatientService {
    private final PatientDaoImpl patientDao;
    private final StaffPatientDaoImpl staffPatientDao;
    private final PatientRepository patientRepository;
    private final StaffDaoImpl staffDao;

    public PatientService(PatientDaoImpl patientDao, StaffPatientDaoImpl staffPatientDao,
                          PatientRepository patientRepository, StaffDaoImpl staffDao) {
        this.patientDao = patientDao;
        this.staffPatientDao = staffPatientDao;
        this.patientRepository = patientRepository;
        this.staffDao = staffDao;
    }

    public List<PatientTo> getAllPatientsOfStaff(long staffId, int offset, int limit, String orderBy, String direction) throws IllegalRequestDataException {
        try {
            return getPatientTos(patientDao.getAllPatients(staffId,
                    new Pageable(offset, limit, new Sort(orderBy, direction))));
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public List<PatientTo> getAllPatientsNotAssignedToStaff(long staffId) throws IllegalRequestDataException {
        try {
            return getPatientTos(patientDao.getAllPatientsNotAssignedToStaff(staffId));
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public int getPatientsOfStaffCount(long staffId) throws IllegalRequestDataException {
        try {
            return staffPatientDao.patientsOfStaffCount(staffId);
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public void assignPatientToStaff(UserTo user, int staffId, int patientId) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN)) {
            try {
                StaffPatient assigment = new StaffPatient(staffId, patientId);
                staffPatientDao.save(assigment);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            }
        } else {
            throw new IllegalRequestDataException(NOT_ADMIN);
        }
    }

    public void savePatient(UserTo user, String password, User newUser, Patient newPatient) {
        checkUserNotNull(user);
        validateUniqueEmail(newUser.getEmail());
        if (user.getRole().equals(Role.ADMIN)) {
            try {
                patientRepository.save(newUser, newPatient);
                sendRegistrationEmail(password, newUser);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            }
        } else {
            throw new IllegalRequestDataException(NOT_ADMIN);
        }
    }

    public Map<Integer, List<PatientTo>> getAllPatients(UserTo user, int offset, int limit, String orderBy, String direction) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN)) {
            return getPatientsForAdmin(new Pageable(offset, limit, new Sort(orderBy, direction)));
        } else if (user.getRole().equals(Role.DOCTOR) || user.getRole().equals(Role.NURSE)) {
            return getPatientsForStaff(user, new Pageable(offset, limit, new Sort(orderBy, direction)));
        } else {
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    private Map<Integer, List<PatientTo>> getPatientsForAdmin(Pageable pageable) {
        try {
            Map<Integer, List<PatientTo>> patients = new HashMap<>();
            int patientsCount = getPatientsCount();
            List<PatientTo> patientTos = getPatientTos(patientDao.getAllPatients(pageable));
            patients.put(patientsCount, patientTos);
            return patients;
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    private Map<Integer, List<PatientTo>> getPatientsForStaff(UserTo user, Pageable pageable) {
        try {
            Staff staff = staffDao.getByUserId(user.getId()).orElse(null);
            if (staff != null) {
                Map<Integer, List<PatientTo>> patients = new HashMap<>();
                int patientsCount = staffPatientDao.patientsOfStaffCount(staff.getId());
                List<PatientTo> patientTos = getPatientTos(patientDao.getAllPatients(staff.getId(), pageable));
                patients.put(patientsCount, patientTos);
                return patients;
            } else {
                throw new IllegalRequestDataException(STAFF_NOT_FOUND);
            }
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public int getPatientsCount() {
        try {
            return patientDao.patientsCount();
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public List<String> getAllGenders() {
        List<String> roles = new ArrayList<>();
        roles.add(Gender.FEMALE.name());
        roles.add(Gender.MALE.name());
        return roles;
    }

    public PatientTo getPatient(long patientId) {
        try {
            return createPatientTo(patientDao.get(patientId).orElseThrow());
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public PatientTo getPatient(UserTo user) throws ApplicationException{
        try {
            return createPatientTo(patientDao.getByUserId(user.getId()).orElseThrow());
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }
}
