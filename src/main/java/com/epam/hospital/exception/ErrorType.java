package com.epam.hospital.exception;

public enum ErrorType {
    INVALID_PASSWORD("error.invalidPassword", "Wrong password"),
    USER_NOT_FOUND("error.userNotFound", "User not found"),
    EMPTY_FIELDS("error.emptyFields", "Email or password is blank"),
    NOT_AUTHORIZED_USER("error.userNotAuthorized", "User not found"),
    PATIENT_NOT_HOSPITALISED("error.patientNotHospitalised", "Patient not hospitalised"),
    PATIENT_NOT_FOUND("error.patientNotFound", "Patient not found"),
    NOT_STAFF("error.userNotStaff", "User role must be DOCTOR or NURSE"),
    NOT_DOCTOR("error.userNotDoctor", "User role must be DOCTOR"),
    NOT_ADMIN("error.userNotAdmin", "User role must be ADMIN"),
    STAFF_NOT_FOUND("error.staffNotFound", "Staff not found"),
    NO_DIAGNOSIS("error.noDiagnosis", "Only patients with determined diagnosis can be discharged"),
    NOT_DISCHARGED("error.notDischarged", "Can't add new hospitalisation for undischarged patient"),
    DUPLICATE_EMAIL("error.duplicateEmail", "User with this email already exists"),

    WRONG_REQUEST("error.wrongRequest", "Bad request"),
    APP_ERROR("error.appError", "Internal server error"),
    DATA_NOT_FOUND("error.dataNotFound", "Unprocessable entity"),
    DATA_ERROR("error.dataError", "Conflict"),
    VALIDATION_ERROR("error.validationError", "Unprocessable entity");

    private final String errorCode;
    private final String errorMessage;

    ErrorType(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
