package com.wegrow.dto;

public record DepartmentListResponse(
    Long id,
    String name,
    String description,
    long employeeCount
) {}