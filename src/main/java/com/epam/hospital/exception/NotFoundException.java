package com.epam.hospital.exception;

public class NotFoundException extends ApplicationException{
    public NotFoundException(ErrorType type) {
        super(type);
    }
}
