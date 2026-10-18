
package com.wegrow.mapper;

import com.wegrow.dto.EmployeeCreateRequest;
import com.wegrow.dto.EmployeeResponse;
import com.wegrow.entity.Department;
import com.wegrow.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeCreateRequest request, Department department) {
        return new Employee(
                request.getEmployeeNumber(),
                request.getFirstName(),
                request.getLastName(),
                request.getJobTitle(),
                request.getContractType(),
                department
        );
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeNumber(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getContractType()
//                employee.getDepartment().getId(),
//                employee.getDepartment().getName()
        );
    }
}
