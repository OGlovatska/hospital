package com.epam.hospital.db.manager;

import com.epam.hospital.db.connection.MySQLConnection;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDBManager implements DBManager {
    private static DBManager dbManager;
    private static HikariDataSource ds;

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new MySQLDBManager();
        }
        return dbManager;
    }

    private MySQLDBManager() {
        MySQLConnection sqlConnection = new MySQLConnection();
        ds = new HikariDataSource(sqlConnection.getConnectionConfiguration());
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void rollbackConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
