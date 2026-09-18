package com.harsh.employee.service;

import com.harsh.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Employee getEmployeeById(long id);

    Employee deleteEmployeeById(long id);

    Employee updateEmployee(Long id, Employee employee);
}
