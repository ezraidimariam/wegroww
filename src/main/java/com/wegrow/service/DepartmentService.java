package com.wegrow.service;

import com.wegrow.dto.DepartmentListResponse;
import com.wegrow.dto.DepartmentResponse;
import com.wegrow.entity.Department;
import com.wegrow.exception.DepartmentAlreadyExistsException;
import com.wegrow.exception.DepartmentHasEmployeesException;
import com.wegrow.exception.DepartmentNotFoundException;
import com.wegrow.exception.ResourceNotFoundException;
import com.wegrow.mapper.DepartmentMapper;
import com.wegrow.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return departmentRepository.saveAndFlush(department);
        } catch (DataIntegrityViolationException exception) {
            if (violatesConstraint(exception, "departments_name_key")) {
                throw new DepartmentAlreadyExistsException(department.getName());
            }
            throw exception;
        }
    }

    @Transactional
    public void delete(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isEmpty()) {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }
        long employeeCount = departmentRepository.countEmployeesByDepartmentId(id);
        if(employeeCount > 0) {
            throw new DepartmentHasEmployeesException(id);
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

    private boolean violatesConstraint(DataIntegrityViolationException exception, String constraintName) {
        Throwable cause = exception;
        while (cause != null) {
            if (cause instanceof ConstraintViolationException violation
                    && constraintName.equals(violation.getConstraintName())) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
