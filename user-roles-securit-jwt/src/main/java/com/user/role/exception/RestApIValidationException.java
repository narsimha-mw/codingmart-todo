package com.user.role.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

abstract class RestAPISubError{

}
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RestApIValidationException extends RestAPISubError{

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

   public RestApIValidationException(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
