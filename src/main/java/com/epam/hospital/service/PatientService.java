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
import com.epam.hospital.pagination.Pageable;
import com.epam.hospital.pagination.Sort;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.exception.ErrorType.APP_ERROR;
import static com.epam.hospital.exception.ErrorType.WRONG_REQUEST;
import static com.epam.hospital.util.EmailUtil.sendRegistrationEmail;
import static com.epam.hospital.util.PatientUtil.*;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class PatientService {
    private static final Logger LOG = LoggerFactory.getLogger(PatientService.class);
    private final PatientDaoImpl patientDao = new PatientDaoImpl();
    private final StaffPatientDaoImpl staffPatientDao = new StaffPatientDaoImpl();
    private final PatientRepository patientRepository = new PatientRepository();
    private final StaffDaoImpl staffDao = new StaffDaoImpl();

    public List<PatientTo> getAllPatientsOfStaff(long staffId, String offset, String limit, String orderBy, String direction) throws IllegalRequestDataException {
        try {
            return getPatientTos(patientDao.getAllPatients(staffId,
                    new Pageable(offset, limit, new Sort(orderBy, direction))));
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllPatientsOfStaff() method in PatientService", e);
            throw new IllegalRequestDataException();
        }
    }

    public List<PatientTo> getAllPatientsNotAssignedToStaff(long staffId) throws IllegalRequestDataException {
        try {
            return getPatientTos(patientDao.getAllPatientsNotAssignedToStaff(staffId));
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllPatientsNotAssignedToStaff() method in PatientService", e);
            throw new IllegalRequestDataException();
        }
    }

    public int getPatientsOfStaffCount(long staffId) throws IllegalRequestDataException {
        try {
            return staffPatientDao.patientsOfStaffCount(staffId);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getStaffCount() method in PatientService", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public void assignPatientToStaff(int staffId, int patientId) {
        try {
            StaffPatient assigment = new StaffPatient(staffId, patientId);
            staffPatientDao.save(assigment);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getStaffCount() method in PatientService", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public PatientTo savePatient(String password, User user, Patient patient) {
        try {
            patientRepository.save(user, patient);
            sendRegistrationEmail(password, user);
            return createNewPatientTo(user, patient);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing savePatient() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public Map<Integer, List<PatientTo>> getAllPatients(UserTo user, String offset, String limit, String orderBy, String direction) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN.name())) {
            return getPatientsForAdmin(new Pageable(offset, limit, new Sort(orderBy, direction)));
        } else if (user.getRole().equals(Role.DOCTOR.name()) || user.getRole().equals(Role.NURSE.name())){
            return getPatientsForStaff(user, new Pageable(offset, limit, new Sort(orderBy, direction)));
        } else {
            LOG.error("Current user can't get patients list, role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    private Map<Integer, List<PatientTo>> getPatientsForAdmin(Pageable pageable){
        try {
            Map<Integer, List<PatientTo>> patients = new HashMap<>();
            int patientsCount = getPatientsCount();
            List<PatientTo> patientTos = getPatientTos(patientDao.getAllPatients(pageable));
            patients.put(patientsCount, patientTos);
            return patients;
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllPatients() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    private Map<Integer, List<PatientTo>> getPatientsForStaff(UserTo user, Pageable pageable){
        try {
            Staff staff = staffDao.getByUserId(user.getId()).orElseThrow();
            if (staff != null) {
                Map<Integer, List<PatientTo>> patients = new HashMap<>();
                int patientsCount = staffPatientDao.patientsOfStaffCount(staff.getId());
                List<PatientTo> patientTos = getPatientTos(patientDao.getAllPatients(staff.getId(), pageable));
                patients.put(patientsCount, patientTos);
                return patients;
            } else {
                throw new IllegalRequestDataException(WRONG_REQUEST);
            }
        } catch (DBException e) {
            e.printStackTrace();
            throw new ApplicationException(APP_ERROR);
        }
    }

    public int getPatientsCount() {
        try {
            return patientDao.patientsCount();
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getPatientsCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
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
            e.printStackTrace();
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }
}
