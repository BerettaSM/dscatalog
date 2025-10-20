package com.devsuperior.dscatalog.services.exceptions;

import org.springframework.http.HttpStatus;

import com.devsuperior.dscatalog.exceptions.ApplicationException;

public class EmailException extends ApplicationException {
    
    public EmailException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public EmailException(String message, Throwable cause) {
        this(message, cause, HttpStatus.BAD_REQUEST);
    }

    public EmailException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public EmailException(Throwable cause, HttpStatus httpStatus) {
        this(cause.getMessage(), cause, httpStatus);
    }

    public EmailException(String message) {
        this(message, (Throwable) null);
    }

    public EmailException() {
        this("Failed to send email.");
    }

}
