package com.musinsa.report.parksanhee.exception;

import com.musinsa.report.parksanhee.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }
    @ExceptionHandler(NotAllowNegativeNumberException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowNegativeNumberException(NotAllowNegativeNumberException e) {
        HttpStatus statusCode= HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(statusCode, e.getMessage());
        return ResponseEntity.status(statusCode).body(errorResponse);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus statusCode= HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(statusCode, e.getMessage());
        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
