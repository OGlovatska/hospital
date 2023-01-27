package com.epam.hospital.to;

import java.time.LocalDate;

public class PatientTo extends UserTo {
    private int userId;
    private LocalDate dateOfBirth;
    private String gender;

    public PatientTo() {
    }

    public PatientTo(int id, String firstName, String lastName, String email, String role,
                     int userId, LocalDate dateOfBirth, String gender) {
        super(id, firstName, lastName, email, role);
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
