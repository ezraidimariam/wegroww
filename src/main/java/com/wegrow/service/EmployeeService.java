package com.wegrow.service;

import com.wegrow.dto.EmployeeRequest;
import com.wegrow.dto.EmployeeResponse;
import com.wegrow.entity.Department;
import com.wegrow.entity.Employee;
import com.wegrow.exception.ResourceNotFoundException;
import com.wegrow.mapper.EmployeeMapper;
import com.wegrow.repository.DepartmentRepository;
import com.wegrow.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
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
    public Employee add(EmployeeRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + request.getDepartmentId()));

        Employee employee = employeeMapper.toEntity(request, department);


        return employeeRepository.save(employee);
    }
}
