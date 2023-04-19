package com.epam.hospital.service;

import com.epam.hospital.dao.DaoFactory;

public class ServiceFactory {
    private final AppointmentService appointmentService;
    private final HospitalisationService hospitalisationService;
    private final PatientService patientService;
    private final StaffService staffService;
    private final UserService userService;
    private static ServiceFactory instance;

    private ServiceFactory(DaoFactory daoFactory) {
        this.appointmentService = new AppointmentService(daoFactory.getAppointmentDao(), daoFactory.getPatientDao(),
                daoFactory.getStaffDao(), daoFactory.getHospitalisationDao());
        this.hospitalisationService = new HospitalisationService(daoFactory.getHospitalisationDao(),
                daoFactory.getPatientDao(), daoFactory.getStaffPatientDao(), daoFactory.getAppointmentDao(),
                daoFactory.getStaffDao());
        this.patientService = new PatientService(daoFactory.getPatientDao(), daoFactory.getStaffPatientDao(),
                daoFactory.getPatientRepository(), daoFactory.getStaffDao());
        this.staffService = new StaffService(daoFactory.getStaffDao(), daoFactory.getStaffPatientDao(),
                daoFactory.getStaffRepository());
        this.userService = new UserService(daoFactory.getUserDao());
    }

    public static synchronized ServiceFactory getInstance(DaoFactory daoFactory){
        if (instance == null){
            instance = new ServiceFactory(daoFactory);
        }
        return instance;
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
}