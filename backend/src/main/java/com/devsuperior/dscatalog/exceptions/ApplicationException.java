package com.devsuperior.dscatalog.exceptions;

import org.springframework.http.HttpStatus;

import com.devsuperior.dscatalog.utils.PathUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {

    protected final HttpStatus httpStatus;
    protected final String path;

    public ApplicationException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.path = PathUtils.getCurrentPath();
    }

    public ApplicationException(String message, Throwable cause) {
        this(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ApplicationException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public ApplicationException(Throwable cause, HttpStatus httpStatus) {
        this(cause.getMessage(), cause, httpStatus);
    }

    public ApplicationException(String message) {
        this(message, (Throwable) null);
    }

    public ApplicationException() {
        this("Oops! Something went wrong.");
    }

}
