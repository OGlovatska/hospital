package com.epam.hospital.util;

import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Staff;
import com.epam.hospital.to.AppointmentTo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.DateTimeUtil.parseLocalDateTime;

public class AppointmentUtil {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentUtil.class);

    public static AppointmentTo createAppointmentTo(Appointment appointment, Patient patient, Staff staff) {
        return new AppointmentTo(appointment.getId(), appointment.getHospitalisationId(), appointment.getPatientId(),
                appointment.getStaffId(), appointment.getDateTime(), appointment.getType(), appointment.getDescription(),
                appointment.getConclusion(), appointment.getStatus(), patient.getFirstName(), patient.getLastName(),
                staff.getFirstName(), staff.getLastName(), staff.getSpecialisation());
    }

    public static Appointment createAppointment(HttpServletRequest request){
        Appointment appointment = new Appointment();
        appointment.setPatientId(Integer.parseInt(request.getParameter(PATIENT_ID)));
        appointment.setStaffId(Integer.parseInt(request.getParameter(STAFF_ID)));
        appointment.setDateTime(parseLocalDateTime(request.getParameter(DATE_TIME)));
        appointment.setType(request.getParameter(TYPE));
        appointment.setDescription(request.getParameter(DESCRIPTION));
        appointment.setConclusion(request.getParameter(CONCLUSION));
        appointment.setStatus(request.getParameter(STATUS));
        return appointment;
    }
}
