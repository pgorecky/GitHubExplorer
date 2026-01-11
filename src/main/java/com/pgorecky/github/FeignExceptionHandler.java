package com.pgorecky.github;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class FeignExceptionHandler {

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleFeignException(FeignException exception) {
        return Map.of(
                "status", String.valueOf(exception.status()),
                "message", "User not found"
        );
    }
}
