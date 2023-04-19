package com.epam.hospital.dao.impl;

import com.epam.hospital.dao.UserDao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.hospital.dao.constant.field.CommonFields.ID;
import static com.epam.hospital.dao.constant.field.UserFields.*;
import static com.epam.hospital.dao.constant.query.UserQueries.*;

public class UserDaoImpl implements UserDao {
    private final DBManager dbManager;

    public UserDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public Optional<User> get(long id) throws DBException {
        return get(String.format(GET_USER_BY_ID, id));
    }

    public Optional<User> getByEmail(String email) throws DBException {
        return get(String.format(GET_USER_BY_EMAIL, email));
    }

    private Optional<User> get(String query) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() throws DBException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<User> save(User user) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            return save(user, connection);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Optional<User> save(User user, Connection connection) throws DBException {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().name());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) throws DBException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().name());
            statement.setInt(6, user.getId());
            return get(user.getId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User.Builder<>().id(resultSet.getInt(ID)).firstName(resultSet.getString(FIRST_NAME))
                .lastName(resultSet.getString(LAST_NAME)).email(resultSet.getString(EMAIL))
                .password(resultSet.getString(PASSWORD)).role(Role.valueOf(resultSet.getString(ROLE))).build();
    }
}