package com.user.role.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {

    private String message;
    private HttpStatus status;

    public ApiError(String message,HttpStatus status) {
        super();
        this.status = status;
        this.message = message;
    }
    public ApiError( String message) {
        super();
        this.message = message;
    }
}
