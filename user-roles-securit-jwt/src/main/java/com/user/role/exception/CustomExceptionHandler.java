//package com.user.role.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.http.ResponseEntity;
//
//@ControllerAdvice
//public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(RecordNotFoundException.class)
//    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
//            (RecordNotFoundException ex, WebRequest request)
//    {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        String INCORRECT_REQUEST = "INCORRECT_REQUEST";
//        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(MissingHeaderInfoException.class)
//    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
//            (MissingHeaderInfoException ex, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        String BAD_REQUEST = "BAD_REQUEST";
//        ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//}
