package com.musinsa.report.parksanhee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class NotFoundException extends RuntimeException {
    private final HttpStatus statusCode = HttpStatus.NOT_FOUND;
    public NotFoundException(String message) {
        super(message);
    }
}
