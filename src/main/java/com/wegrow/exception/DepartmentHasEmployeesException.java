package com.wegrow.exception;

public class DepartmentHasEmployeesException extends RuntimeException {
    public DepartmentHasEmployeesException(Long id) {
        super("Cannot delete department with employees. ID: " + id);
    }
}
