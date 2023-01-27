package com.epam.hospital.model;

public class StaffPatient extends Entity{
    private int staffId;
    private int patientId;

    public StaffPatient() {
    }

    public StaffPatient(int staffId, int patientId) {
        this.staffId = staffId;
        this.patientId = patientId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
