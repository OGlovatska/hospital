package com.epam.hospital.repository;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;

import java.util.Optional;

public interface StaffRepository {

    Optional<Staff> save(User user, Staff staff) throws DBException;
}