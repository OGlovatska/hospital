package com.epam.hospital.to;

public class StaffTo extends UserTo {
    private int userId;
    private String specialisation;
    private int patients;

    private StaffTo(Builder builder){
        super(builder);
        this.userId = builder.userId;
        this.specialisation = builder.specialisation;
        this.patients = builder.patients;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public int getPatients() {
        return patients;
    }

    public void setPatients(int patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "StaffTo{" +
                "id=" + super.getId() +
                ", firstName=" + super.getFirstName() +
                ", lastName=" + super.getLastName() +
                ", email=" + super.getEmail() +
                ", role=" + super.getRole() +
                ", userId=" + userId +
                ", specialisation='" + specialisation + '\'' +
                ", patients=" + patients +
                '}';
    }

    public static class Builder extends UserTo.Builder<Builder> {
        private int userId;
        private String specialisation;
        private int patients;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder specialisation(String specialisation) {
            this.specialisation = specialisation;
            return this;
        }

        public Builder patients(int patients) {
            this.patients = patients;
            return this;
        }

        public StaffTo build() {
            return new StaffTo(this);
        }
    }
}
