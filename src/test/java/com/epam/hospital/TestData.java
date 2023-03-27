package com.epam.hospital;

import com.epam.hospital.model.enums.AppointmentType;
import com.epam.hospital.model.enums.Gender;
import com.epam.hospital.model.enums.Role;
import com.epam.hospital.model.enums.Specialisation;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.StaffTo;
import com.epam.hospital.to.UserTo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestData {
    public static final String EMAIL = "admin@gmail.com";
    public static final String INCORRECT_EMAIL = "admin1@gmail.com";
    public static final String PASSWORD = "password";
    public static final String INCORRECT_PASSWORD = "passworddd";

    public static final int ADMIN_USER_ID = 1;
    public static final String ADMIN_FIRST_NAME = "Viola";
    public static final String ADMIN_LAST_NAME = "Robson";
    public static final Role ADMIN_ROLE = Role.ADMIN;

    public static final int PATIENT_USER_ID = 2;
    public static final int PATIENT_ID = 1;
    public static final String PATIENT_FIRST_NAME = "Barbara";
    public static final String PATIENT_LAST_NAME = "Henry";
    public static final String PATIENT_EMAIL = "patient@gmail.com";
    public static final Role PATIENT_ROLE = Role.PATIENT;
    public static final LocalDate PATIENT_DATE_OF_BIRTH = LocalDate.of(1990, 1, 30);
    public static final Gender PATIENT_GENDER = Gender.FEMALE;

    public static final int DOCTOR_USER_ID = 3;
    public static final int DOCTOR_STAFF_ID = 1;
    public static final String DOCTOR_FIRST_NAME = "Peter";
    public static final String DOCTOR_LAST_NAME = "Smith";
    public static final String DOCTOR_EMAIL = "doctor@gmail.com";
    public static final Role DOCTOR_ROLE = Role.DOCTOR;
    public static final String SPECIALISATION = Specialisation.ALLERGISTS_AND_IMMUNOLOGISTS.getSpecialisation();

    public static final int NURSE_USER_ID = 4;
    public static final int NURSE_STAFF_ID = 2;
    public static final String NURSE_FIRST_NAME = "Julia";
    public static final String NURSE_LAST_NAME = "Stone";
    public static final Role NURSE_ROLE = Role.NURSE;

    public static final LocalDateTime APPOINTMENT_DATE_TIME = LocalDateTime.of(2023, 1, 24, 8, 0);
    public static final AppointmentType APPOINTMENT_TYPE = AppointmentType.ANALYSIS;
    public static final String APPOINTMENT_DESCRIPTION = "General blood analysis";
    public static final String APPOINTMENT_STATUS = "ASSIGNED";

    public static final int HOSPITALISATION_ID = 1;
    public static final String DIAGNOSIS = "Severe allergic reaction";
    public static final LocalDate HOSPITALISATION_DATE = LocalDate.of(2023, 1, 23);
    public static final LocalDate DISCHARGING_DATE = LocalDate.of(2023, 1, 31);

    public static UserTo getAdminUserTo() {
        return new UserTo.Builder<>().id(ADMIN_USER_ID).firstName(ADMIN_FIRST_NAME)
                .lastName(ADMIN_LAST_NAME).role(ADMIN_ROLE).build();
    }

    public static UserTo getDoctorUserTo() {
        return new UserTo.Builder<>().id(DOCTOR_USER_ID).firstName(DOCTOR_FIRST_NAME)
                .lastName(DOCTOR_LAST_NAME).role(DOCTOR_ROLE).build();
    }

    public static UserTo getNurseUserTo() {
        return new UserTo.Builder<>().id(NURSE_USER_ID).firstName(NURSE_FIRST_NAME)
                .lastName(NURSE_LAST_NAME).role(NURSE_ROLE).build();
    }

    public static UserTo getPatientUserTo() {
        return new UserTo.Builder<>().id(PATIENT_USER_ID).firstName(PATIENT_FIRST_NAME)
                .lastName(PATIENT_LAST_NAME).role(PATIENT_ROLE).build();
    }

    public static StaffTo getStaffTo() {
        return new StaffTo.Builder().id(DOCTOR_STAFF_ID).userId(DOCTOR_USER_ID).firstName(DOCTOR_FIRST_NAME)
                .lastName(DOCTOR_LAST_NAME).specialisation(SPECIALISATION).build();
    }

    public static PatientTo getPatientTo() {
        return new PatientTo.Builder().id(PATIENT_ID).userId(PATIENT_USER_ID).firstName(PATIENT_FIRST_NAME)
                .lastName(PATIENT_LAST_NAME).dateOfBirth(PATIENT_DATE_OF_BIRTH).gender(PATIENT_GENDER).build();
    }

    private TestData() {
    }
}
