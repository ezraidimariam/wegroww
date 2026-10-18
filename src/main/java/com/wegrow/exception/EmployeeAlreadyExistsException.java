package com.wegrow.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String employeeNumber) {
        super("Employee number already exists: " + employeeNumber);
    }
}
