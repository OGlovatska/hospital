package com.epam.hospital.dao.constant.query;

public class PatientQuery {
    public static final String GET_PATIENT_BY_ID = "SELECT patient.id AS id, user_id, first_name, last_name, email, " +
            "date_of_birth, gender, role FROM patient JOIN users ON patient.user_id=users.id WHERE patient.id=%s";

    public static final String GET_PATIENT_BY_USER_ID = "SELECT patient.id AS id, user_id, first_name, last_name, email, " +
            "date_of_birth, gender, role FROM patient JOIN users ON patient.user_id=users.id WHERE patient.user_id=%s";

    public static final String ADD_PATIENT = "INSERT INTO patient (user_id, date_of_birth, gender) VALUES (?, ?, ?)";

    public static final String GET_ALL_PATIENTS = "SELECT patient.id AS id, user_id, first_name, last_name, email, " +
            "date_of_birth, gender, role FROM users INNER JOIN patient ON users.id=patient.user_id %s";

    public static final String GET_ALL_PATIENTS_OF_STAFF = "SELECT patient.id AS id, user_id, first_name, last_name, " +
            "email, date_of_birth, gender, role FROM staff_patient INNER JOIN patient ON staff_patient.patient_id=patient.id " +
            "INNER JOIN users ON patient.user_id=users.id AND staff_patient.staff_id=%s %s";

    public static final String GET_ALL_PATIENTS_NOT_ASSIGNED_TO_STAFF = "SELECT DISTINCT patient.id AS id, user_id, " +
            "first_name, last_name, email, date_of_birth, gender, role FROM patient INNER JOIN users ON " +
            "patient.user_id=users.id WHERE patient.id NOT IN (SELECT patient_id FROM staff_patient WHERE staff_id=%s)";

    public static final String GET_PATIENTS_COUNT = "SELECT COUNT(*) as patients FROM patient";

    private PatientQuery() {
    }
}
