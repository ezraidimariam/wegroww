package com.wegrow.exception;

public class DepartmentHasEmployeesException extends RuntimeException {
    public DepartmentHasEmployeesException(String message) {
        super(message);
    }
}
