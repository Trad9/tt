package com.yolo.testtask.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ WebExchangeBindException.class })
    public ResponseEntity<ErrorResource> methodArgumentNotValidException(
            final WebExchangeBindException constraintViolationException) {
        log.error(constraintViolationException.getMessage());
        final Map<String, List<String>> error = new HashMap<>();
        constraintViolationException.getAllErrors().forEach(e -> error.put(e.getCodes()[0], List.of(e.getDefaultMessage())));
        return ResponseEntity.badRequest().body(ErrorResource.builder().messages(error).type("VALIDATION").build());
    }


}
