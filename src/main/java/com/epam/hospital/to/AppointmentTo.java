package com.epam.hospital.to;

import java.time.LocalDateTime;

public class AppointmentTo {
    private int id;
    private int hospitalisationId;
    private int patientId;
    private int staffId;
    private LocalDateTime dateTime;
    private String type;
    private String description;
    private String conclusion;
    private String status;

    private String patientFirstName;
    private String patientLastName;
    private String staffFirstName;
    private String staffLastName;
    private String specialisation;

    public AppointmentTo() {
    }

    public AppointmentTo(int id, int hospitalisationId, int patientId, int staffId, LocalDateTime dateTime,
                         String type, String description, String conclusion, String status, String patientFirstName,
                         String patientLastName, String staffFirstName, String staffLastName, String specialisation) {
        this.id = id;
        this.hospitalisationId = hospitalisationId;
        this.patientId = patientId;
        this.staffId = staffId;
        this.dateTime = dateTime;
        this.type = type;
        this.description = description;
        this.conclusion = conclusion;
        this.status = status;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.staffFirstName = staffFirstName;
        this.staffLastName = staffLastName;
        this.specialisation = specialisation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getStaffFirstName() {
        return staffFirstName;
    }

    public void setStaffFirstName(String staffFirstName) {
        this.staffFirstName = staffFirstName;
    }

    public String getStaffLastName() {
        return staffLastName;
    }

    public void setStaffLastName(String staffLastName) {
        this.staffLastName = staffLastName;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public String toString() {
        return "AppointmentTo{" +
                "id=" + id +
                ", hospitalisationId=" + hospitalisationId +
                ", patientId=" + patientId +
                ", staffId=" + staffId +
                ", dateTime=" + dateTime +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", status='" + status + '\'' +
                ", patientFirstName='" + patientFirstName + '\'' +
                ", patientLastName='" + patientLastName + '\'' +
                ", staffFirstName='" + staffFirstName + '\'' +
                ", staffLastName='" + staffLastName + '\'' +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }
}
