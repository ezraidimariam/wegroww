package com.wegrow.service;

import com.wegrow.dto.DepartmentListResponse;
import com.wegrow.dto.DepartmentResponse;
import com.wegrow.entity.Department;
import com.wegrow.exception.DepartmentAlreadyExistsException;
import com.wegrow.exception.DepartmentHasEmployeesException;
import com.wegrow.exception.DepartmentNotFoundException;
import com.wegrow.mapper.DepartmentMapper;
import com.wegrow.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Transactional
    public Department add(Department department) {
        if(departmentRepository.existsByName(department.getName())) {
            throw new DepartmentAlreadyExistsException(department.getName());
        }
        return departmentRepository.save(department);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isEmpty()) {
            throw  new DepartmentNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.delete(department.get());
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

//    public List<DepartmentListResponse> findAllWithEmployeeCount() {
//        return departmentRepository.findAllWithEmployeeCount()
//            .stream()
//            .map(departmentMapper::toListResponse)
//            .toList();
//    }

    public List<DepartmentListResponse> findAllWithEmployeeCount() {
        return departmentRepository.findAllWithEmployeeCount();
    }
}