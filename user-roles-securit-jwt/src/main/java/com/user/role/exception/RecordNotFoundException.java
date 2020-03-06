package com.user.role.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;
    private HttpStatus status;

    public RecordNotFoundException(String message, HttpStatus status) {
        super(message);
        this.message=message;
        this.status=status;
    }

    public RecordNotFoundException(String message) {
        this.message=message;

    }
}
