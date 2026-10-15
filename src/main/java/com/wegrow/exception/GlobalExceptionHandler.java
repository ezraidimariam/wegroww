package com.wegrow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDepartmentAlreadyExists(DepartmentAlreadyExistsException exception) {

        Map<String, String> error = Map.of(
            "message", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
