package com.epam.hospital.service;

import com.epam.hospital.dao.impl.AppointmentDaoImpl;
import com.epam.hospital.dao.impl.HospitalisationDaoImpl;
import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.dao.impl.StaffDaoImpl;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.exception.DBException;
import com.epam.hospital.exception.IllegalRequestDataException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.enums.AppointmentStatus;
import com.epam.hospital.model.enums.AppointmentType;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.pagination.Pageable;
import com.epam.hospital.pagination.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.hospital.exception.ErrorType.*;
import static com.epam.hospital.util.AppointmentUtil.createAppointmentTo;
import static com.epam.hospital.util.ValidationUtil.checkUserNotNull;

public class AppointmentService {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
    private final PatientDaoImpl patientDao = new PatientDaoImpl();
    private final StaffDaoImpl staffDao = new StaffDaoImpl();
    private final HospitalisationDaoImpl hospitalisationDao = new HospitalisationDaoImpl();

    public Map<Integer, List<AppointmentTo>> getAllAppointments(UserTo user, String offset, String limit, String orderBy, String direction) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN.name())) {
            return getAppointmentsForAdmin(offset, limit, orderBy, direction);
        } else if (user.getRole().equals(Role.DOCTOR.name()) || user.getRole().equals(Role.NURSE.name())){
            return getAppointmentsForStaff(user, offset, limit, orderBy, direction);
        } else {
            LOG.error("Only ADMIN, DOCTOR and NURSE can get list of appointments, current user role is {}", user.getRole());
            throw new IllegalRequestDataException(WRONG_REQUEST);
        }
    }

    private Map<Integer, List<AppointmentTo>> getAppointmentsForAdmin(String offset, String limit, String orderBy, String direction){
        try {
            Map<Integer, List<AppointmentTo>> appointments = new HashMap<>();
            int appointmentsCount = getAllAppointmentsCount();
            List<AppointmentTo> appointmentTos = getAppointmentTos(appointmentDao.getAll(new Pageable(offset, limit, new Sort(orderBy, direction))));
            appointments.put(appointmentsCount, appointmentTos);
            return appointments;
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllAppointments() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    private Map<Integer, List<AppointmentTo>> getAppointmentsForStaff(UserTo user, String offset, String limit, String orderBy, String direction){
        try {
            Staff staff = staffDao.getByUserId(user.getId()).orElse(null);
            if (staff != null){
                Map<Integer, List<AppointmentTo>> appointments = new HashMap<>();
                int appointmentsCount = appointmentDao.appointmentsCount(staff.getId());
                List<AppointmentTo> appointmentTos = getAllAppointmentsOfStaff(staff.getId(), offset, limit, orderBy,direction);
                appointments.put(appointmentsCount, appointmentTos);
                return appointments;
            } else {
                LOG.error("If user role is DOCTOR or NURSE staff object must not be null, current user role is {}", user.getRole());
                throw new IllegalRequestDataException(WRONG_REQUEST);
            }
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllAppointments() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public int getAllAppointmentsCount() {
        try {
            return appointmentDao.appointmentsCount();
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllAppointmentsCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public int getAllAppointmentsCount(int staffId) {
        try {
            return appointmentDao.appointmentsCount(staffId);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllAppointmentsCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public int getAllAppointmentsForDateCount(String date) {
        try {
            return appointmentDao.appointmentsForDateCount(date);
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllAppointmentsCount() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
        }
    }

    public List<AppointmentTo> getAllAppointmentsOfStaff(int staffId, String offset, String limit, String orderBy, String direction) {
        try {
            return getAppointmentTos(appointmentDao.getAllAppointmentsOfStaff(staffId,
                    new Pageable(offset, limit, new Sort(orderBy, direction))));
        } catch (DBException e) {
            LOG.error("Exception has occurred during executing getAllAppointments() method", e);
            throw new IllegalRequestDataException(APP_ERROR);
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

    public List<String> getAppointmentTypes(UserTo user) {
        checkUserNotNull(user);
        if (user.getRole().equals(Role.ADMIN.name()) || user.getRole().equals(Role.DOCTOR.name())) {
            return Arrays
                    .stream(AppointmentType.values())
                    .map(AppointmentType::name)
                    .collect(Collectors.toList());
        } else if (user.getRole().equals(Role.NURSE.name())){
            return Arrays.asList(AppointmentType.MEDICATION.name(), AppointmentType.PROCEDURE.name());
        } else {
            throw new IllegalRequestDataException(VALIDATION_ERROR);
        }
    }

    public void saveAppointment(Appointment appointment) {
        try {
            Hospitalisation hospitalisation = hospitalisationDao
                    .getPatientCurrentHospitalisation(appointment.getPatientId()).orElseThrow();

            if (hospitalisation != null){
                appointment.setHospitalisationId(hospitalisation.getId());
                appointmentDao.save(appointment);
            } else {
                throw new IllegalRequestDataException(VALIDATION_ERROR);
            }
        } catch (DBException e) {
            e.printStackTrace();
            throw new ApplicationException(APP_ERROR);
        }
    }

    public List<String> getAppointmentStatuses() {
        return Arrays
                .stream(AppointmentStatus.values())
                .map(AppointmentStatus::name)
                .collect(Collectors.toList());
    }
}

