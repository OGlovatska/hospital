package com.epam.hospital.service;

import com.epam.hospital.dao.AppointmentDao;
import com.epam.hospital.dao.HospitalisationDao;
import com.epam.hospital.dao.PatientDao;
import com.epam.hospital.dao.StaffDao;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.exception.IllegalRequestDataException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.enums.AppointmentStatus;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.pagination.Pageable;
import com.epam.hospital.util.pagination.Sort;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.AppointmentUtil.createAppointmentTo;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class AppointmentService {
    private final AppointmentDao appointmentDao;
    private final PatientDao patientDao;
    private final StaffDao staffDao;
    private final HospitalisationDao hospitalisationDao;

    public AppointmentService(AppointmentDao appointmentDao, PatientDao patientDao, StaffDao staffDao,
                              HospitalisationDao hospitalisationDao) {
        this.appointmentDao = appointmentDao;
        this.patientDao = patientDao;
        this.staffDao = staffDao;
        this.hospitalisationDao = hospitalisationDao;
    }

    public Map<Integer, List<AppointmentTo>> getAllAppointments(UserTo user, int offset, int limit, String orderBy, String direction) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN)) {
            return getAppointmentsForAdmin(offset, limit, orderBy, direction);
        } else if (user.getRole().equals(Role.DOCTOR) || user.getRole().equals(Role.NURSE)) {
            return getAppointmentsForStaff(user, offset, limit, orderBy, direction);
        } else {
            return getAppointmentsForPatient(user, offset, limit, orderBy, direction);
        }
    }

    private Map<Integer, List<AppointmentTo>> getAppointmentsForAdmin(int offset, int limit, String orderBy, String direction) {
        try {
            Map<Integer, List<AppointmentTo>> appointments = new HashMap<>();
            int appointmentsCount = getAllAppointmentsCount();
            List<AppointmentTo> appointmentTos = getAppointmentTos(appointmentDao.getAll(
                    new Pageable(offset, limit, new Sort(orderBy, direction))));
            appointments.put(appointmentsCount, appointmentTos);
            return appointments;
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    private Map<Integer, List<AppointmentTo>> getAppointmentsForStaff(UserTo user, int offset, int limit, String orderBy, String direction) {
        try {
            Staff staff = staffDao.getByUserId(user.getId()).orElse(null);
            if (staff != null) {
                Map<Integer, List<AppointmentTo>> appointments = new HashMap<>();
                int appointmentsCount = appointmentDao.appointmentsCountForStaff(staff.getId());
                List<AppointmentTo> appointmentTos = getAllAppointmentsOfStaff(staff.getId(), offset, limit, orderBy, direction);
                appointments.put(appointmentsCount, appointmentTos);
                return appointments;
            } else {
                throw new IllegalRequestDataException(STAFF_NOT_FOUND);
            }
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    private Map<Integer, List<AppointmentTo>> getAppointmentsForPatient(UserTo user, int offset, int limit, String orderBy, String direction) {
        try {
            Patient patient = patientDao.getByUserId(user.getId()).orElse(null);
            if (patient != null) {
                Map<Integer, List<AppointmentTo>> appointments = new HashMap<>();
                int appointmentsCount = appointmentDao.appointmentsCountForPatient(patient.getId());
                List<AppointmentTo> appointmentTos = getAppointmentTos(appointmentDao.getAllAppointmentsOfPatient(patient.getId(),
                        new Pageable(offset, limit, new Sort(orderBy, direction))));
                appointments.put(appointmentsCount, appointmentTos);
                return appointments;
            } else {
                throw new IllegalRequestDataException(PATIENT_NOT_FOUND);
            }
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public int getAllAppointmentsCount() {
        try {
            return appointmentDao.appointmentsCount();
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    public List<AppointmentTo> getAllAppointmentsOfStaff(int staffId, int offset, int limit, String orderBy, String direction) {
        try {
            return getAppointmentTos(appointmentDao.getAllAppointmentsOfStaff(staffId,
                    new Pageable(offset, limit, new Sort(orderBy, direction))));
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }

    private List<AppointmentTo> getAppointmentTos(List<Appointment> appointments) throws DBException {
        List<AppointmentTo> appointmentTos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Patient patient = patientDao.get(appointment.getPatientId()).orElseThrow();
            Staff staff = staffDao.get(appointment.getStaffId()).orElseThrow();
            AppointmentTo appointmentTo = createAppointmentTo(appointment, patient, staff);
            appointmentTos.add(appointmentTo);
        }
        return appointmentTos;
    }

    public void saveAppointment(UserTo user, Appointment appointment) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.DOCTOR) || user.getRole().equals(Role.NURSE)) {
            try {
                Hospitalisation hospitalisation = hospitalisationDao
                        .getPatientCurrentHospitalisation(appointment.getPatientId()).orElseThrow();
                appointment.setHospitalisationId(hospitalisation.getId());
                appointmentDao.save(appointment);
            } catch (DBException e) {
                throw new ApplicationException(e.getMessage(), APP_ERROR);
            } catch (NoSuchElementException e) {
                throw new IllegalRequestDataException(PATIENT_NOT_HOSPITALISED);
            }
        } else {
            throw new IllegalRequestDataException(NOT_STAFF);
        }
    }

    public List<String> getAppointmentStatuses() {
        return Arrays
                .stream(AppointmentStatus.values())
                .map(AppointmentStatus::name)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<AppointmentTo>> getAllAppointments(UserTo user, int hospitalisationId, int offset,
                                                                int limit, String orderBy, String direction) {
        checkUserNotNull(user);
        try {
            List<AppointmentTo> appointmentTos = getAppointmentTos(appointmentDao.getAllAppointmentsByHospitalisation(
                    hospitalisationId, new Pageable(offset, limit, new Sort(orderBy, direction))));
            int appointmentsCount = appointmentDao.appointmentsCountForHospitalisation(hospitalisationId);
            Map<Integer, List<AppointmentTo>> appointments = new HashMap<>();
            appointments.put(appointmentsCount, appointmentTos);
            return appointments;
        } catch (DBException e) {
            throw new ApplicationException(e.getMessage(), APP_ERROR);
        }
    }
}