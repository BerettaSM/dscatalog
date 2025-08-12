package com.devsuperior.dscatalog.services.exceptions;

import org.springframework.http.HttpStatus;

import com.devsuperior.dscatalog.exceptions.ApplicationException;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        this(message, cause, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public ResourceNotFoundException(String message) {
        this(message, (Throwable) null);
    }

    public ResourceNotFoundException() {
        this("Resource not found.");
    }

}
