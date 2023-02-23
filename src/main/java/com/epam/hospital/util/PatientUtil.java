package com.epam.hospital.util;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.enums.Gender;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.PatientTo;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.command.constant.Parameter.DATE_OF_BIRTH;
import static com.epam.hospital.command.constant.Parameter.GENDER;

public class PatientUtil {
    public static PatientTo createPatientTo(Patient patient) {
        return new PatientTo.Builder().id(patient.getId()).firstName(patient.getFirstName())
                .lastName(patient.getLastName()).email(patient.getEmail()).role(patient.getRole())
                .userId(patient.getUserId()).dateOfBirth(patient.getDateOfBirth()).gender(patient.getGender())
                .build();
    }

    public static List<PatientTo> getPatientTos(List<Patient> patients){
        return patients.stream()
                .map(PatientUtil::createPatientTo)
                .collect(Collectors.toList());
    }

    public static Patient createPatient(HttpServletRequest request){
        return new Patient.Builder().dateOfBirth(LocalDate.parse(request.getParameter(DATE_OF_BIRTH)))
                .gender(Gender.valueOf(request.getParameter(GENDER))).build();
    }
}
