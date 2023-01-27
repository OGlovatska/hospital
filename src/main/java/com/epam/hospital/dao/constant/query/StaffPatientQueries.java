package com.epam.hospital.dao.constant.query;

public class StaffPatientQueries {
    public static final String ADD_STAFF_PATIENT = "INSERT INTO staff_patient (staff_id, patient_id) VALUES (?, ?)";
    public static final String GET_COUNT_PATIENTS_OF_STAFF = "SELECT COUNT(*) as patients FROM staff_patient WHERE staff_id=?";
    public static final String GET_COUNT_STAFF_OF_PATIENT = "SELECT COUNT(*) as staff FROM staff_patient WHERE patient_id=?";
    private StaffPatientQueries() {
    }
}