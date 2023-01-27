package com.epam.hospital.model.enums;

public enum Specialisation {
    REGISTER_NURSE("Registered Nurse"),
    CARDIAC_NURSE("Cardiac Nurse"),
    PEDIATRIC_NURSE("Pediatric Nurse"),
    ALLERGISTS_AND_IMMUNOLOGISTS("Allergists and immunologists"),
    ANESTHESIOLOGISTS("Anesthesiologists");

    private final String specialisation;

    private Specialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getSpecialisation() {
        return specialisation;
    }
}
