package com.epam.hospital.service;

import com.epam.hospital.dao.impl.StaffDaoImpl;
import com.epam.hospital.dao.impl.StaffPatientDaoImpl;
import com.epam.hospital.exception.*;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.model.enums.Specialisation;
import com.epam.hospital.repository.StaffRepository;
import com.epam.hospital.to.StaffTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.pagination.Pageable;
import com.epam.hospital.pagination.Sort;
import com.epam.hospital.util.StaffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.EmailUtil.sendRegistrationEmail;
import static com.epam.hospital.util.StaffUtil.*;
import static com.epam.hospital.util.ValidationUtil.*;

public class StaffService {
    private static final Logger LOG = LoggerFactory.getLogger(StaffService.class);
    private final StaffDaoImpl staffDao = new StaffDaoImpl();
    private final StaffPatientDaoImpl staffPatientDao = new StaffPatientDaoImpl();
    private final StaffRepository staffRepository = new StaffRepository();

    public List<StaffTo> getAllStaff(UserTo user, String offset, String limit, String orderBy, String direction) throws IllegalRequestDataException {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN.name())) {
            try {
                return StaffUtil.getStaffTos(staffDao.getAllStaff(new Pageable(offset, limit, new Sort(orderBy, direction))));
            } catch (DBException e) {
                LOG.error("Exception has occurred during executing getAllStaff() method", e);
                throw new IllegalRequestDataException(APP_ERROR);
            }
        } else {
            LOG.error("Only ADMIN can get all staff list, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    public int getStaffCount() {
        try {
            return staffDao.staffCount();
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getStaffCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public List<String> getAllSpecialisations(){
        return Arrays
                .stream(Specialisation.values())
                .map(Specialisation::getSpecialisation)
                .collect(Collectors.toList());
    }

    public List<String> getStaffRoles() {
        List<String> roles = new ArrayList<>();
        roles.add(Role.NURSE.name());
        roles.add(Role.DOCTOR.name());
        return roles;
    }

    public void saveStaff(String password, User user, Staff staff){
        try {
            staffRepository.save(user, staff);
            sendRegistrationEmail(password, user);
            createNewStaffTo(user, staff);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing saveStaff() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public List<StaffTo> getAllStaffOfPatient(int patientId, String offset, String limit, String orderBy, String direction) {
        try {
            return getStaffTos(staffDao.getAllStaff(patientId,
                    new Pageable(offset, limit, new Sort(orderBy, direction))));
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllStaffOfPatient() method", e);
            throw new IllegalRequestDataException();
        }
    }

    public int getStaffOfPatientCount(int patientId) {
        try {
            return staffPatientDao.staffOfPatientCount(patientId);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getStaffOfPatientCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public List<StaffTo> getAllStaffNotAssignedToPatient(int patientId) {
        try {
            return getStaffTos(staffDao.getAllStaffNotAssignedToPatient(patientId));
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllPatientsNotAssignedToStaff() method", e);
            throw new IllegalRequestDataException();
        }
    }

    public StaffTo getStaff(long staffId) {
        try {
            return createStaffTo(staffDao.get(staffId).orElseThrow(), 0);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getStaff method", e);
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    public StaffTo getStaff(UserTo user) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.DOCTOR.name()) || user.getRole().equals(Role.NURSE.name())){
            try {
                return createStaffTo(staffDao.getByUserId(user.getId()).orElseThrow(), 0);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
