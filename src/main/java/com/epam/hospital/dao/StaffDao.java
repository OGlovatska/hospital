package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.Staff;
import com.epam.hospital.util.pagination.Pageable;

import java.sql.*;
import java.util.*;

public interface StaffDao {

    Optional<Staff> get(long id) throws DBException;

    List<Staff> getAll() throws DBException;

    Optional<Staff> save(Staff t) throws DBException;

    Optional<Staff> update(Staff t) throws DBException;

    Optional<Staff> getByUserId(long userId) throws DBException;

    Optional<Staff> save(Staff staff, Connection connection) throws DBException;

    Map<Staff, Integer> getAllStaff(Pageable pageable) throws DBException;

    List<Staff> getAllStaff(int patientId, Pageable pageable) throws DBException;

    List<Staff> getAllStaffNotAssignedToPatient(int patientId) throws DBException;

    int staffCount() throws DBException;
}