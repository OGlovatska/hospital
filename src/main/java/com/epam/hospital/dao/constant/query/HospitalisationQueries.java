package com.epam.hospital.dao.constant.query;

public class HospitalisationQueries {
    public static final String GET_HOSPITALISATION = "SELECT * FROM hospitalisation h WHERE h.id=?";

    public static final String GET_ALL_HOSPITALISATIONS = "SELECT * FROM hospitalisation %s";

    public static final String GET_ALL_HOSPITALISATIONS_OF_PATIENT = "SELECT * FROM hospitalisation WHERE patient_id=%s %s";

    public static final String ADD_HOSPITALISATION = "INSERT INTO hospitalisation (patient_id, start_date, end_date, status, " +
            "diagnosis) VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_HOSPITALISATION = "UPDATE hospitalisation SET start_date=?, end_date=?, status=?, " +
            "diagnosis=? WHERE id=?";

    public static final String GET_HOSPITALISATIONS_COUNT = "SELECT COUNT(*) as hospitalisations FROM hospitalisation";

    public static final String GET_HOSPITALISATIONS_OF_PATIENT_COUNT = "SELECT COUNT(*) as hospitalisations FROM hospitalisation WHERE patient_id=%s";

    public static final String GET_PATIENT_CURRENT_HOSPITALISATION = "SELECT * FROM hospitalisation h WHERE h.patient_id=? AND status='HOSPITALIZED'";

    private HospitalisationQueries() {
    }
}