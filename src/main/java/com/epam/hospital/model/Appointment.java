package com.epam.hospital.model;

import java.time.LocalDateTime;

public class Appointment extends Entity{
    private int hospitalisationId;
    private int patientId;
    private int staffId;
    private LocalDateTime dateTime;
    private String type;
    private String description;
    private String conclusion;
    private String status;

    public Appointment() {
    }

    public Appointment(int id, int hospitalisationId, int patientId, int staffId, LocalDateTime dateTime, String type, String description, String conclusion, String status) {
        super(id);
        this.hospitalisationId = hospitalisationId;
        this.patientId = patientId;
        this.staffId = staffId;
        this.dateTime = dateTime;
        this.type = type;
        this.description = description;
        this.conclusion = conclusion;
        this.status = status;
    }

    public int getHospitalisationId() {
        return hospitalisationId;
    }

    public void setHospitalisationId(int hospitalisationId) {
        this.hospitalisationId = hospitalisationId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "hospitalisationId=" + hospitalisationId +
                ", patientId=" + patientId +
                ", staffId=" + staffId +
                ", dateTime=" + dateTime +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
