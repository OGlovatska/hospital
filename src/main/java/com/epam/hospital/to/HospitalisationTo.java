package com.epam.hospital.to;

import com.epam.hospital.model.enums.HospitalisationStatus;

import java.time.LocalDate;
import java.util.List;

public class HospitalisationTo {
    private int id;
    private int patientId;
    private String patientFirstName;
    private String patientLastName;
    private LocalDate startDate;
    private LocalDate endDate;
    private HospitalisationStatus status;
    private String diagnosis;

    private HospitalisationTo(Builder builder) {
        this.id = builder.id;
        this.patientId = builder.patientId;
        this.patientFirstName = builder.patientFirstName;
        this.patientLastName = builder.patientLastName;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
        this.diagnosis = builder.diagnosis;
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

    public HospitalisationStatus getStatus() {
        return status;
    }

    public void setStatus(HospitalisationStatus status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public static class Builder {
        private int id;
        private int patientId;
        private String patientFirstName;
        private String patientLastName;
        private LocalDate startDate;
        private LocalDate endDate;
        private HospitalisationStatus status;
        private String diagnosis;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder patientId(int patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder patientFirstName(String patientFirstName) {
            this.patientFirstName = patientFirstName;
            return this;
        }

        public Builder patientLastName(String patientLastName) {
            this.patientLastName = patientLastName;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder status(HospitalisationStatus status) {
            this.status = status;
            return this;
        }

        public Builder diagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
            return this;
        }

        public HospitalisationTo build() {
            return new HospitalisationTo(this);
        }
    }
}
