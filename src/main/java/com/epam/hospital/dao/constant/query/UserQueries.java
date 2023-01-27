package com.epam.hospital.dao.constant.query;

public class UserQueries {
    public static final String GET_USER_BY_ID = "SELECT * FROM users u WHERE u.id=%s";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM users u WHERE u.email='%s'";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String ADD_USER = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE users SET first_name=?, last_name=?, email=?, password=?, role=? WHERE id=?";

    private UserQueries() {
    }
}