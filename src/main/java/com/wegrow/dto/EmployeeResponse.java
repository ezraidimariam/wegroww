
package com.wegrow.dto;

import com.wegrow.enums.ContractType;

public record EmployeeResponse (
    Long id,
    String employeeNumber,
    String firstName,
    String lastName,
    String jobTitle,
    ContractType contractType
) {}