package com.musinsa.report.parksanhee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAllowNegativeNumberException extends RuntimeException {
    private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;

    public NotAllowNegativeNumberException(String message) {
        super(message);
    }
}
