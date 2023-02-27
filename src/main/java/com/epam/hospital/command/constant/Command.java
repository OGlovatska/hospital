package com.epam.hospital.command.constant;

public class Command {
    public static final String CONTROLLER = "api";
    public static final String COMMAND = "command";
    public static final String EMPTY = "";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String MAIN = "main";
    public static final String ABOUT = "about";
    public static final String STAFF_LIST = "staff-list";
    public static final String SAVE_STAFF = "save-staff";
    public static final String STAFF_DETAILS = "staff";
    public static final String ASSIGN_PATIENT_TO_STAFF = "assign-patient-to-staff";
    public static final String PATIENTS_LIST = "patients-list";
    public static final String SAVE_PATIENT = "save-patient";
    public static final String PATIENT_DETAILS = "patient";
    public static final String ASSIGN_STAFF_TO_PATIENT = "assign-staff-to-patient";
    public static final String APPOINTMENTS = "appointments-list";
    public static final String HOSPITALISATIONS = "hospitalisations-list";
    public static final String CREATE_APPOINTMENT = "create-appointment";
    public static final String SAVE_APPOINTMENT = "save-appointment";
    public static final String SAVE_HOSPITALISATION = "save-hospitalisation";
    public static final String DISCHARGE_PATIENT = "discharge-patient";
    public static final String DETERMINE_DIAGNOSIS = "determine-diagnosis";
    public static final String HOSPITALISATION_DETAILS = "hospitalisation";
    private Command() {
    }
}
