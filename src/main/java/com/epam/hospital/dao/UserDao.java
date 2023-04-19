package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> get(long id) throws DBException;

    Optional<User> getByEmail(String email) throws DBException;

    List<User> getAll() throws DBException;

    Optional<User> save(User user) throws DBException;

    Optional<User> save(User user, Connection connection) throws DBException;

    Optional<User> update(User user) throws DBException;
}