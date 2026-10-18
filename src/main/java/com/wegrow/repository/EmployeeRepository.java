package com.wegrow.repository;

import com.wegrow.dto.EmployeeResponse;
import com.wegrow.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Locale;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeNumber(String employeeNumber);
    List<Employee> findByDepartmentId(Long departmentId);
}
