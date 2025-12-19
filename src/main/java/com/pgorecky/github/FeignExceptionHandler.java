package com.pgorecky.github;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Map;

@ControllerAdvice
public class FeignExceptionHandler {

    @ExceptionHandler(FeignException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Serializable> handleFeignException(FeignException exception) {
        return Map.of(
                "status", exception.status(),
                "message", "User not found"
        );
    }
}
