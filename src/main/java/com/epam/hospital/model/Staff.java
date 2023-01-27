package com.epam.hospital.model;

public class Staff extends User {
    private int userId;
    private String specialisation;

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
                "id=" + getId() +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", role=" + super.getRole() +
                ", userId=" + userId +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }
}
