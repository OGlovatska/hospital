package com.epam.hospital.db.connection;

import com.zaxxer.hikari.HikariConfig;

public interface AbstractConnection {
    HikariConfig getConnectionConfiguration();
}
