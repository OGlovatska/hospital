package com.epam.hospital.model;

import java.time.LocalDate;

public class Hospitalisation extends Entity{
    private int patientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String diagnosis;

    public Hospitalisation() {
    }

    public Hospitalisation(int id, int patientId, LocalDate startDate, LocalDate endDate, String status, String diagnosis) {
        super(id);
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.diagnosis = diagnosis;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Hospitalisation{" +
                "patientId=" + patientId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}
