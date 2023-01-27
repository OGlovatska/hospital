package com.epam.hospital.db.manager;


import java.sql.Connection;
import java.sql.SQLException;

public interface DBManager {
    Connection getConnection() throws SQLException;

    void closeConnection(Connection connection);

    void rollbackConnection(Connection connection);
}
