package com.epam.hospital.dao;

import com.epam.hospital.dao.impl.*;
import com.epam.hospital.db.manager.DBManager;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.repository.StaffRepository;

public class DaoFactory {
    private final AppointmentDaoImpl appointmentDao;
    private final HospitalisationDaoImpl hospitalisationDao;
    private final PatientDaoImpl patientDao;
    private final StaffDaoImpl staffDao;
    private final StaffPatientDaoImpl staffPatientDao;
    private final UserDaoImpl userDao;
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
        patientRepository = new PatientRepository(userDao, patientDao, dbManager);
        staffRepository = new StaffRepository(userDao, staffDao, dbManager);
    }

    public static synchronized DaoFactory getInstance(DBManager dbManager) {
        if (instance == null) {
            instance = new DaoFactory(dbManager);
        }
        return instance;
    }

    public AppointmentDaoImpl getAppointmentDao() {
        return appointmentDao;
    }

    public HospitalisationDaoImpl getHospitalisationDao() {
        return hospitalisationDao;
    }

    public PatientDaoImpl getPatientDao() {
        return patientDao;
    }

    public StaffDaoImpl getStaffDao() {
        return staffDao;
    }

    public StaffPatientDaoImpl getStaffPatientDao() {
        return staffPatientDao;
    }

    public UserDaoImpl getUserDao() {
        return userDao;
    }

    public PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public StaffRepository getStaffRepository() {
        return staffRepository;
    }
}