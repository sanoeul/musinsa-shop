package com.musinsa.report.parksanhee.exception;

import com.musinsa.report.parksanhee.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthException(NotFoundException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getStatusCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }
    @ExceptionHandler({NotAllowNegativeNumberException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> handleAuthException(NotAllowNegativeNumberException e) {
        HttpStatus statusCode= HttpStatus.BAD_REQUEST;
        ErrorResponseDto errorResponse = new ErrorResponseDto(statusCode, e.getMessage());
        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
