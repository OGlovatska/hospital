package com.epam.hospital.util;

import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.enums.AppointmentStatus;
import com.epam.hospital.model.enums.AppointmentType;
import com.epam.hospital.to.AppointmentTo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Parameter.*;

public class AppointmentUtil {

    public static AppointmentTo createAppointmentTo(Appointment appointment, Patient patient, Staff staff) {
        return new AppointmentTo.Builder().id(appointment.getId()).hospitalisationId(appointment.getHospitalisationId())
                .patientId(appointment.getPatientId()).staffId(appointment.getStaffId()).dateTime(appointment.getDateTime())
                .type(appointment.getType()).description(appointment.getDescription()).conclusion(appointment.getConclusion())
                .status(appointment.getStatus()).patientFirstName(patient.getFirstName()).patientLastName(patient.getLastName())
                .staffFirstName(staff.getFirstName()).staffLastName(staff.getLastName()).specialisation(staff.getSpecialisation())
                .build();
    }

    public static AppointmentTo createAppointmentTo(Appointment appointment, Staff staff) {
        return new AppointmentTo.Builder().id(appointment.getId()).hospitalisationId(appointment.getHospitalisationId())
                .patientId(appointment.getPatientId()).staffId(appointment.getStaffId()).dateTime(appointment.getDateTime())
                .type(appointment.getType()).description(appointment.getDescription()).conclusion(appointment.getConclusion())
                .status(appointment.getStatus()).staffFirstName(staff.getFirstName()).staffLastName(staff.getLastName())
                .specialisation(staff.getSpecialisation())
                .build();
    }

    public static Appointment createAppointment(HttpServletRequest request) {
        return new Appointment.Builder().patientId(Integer.parseInt(request.getParameter(PATIENT_ID)))
                .staffId(Integer.parseInt(request.getParameter(STAFF_ID)))
                .dateTime(DateTimeUtil.parseLocalDateTime(request.getParameter(DATE_TIME)))
                .type(AppointmentType.valueOf(request.getParameter(TYPE))).description(request.getParameter(DESCRIPTION))
                .status(AppointmentStatus.ASSIGNED.name())
                .build();
    }
}
