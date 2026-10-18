package com.wegrow.service;

import com.wegrow.dto.EmployeeRequest;
import com.wegrow.dto.EmployeeResponse;
import com.wegrow.entity.Department;
import com.wegrow.entity.Employee;
import com.wegrow.exception.EmployeeAlreadyExistsException;
import com.wegrow.exception.ResourceNotFoundException;
import com.wegrow.mapper.EmployeeMapper;
import com.wegrow.repository.DepartmentRepository;
import com.wegrow.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public EmployeeResponse add(@Param("id") Long departmentId, EmployeeRequest request) {

        if(employeeRepository.existsByEmployeeNumber(request.getEmployeeNumber())) {
            throw new EmployeeAlreadyExistsException(request.getEmployeeNumber());
        }

        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        Employee employee = employeeMapper.toEntity(request, department);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }
}
