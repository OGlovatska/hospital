package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.User;

import java.sql.*;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> getByEmail(String email) throws DBException;

    Optional<User> save(User user, Connection connection) throws DBException;
}