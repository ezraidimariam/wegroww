package com.wegrow.service;

import com.wegrow.entity.Department;
import com.wegrow.exception.DepartmentAlreadyExistsException;
import com.wegrow.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Department add(Department department) {
        if(departmentRepository.existsByName(department.getName())) {
            throw new DepartmentAlreadyExistsException(department.getName());
        }
        return departmentRepository.save(department);
    }
}