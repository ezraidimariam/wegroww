
package com.wegrow.dto;

import com.wegrow.enums.ContractType;

public class EmployeeResponse {

    private Long id;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private ContractType contractType;
    private Long departmentId;
    private String departmentName;

    public EmployeeResponse() {
    }

    public EmployeeResponse(
            Long id,
            String employeeNumber,
            String firstName,
            String lastName,
            String jobTitle,
            ContractType contractType,
            Long departmentId,
            String departmentName) {

        this.id = id;
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.contractType = contractType;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}