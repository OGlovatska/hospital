package com.epam.hospital.model.enums;

public enum MessageType {
    SUCCESS_SAVE_APPOINTMENT("message.success.save.appointment"),
    SUCCESS_DETERMINE_DIAGNOSIS("message.success.determine.diagnosis"),
    SUCCESS_DISCHARGE_PATIENT("message.success.discharge.patient"),
    SUCCESS_SAVE_HOSPITALISATION("message.success.save.hospitalisation"),
    SUCCESS_ASSIGNED_STAFF_TO_PATIENT("message.success.assigned.staff.to.patient"),
    SUCCESS_SAVE_PATIENT("message.success.save.patient"),
    SUCCESS_ASSIGNED_PATIENT_TO_STAFF("message.success.assigned.patient.to.staff"),
    SUCCESS_SAVE_STAFF("message.success.save.staff");

    private final String messageCode;

    MessageType(String message) {
        this.messageCode = message;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
