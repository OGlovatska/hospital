package com.epam.hospital.model;

public class Staff extends User {
    private int userId;
    private String specialisation;

    private Staff(Builder builder){
        super(builder);
        this.userId = builder.userId;
        this.specialisation = builder.specialisation;
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

    @Override
    public String toString() {
        return " Staff{" +
                "id=" + super.getId() +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", role=" + super.getRole() +
                ", userId=" + userId +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }

    public static class Builder extends User.Builder<Staff.Builder> {
        private int userId;
        private String specialisation;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder specialisation(String specialisation) {
            this.specialisation = specialisation;
            return this;
        }

        public Staff build() {
            return new Staff(this);
        }
    }
}
