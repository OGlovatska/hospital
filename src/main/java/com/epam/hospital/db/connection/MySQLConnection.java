package com.epam.hospital.db.connection;

import com.zaxxer.hikari.HikariConfig;

public class MySQLConnection implements AbstractConnection{
    @Override
    public HikariConfig getConnectionConfiguration() {
        return new HikariConfig("/db/mysql.properties");
    }
}
