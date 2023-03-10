package com.epam.hospital.model;

import com.epam.hospital.model.enums.HospitalisationStatus;

import java.time.LocalDate;

public class Hospitalisation extends Entity {
    private int patientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private HospitalisationStatus status;
    private String diagnosis;

    private Hospitalisation(Builder builder) {
        super(builder.id);
        this.patientId = builder.patientId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
        this.diagnosis = builder.diagnosis;
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

    @Override
    public String toString() {
        return "Hospitalisation{" +
                "id=" + super.getId() +
                ", patientId=" + patientId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private int patientId;
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

        public Hospitalisation build() {
            return new Hospitalisation(this);
        }
    }
}
