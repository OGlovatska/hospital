package com.epam.hospital.model;

import com.epam.hospital.model.enums.Gender;

import java.time.LocalDate;
import java.util.Objects;

public class Patient extends User{
    private int userId;
    private LocalDate dateOfBirth;
    private Gender gender;

    private Patient(Builder builder){
        super(builder);
        this.userId = builder.userId;
        this.dateOfBirth = builder.dateOfBirth;
        this.gender = builder.gender;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Patient patient = (Patient) o;

        if (userId != patient.userId) return false;
        if (!Objects.equals(dateOfBirth, patient.dateOfBirth)) return false;
        return gender == patient.gender;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId;
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "Patient{" +
                "id=" + super.getId() +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", role=" + super.getRole() +
                ", userId=" + userId +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }

    public static class Builder extends User.Builder<Patient.Builder> {
        private int userId;
        private LocalDate dateOfBirth;
        private Gender gender;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}
