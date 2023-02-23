package com.epam.hospital.appcontext;

import com.epam.hospital.dao.impl.*;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.repository.StaffRepository;
import com.epam.hospital.service.*;

public class ApplicationContext {
    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    private final HospitalisationDaoImpl hospitalisationDao = new HospitalisationDaoImpl();
    private final PatientDaoImpl patientDao = new PatientDaoImpl();
    private final StaffDaoImpl staffDao = new StaffDaoImpl();
    private final StaffPatientDaoImpl staffPatientDao = new StaffPatientDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();

    private final PatientRepository patientRepository = new PatientRepository(userDao, patientDao);
    private final StaffRepository staffRepository = new StaffRepository();

    private final AppointmentService appointmentService = new AppointmentService(appointmentDao, patientDao,
            staffDao, hospitalisationDao);
    private final HospitalisationService hospitalisationService = new HospitalisationService(hospitalisationDao,
            patientDao, staffDao, appointmentDao);
    private final PatientService patientService = new PatientService(patientDao, staffPatientDao, patientRepository,
            staffDao);
    private final StaffService staffService = new StaffService(staffDao, staffPatientDao, staffRepository);
    private final UserService userService = new UserService(userDao);

    private static ApplicationContext applicationContext;

    public static synchronized ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
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

    public UserDaoImpl getUserDao() {
        return userDao;
    }
}
