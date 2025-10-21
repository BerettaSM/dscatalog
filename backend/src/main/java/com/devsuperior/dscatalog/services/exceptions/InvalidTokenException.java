package com.devsuperior.dscatalog.services.exceptions;

import org.springframework.http.HttpStatus;

import com.devsuperior.dscatalog.exceptions.ApplicationException;

public class InvalidTokenException extends ApplicationException {

    public InvalidTokenException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public InvalidTokenException(String message, Throwable cause) {
        this(message, cause, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public InvalidTokenException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public InvalidTokenException(Throwable cause, HttpStatus httpStatus) {
        this(cause.getMessage(), cause, httpStatus);
    }

    public InvalidTokenException(String message) {
        this(message, (Throwable) null);
    }

    public InvalidTokenException() {
        this("Invalid token");
    }

}
