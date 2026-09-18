package com.harsh.employee.repository;

import com.harsh.employee.entity.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeDto, Long> {

}
