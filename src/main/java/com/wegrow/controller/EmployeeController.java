package com.wegrow.controller;

import com.wegrow.dto.EmployeeRequest;
import com.wegrow.dto.EmployeeResponse;
import com.wegrow.entity.Employee;
import com.wegrow.mapper.EmployeeMapper;
import com.wegrow.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    public EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee employee = employeeService.add(request);
        return employeeMapper.toResponse(employee);
    }
}
