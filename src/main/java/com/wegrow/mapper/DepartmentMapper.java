package com.wegrow.mapper;

import com.wegrow.dto.DepartmentCreateRequest;
import com.wegrow.dto.DepartmentResponse;
import com.wegrow.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentCreateRequest request) {
        Department department = new Department();

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        return department;
    }

    public DepartmentResponse toResponse(Department department) {
        return new DepartmentResponse(
            department.getId(),
            department.getName(),
            department.getDescription()
        );
    }
}
