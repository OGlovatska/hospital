package com.epam.hospital.dao.constant.query;

public class AppointmentQueries {
    public static final String GET_APPOINTMENT = "SELECT * FROM appointment a WHERE a.id=?";

    public static final String GET_ALL_APPOINTMENTS = "SELECT * FROM appointment %s";

    public static final String GET_ALL_APPOINTMENTS_BY_DATE = "SELECT * FROM appointment WHERE DATE(date_time)='%s' %s";

    public static final String ADD_APPOINTMENT = "INSERT INTO appointment (hospitalisation_id, patient_id, staff_id, " +
            "date_time, type, description, conclusion, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_APPOINTMENT = "UPDATE appointment SET staff_id=?, date_time=?, type=?, " +
            "description=?, conclusion=?, status=? WHERE id=?";

    public static final String GET_APPOINTMENTS_COUNT = "SELECT COUNT(*) as appointments FROM appointment";

    public static final String GET_APPOINTMENTS_OF_STAFF_COUNT = "SELECT COUNT(*) as appointments FROM appointment WHERE staff_id=%s";

    public static final String GET_APPOINTMENTS_FOR_DATE_COUNT = "SELECT COUNT(*) as appointments FROM appointment WHERE DATE(date_time)='%s'";

    public static final String GET_ALL_APPOINTMENTS_FOR_STAFF = "SELECT * FROM appointment WHERE staff_id=%s %s";

    public static final String GET_ALL_APPOINTMENTS_FOR_HOSPITALISATION = "SELECT * FROM appointment WHERE hospitalisation_id=%s";

    private AppointmentQueries() {
    }
}
