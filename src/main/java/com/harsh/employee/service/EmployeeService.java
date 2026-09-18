package com.harsh.employee.service;

import com.harsh.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee deleteEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);
}
