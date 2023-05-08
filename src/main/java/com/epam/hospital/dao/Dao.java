package com.epam.hospital.dao;

import com.epam.hospital.exception.DBException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id) throws DBException;

    List<T> getAll() throws DBException;

    Optional<T> save(T t) throws DBException;

    Optional<T> update(T t) throws DBException;
}