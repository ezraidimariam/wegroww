package com.wegrow.controller;

import com.wegrow.dto.DepartmentListResponse;
import com.wegrow.dto.DepartmentRequest;
import com.wegrow.dto.DepartmentResponse;
import com.wegrow.entity.Department;
import com.wegrow.mapper.DepartmentMapper;
import com.wegrow.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService, DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse add(@Valid @RequestBody DepartmentRequest request) {
        Department department = departmentMapper.toEntity(request);
        Department savedDepartment = departmentService.add(department);
        return departmentMapper.toResponse(savedDepartment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentResponse> getAll() {
        return departmentService.getAll().stream()
//             method reference
//            .map(DepartmentMapper::toResponse) static method
//            .map(departmentMapper::toResponse).toList(); instance method
            .map(department -> departmentMapper.toResponse(department))
            .toList();
    }


    @GetMapping("/with-employee-count")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentListResponse> findAllWithEmployeeCount() {
        return departmentService.findAllWithEmployeeCount();
    }

}