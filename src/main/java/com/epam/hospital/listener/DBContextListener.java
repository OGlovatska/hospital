package com.epam.hospital.listener;

import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class DBContextListener implements ServletContextListener {
    private static ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBManager dbManager = MySQLDBManager.getInstance();
        context = sce.getServletContext();
        context.setAttribute("dbManager", dbManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized ServletContext getServletContext() {
        return context;
    }
}
