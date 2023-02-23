package com.epam.hospital.model;

import java.time.LocalDateTime;

public class Appointment extends Entity {
    private int hospitalisationId;
    private int patientId;
    private int staffId;
    private LocalDateTime dateTime;
    private String type;
    private String description;
    private String conclusion;
    private String status;

    public Appointment(Builder builder){
        super(builder.id);
        this.hospitalisationId = builder.hospitalisationId;
        this.patientId = builder.patientId;
        this.staffId = builder.staffId;
        this.dateTime = builder.dateTime;
        this.type = builder.type;
        this.description = builder.description;
        this.conclusion = builder.conclusion;
        this.status = builder.status;
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
                "id=" + super.getId() +
                ", hospitalisationId=" + hospitalisationId +
                ", patientId=" + patientId +
                ", staffId=" + staffId +
                ", dateTime=" + dateTime +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder{
        private int id;
        private int hospitalisationId;
        private int patientId;
        private int staffId;
        private LocalDateTime dateTime;
        private String type;
        private String description;
        private String conclusion;
        private String status;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder hospitalisationId(int hospitalisationId) {
            this.hospitalisationId = hospitalisationId;
            return this;
        }

        public Builder patientId(int patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder staffId(int staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder conclusion(String conclusion) {
            this.conclusion = conclusion;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Appointment build() {
            return new Appointment(this);
        }
    }
}
