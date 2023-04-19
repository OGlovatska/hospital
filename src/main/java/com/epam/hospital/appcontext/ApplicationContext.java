package com.epam.hospital.appcontext;

import com.epam.hospital.dao.DaoFactory;
import com.epam.hospital.dao.UserDao;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.db.manager.MySQLDBManager;
import com.epam.hospital.service.*;

public class ApplicationContext {
    private final UserDao userDao;
    private final AppointmentService appointmentService;
    private final HospitalisationService hospitalisationService;
    private final PatientService patientService;
    private final StaffService staffService;
    private final UserService userService;

    private static ApplicationContext applicationContext;

    private ApplicationContext() {
        DBManager dbManager = MySQLDBManager.getInstance();
        DaoFactory daoFactory = DaoFactory.getInstance(dbManager);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);

        this.userDao = daoFactory.getUserDao();
        this.appointmentService = serviceFactory.getAppointmentService();
        this.hospitalisationService = serviceFactory.getHospitalisationService();
        this.patientService = serviceFactory.getPatientService();
        this.staffService = serviceFactory.getStaffService();
        this.userService = serviceFactory.getUserService();
    }

    public static synchronized ApplicationContext getInstance() {
        return applicationContext;
    }

    public static void createApplicationContext() {
        applicationContext = new ApplicationContext();
    }

    public AppointmentService getAppointmentService() {
        return appointmentService;
    }

    public HospitalisationService getHospitalisationService() {
        return hospitalisationService;
    }

    public PatientService getPatientService() {
        return patientService;
    }

    public StaffService getStaffService() {
        return staffService;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}