package com.epam.hospital.dao.constant.query;

public class StaffQueries {
    public static final String GET_STAFF_BY_ID = "SELECT staff.id AS id, user_id, first_name, last_name, email, " +
            "specialisation, role FROM staff JOIN users ON staff.user_id=users.id WHERE staff.id=%s";

    public static final String GET_STAFF_BY_USER_ID = "SELECT staff.id AS id, user_id, first_name, last_name, email, " +
            "specialisation, role FROM staff JOIN users ON staff.user_id=users.id WHERE staff.user_id=%s";

    public static final String ADD_STAFF = "INSERT INTO staff (user_id, specialisation) VALUES (?, ?)";

    public static final String GET_ALL_STAFF = "SELECT staff.id AS id, user_id, email, first_name, " +
            "last_name, role, specialisation, patients FROM staff staff LEFT JOIN(SELECT * FROM users) users " +
            "ON staff.user_id=users.id LEFT JOIN(SELECT staff_id, COUNT(*) AS patients FROM staff_patient GROUP BY staff_id) staff_patient " +
            "ON staff_patient.staff_id=staff.id %s";

    public static final String GET_ALL_STAFF_OF_PATIENT = "SELECT staff.id AS id, user_id, first_name, last_name, " +
            "email, specialisation, role FROM staff_patient INNER JOIN staff ON staff_patient.staff_id=staff.id " +
            "INNER JOIN users ON staff.user_id=users.id AND staff_patient.patient_id=%s %s";

    public static final String GET_ALL_STAFF_NOT_ASSIGNED_TO_PATIENT = "SELECT DISTINCT staff.id AS id, user_id, " +
            "first_name, last_name, email, specialisation, role FROM staff INNER JOIN users ON staff.user_id=users.id " +
            "WHERE staff.id NOT IN (SELECT staff_id FROM staff_patient WHERE patient_id=%s)";

    public static final String GET_STAFF_COUNT = "SELECT COUNT(*) as staff FROM staff";

    private StaffQueries() {
    }
}
