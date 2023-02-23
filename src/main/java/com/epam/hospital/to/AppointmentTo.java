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

    private AppointmentTo(Builder builder){
        this.id = builder.id;
        this.hospitalisationId = builder.hospitalisationId;
        this.patientId = builder.patientId;
        this.staffId = builder.staffId;
        this.dateTime = builder.dateTime;
        this.type = builder.type;
        this.description = builder.description;
        this.conclusion = builder.conclusion;
        this.status = builder.status;
        this.patientFirstName = builder.patientFirstName;
        this.patientLastName = builder.patientLastName;
        this.staffFirstName = builder.staffFirstName;
        this.staffLastName = builder.staffLastName;
        this.specialisation = builder.specialisation;
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

    public static class Builder {
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

        public Builder patientFirstName(String patientFirstName) {
            this.patientFirstName = patientFirstName;
            return this;
        }

        public Builder patientLastName(String patientLastName) {
            this.patientLastName = patientLastName;
            return this;
        }

        public Builder staffFirstName(String staffFirstName) {
            this.staffFirstName = staffFirstName;
            return this;
        }

        public Builder staffLastName(String staffLastName) {
            this.staffLastName = staffLastName;
            return this;
        }

        public Builder specialisation(String specialisation) {
            this.specialisation = specialisation;
            return this;
        }

        public AppointmentTo build() {
            return new AppointmentTo(this);
        }
    }
}
