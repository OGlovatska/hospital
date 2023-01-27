package com.epam.hospital.to;

public class StaffTo extends UserTo {
    private int userId;
    private String specialisation;
    private int patients;

    public StaffTo(int id, String firstName, String lastName, String email, String role,
                   int userId, String specialisation, int patients) {
        super(id, firstName, lastName, email, role);
        this.specialisation = specialisation;
        this.patients = patients;
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
                "specialisation='" + specialisation + '\'' +
                ", patients=" + patients +
                '}';
    }
}
