package com.wegrow.repository;

import com.wegrow.dto.DepartmentListProjection;
import com.wegrow.dto.DepartmentListResponse;
import com.wegrow.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);

//    Sql native : @SqlResultSetMapping
//    @Query(value = """
//    SELECT d.id AS id,
//           d.name AS name,
//           d.description AS description,
//           COUNT(e.id) AS "employeeCount"
//    FROM departments d
//    LEFT JOIN employees e ON e.department_id = d.id
//    GROUP BY d.id, d.name, d.description
//    """, nativeQuery = true)
//    List<DepartmentListProjection> findAllWithEmployeeCount();


//    JPAL  Java Persistence Query Language
//    Constructor Expression
    @Query("""
        SELECT new com.wegrow.dto.DepartmentListResponse(
            d.id,
            d.name,
            d.description,
            COUNT(e.id)
        )
        FROM Department d
        LEFT JOIN Employee e ON e.department = d
        GROUP BY d.id, d.name, d.description
    """)
    List<DepartmentListResponse> findAllWithEmployeeCount();
}