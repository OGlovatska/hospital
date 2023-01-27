package com.epam.hospital.exception;

public enum HttpStatus {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    CONFLICT(409, "Conflict"),
    BAD_REQUEST(400, "Bad Request");

    private final int value;
    private final String reasonPhrase;

    HttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
