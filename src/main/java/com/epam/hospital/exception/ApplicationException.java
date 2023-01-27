package com.epam.hospital.exception;

public class ApplicationException extends RuntimeException {
    private final ErrorType type;

    public ApplicationException(ErrorType type) {
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
