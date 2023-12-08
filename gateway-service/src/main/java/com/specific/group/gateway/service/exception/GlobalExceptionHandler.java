package com.specific.group.gateway.service.exception;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ErrorResponse mapError(HttpClientErrorException e) {
        return ErrorResponse.builder(e, e.getStatusCode(), e.getMessage()).build();
    }
}
