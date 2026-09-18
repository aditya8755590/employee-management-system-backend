package com.harsh.employee.repository;

import com.harsh.employee.entity.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDto, Long> {

}
