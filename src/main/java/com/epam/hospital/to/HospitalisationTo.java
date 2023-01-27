package com.epam.hospital.to;

import java.time.LocalDate;
import java.util.List;

public class HospitalisationTo {
    private int id;
    private int patientId;
    private String patientFirstName;
    private String patientLastName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String diagnosis;
    private List<AppointmentTo> appointments;

    public HospitalisationTo() {
    }

    public HospitalisationTo(int id, int patientId, LocalDate startDate, LocalDate endDate, String status, String diagnosis) {
        this.id = id;
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.diagnosis = diagnosis;
    }

    public HospitalisationTo(int id, int patientId, String patientFirstName, String patientLastName,
                             LocalDate startDate, LocalDate endDate, String status, String diagnosis) {
        this.id = id;
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public List<AppointmentTo> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentTo> appointments) {
        this.appointments = appointments;
    }
}
