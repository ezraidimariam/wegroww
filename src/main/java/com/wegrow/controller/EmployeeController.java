package com.wegrow.controller;

import com.wegrow.dto.EmployeeCreateRequest;
import com.wegrow.dto.EmployeeResponse;
import com.wegrow.mapper.EmployeeMapper;
import com.wegrow.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements/{id}/employes")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@PathVariable("id") Long departmentId, @Valid @RequestBody EmployeeCreateRequest request) {
        return employeeService.add(departmentId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable("id") Long departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }
}
