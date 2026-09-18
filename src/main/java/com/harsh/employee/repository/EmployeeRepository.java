package com.harsh.employee.repository;

import com.harsh.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access layer for {@link EmployeeEntity} records.
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
