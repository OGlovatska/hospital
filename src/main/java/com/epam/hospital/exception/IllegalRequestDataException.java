package com.epam.hospital.exception;

public class IllegalRequestDataException extends ApplicationException {

    public IllegalRequestDataException(ErrorType type) {
        super(type);
    }
}
