package com.musinsa.report.parksanhee.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final HttpStatus status;
    private final String reason;
    private final LocalDateTime dateTime = LocalDateTime.now();

    public String getDateTime() {
        return dateTime.toString();
    }
}
