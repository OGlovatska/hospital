package com.epam.hospital.service;

import com.epam.hospital.dao.StaffDao;
import com.epam.hospital.dao.StaffPatientDao;
import com.epam.hospital.exception.*;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.model.enums.Specialisation;
import com.epam.hospital.repository.StaffRepository;
import com.epam.hospital.to.StaffTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.pagination.Pageable;
import com.epam.hospital.util.pagination.Sort;
import com.epam.hospital.util.StaffUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.EmailUtil.sendRegistrationEmail;
import static com.epam.hospital.util.StaffUtil.*;
import static com.epam.hospital.util.ValidationUtil.*;

public class StaffService {
    private final StaffDao staffDao;
    private final StaffPatientDao staffPatientDao;
    private final StaffRepository staffRepository;

    public StaffService(StaffDao staffDao, StaffPatientDao staffPatientDao, StaffRepository staffRepository) {
        this.staffDao = staffDao;
        this.staffPatientDao = staffPatientDao;
        this.staffRepository = staffRepository;
    }

    public List<StaffTo> getAllStaff(UserTo user, int offset, int limit, String orderBy, String direction) throws IllegalRequestDataException {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN)) {
            try {
                return StaffUtil.getStaffTos(staffDao.getAllStaff(new Pageable(offset,
                        limit, new Sort(orderBy, direction))));
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            }
        } else {
            throw new IllegalRequestDataException(NOT_ADMIN);
        }
    }

    public int getStaffCount() {
        try {
            return staffDao.staffCount();
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public List<String> getAllSpecialisations() {
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

    public void saveStaff(UserTo user, String password, User newUser, Staff newStaff) {
        checkUserNotNull(user);
        validateUniqueEmail(newUser.getEmail());
        if (user.getRole().equals(Role.ADMIN)) {
            try {
                staffRepository.save(newUser, newStaff);
                sendRegistrationEmail(password, newUser);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            }
        } else {
            throw new IllegalRequestDataException(NOT_ADMIN);
        }
    }

    public List<StaffTo> getAllStaffOfPatient(int patientId, int offset, int limit, String orderBy, String direction) {
        try {
            return getStaffTos(staffDao.getAllStaff(patientId, new Pageable(offset, limit, new Sort(orderBy, direction))));
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public int getStaffOfPatientCount(int patientId) {
        try {
            return staffPatientDao.staffOfPatientCount(patientId);
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public List<StaffTo> getAllStaffNotAssignedToPatient(int patientId) {
        try {
            return getStaffTos(staffDao.getAllStaffNotAssignedToPatient(patientId));
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public StaffTo getStaff(long staffId) {
        try {
            return createStaffTo(staffDao.get(staffId).orElseThrow(), 0);
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public StaffTo getStaff(UserTo user) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.DOCTOR) || user.getRole().equals(Role.NURSE)) {
            try {
                return createStaffTo(staffDao.getByUserId(user.getId()).orElseThrow(), 0);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            } catch (NoSuchElementException e) {
                throw new IllegalRequestDataException(STAFF_NOT_FOUND);
            }
        } else {
            throw new IllegalRequestDataException(NOT_STAFF);
        }
    }
}