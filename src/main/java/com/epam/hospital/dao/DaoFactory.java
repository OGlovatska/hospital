package com.epam.hospital.dao;

import com.epam.hospital.dao.impl.*;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.repository.StaffRepository;
import com.epam.hospital.repository.impl.PatientRepositoryImpl;
import com.epam.hospital.repository.impl.StaffRepositoryImpl;

public class DaoFactory {
    private final AppointmentDao appointmentDao;
    private final HospitalisationDao hospitalisationDao;
    private final PatientDao patientDao;
    private final StaffDao staffDao;
    private final StaffPatientDao staffPatientDao;
    private final UserDao userDao;
    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;
    private static DaoFactory instance;

    private DaoFactory(DBManager dbManager) {
        appointmentDao = new AppointmentDaoImpl(dbManager);
        hospitalisationDao = new HospitalisationDaoImpl(dbManager);
        patientDao = new PatientDaoImpl(dbManager);
        staffDao = new StaffDaoImpl(dbManager);
        staffPatientDao = new StaffPatientDaoImpl(dbManager);
        userDao = new UserDaoImpl(dbManager);
        patientRepository = new PatientRepositoryImpl(userDao, patientDao, dbManager);
        staffRepository = new StaffRepositoryImpl(userDao, staffDao, dbManager);
    }

    public static synchronized DaoFactory getInstance(DBManager dbManager) {
        if (instance == null) {
            instance = new DaoFactory(dbManager);
        }
        return instance;
    }

    public AppointmentDao getAppointmentDao() {
        return appointmentDao;
    }

    public HospitalisationDao getHospitalisationDao() {
        return hospitalisationDao;
    }

    public PatientDao getPatientDao() {
        return patientDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    public StaffPatientDao getStaffPatientDao() {
        return staffPatientDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public StaffRepository getStaffRepository() {
        return staffRepository;
    }
}