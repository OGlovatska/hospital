package com.epam.hospital.to;

import com.epam.hospital.model.enums.Gender;

import java.time.LocalDate;

public class PatientTo extends UserTo {
    private int userId;
    private LocalDate dateOfBirth;
    private Gender gender;

    private PatientTo(Builder builder){
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

    public static class Builder extends UserTo.Builder<Builder>{
        private int userId;
        private LocalDate dateOfBirth;
        private Gender gender;

        public Builder userId(int userId){
            this.userId = userId;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(Gender gender){
            this.gender = gender;
            return this;
        }

        public PatientTo build(){
            return new PatientTo(this);
        }
    }
}
