package com.certification.app.exception.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomWebException extends RuntimeException {

    private final String message;
    private final String traceId;
    private final HttpStatus status;
    private Throwable cause;

}
