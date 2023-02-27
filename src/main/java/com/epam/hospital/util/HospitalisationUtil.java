package com.epam.hospital.util;

import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.HospitalisationTo;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

import static com.epam.hospital.command.constant.Parameter.*;

public class HospitalisationUtil {

    public static HospitalisationTo createHospitalisationTo(Hospitalisation hospitalisation, Patient patient) {
        return new HospitalisationTo.Builder().id(hospitalisation.getId()).patientId(hospitalisation.getPatientId())
                .patientFirstName(patient.getFirstName()).patientLastName(patient.getLastName())
                .startDate(hospitalisation.getStartDate()).endDate(hospitalisation.getEndDate())
                .status(hospitalisation.getStatus()).diagnosis(hospitalisation.getDiagnosis())
                .build();
    }

    public static HospitalisationTo createHospitalisationTo(Hospitalisation hospitalisation) {
        return new HospitalisationTo.Builder().id(hospitalisation.getId())
                .patientId(hospitalisation.getPatientId()).startDate(hospitalisation.getStartDate())
                .endDate(hospitalisation.getEndDate()).status(hospitalisation.getStatus())
                .diagnosis(hospitalisation.getDiagnosis())
                .build();
    }

    public static Hospitalisation createHospitalisation(HttpServletRequest request) {
        return new Hospitalisation.Builder()
                .patientId(Integer.parseInt(request.getParameter(PATIENT_ID)))
                .startDate(request.getParameter(HOSPITALISATION_START_DATE) != null ? LocalDate.parse(request.getParameter(HOSPITALISATION_START_DATE)) : null)
                .endDate(request.getParameter(HOSPITALISATION_END_DATE) != null ? LocalDate.parse(request.getParameter(HOSPITALISATION_END_DATE)) : null)
                .status(request.getParameter(STATUS)).diagnosis(request.getParameter(DIAGNOSIS))
                .build();
    }
}