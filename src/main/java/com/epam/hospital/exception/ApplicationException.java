package com.epam.hospital.exception;

public class ApplicationException extends RuntimeException {
    private final ErrorType type;

    public ApplicationException(ErrorType type) {
        super(type.getErrorMessage());
        this.type = type;
    }

    public ApplicationException(String message, ErrorType type) {
        super(message);
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
