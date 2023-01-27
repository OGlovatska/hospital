package com.epam.hospital.util;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.StaffTo;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.command.constant.Parameter.DATE_OF_BIRTH;
import static com.epam.hospital.command.constant.Parameter.GENDER;

public class PatientUtil {
    public static PatientTo createPatientTo(Patient patient) {
        return new PatientTo(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getEmail(), patient.getRole().name(), patient.getUserId(), patient.getDateOfBirth(),
                patient.getGender());
    }

    public static List<PatientTo> getPatientTos(List<Patient> patients){
        return patients.stream()
                .map(PatientUtil::createPatientTo)
                .collect(Collectors.toList());
    }

    public static Patient createPatient(HttpServletRequest request){
        Patient patient = new Patient();
        patient.setDateOfBirth(LocalDate.parse(request.getParameter(DATE_OF_BIRTH)));
        patient.setGender(request.getParameter(GENDER).toLowerCase());
        return patient;
    }

    public static PatientTo createNewPatientTo(User user, Patient patient) {
        return new PatientTo(patient.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getRole().name(), patient.getUserId(),
                patient.getDateOfBirth(), patient.getGender());
    }
}
