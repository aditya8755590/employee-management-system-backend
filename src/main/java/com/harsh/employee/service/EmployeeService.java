package com.harsh.employee.service;

import com.harsh.employee.model.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employee);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto deleteEmployeeById(Long id);

    EmployeeDto updateEmployee(Long id, EmployeeDto employee);
}
