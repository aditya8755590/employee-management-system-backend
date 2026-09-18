package com.harsh.employee.service;

import com.harsh.employee.model.EmployeeDto;

import java.util.List;

/**
 * Operations for managing employee records.
 */
public interface EmployeeService {

    /**
     * Persist a new employee and return the created record.
     *
     * @param employeeDto employee data supplied by the caller
     * @return persisted employee including any generated fields
     */
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    /**
     * Return every employee in the system.
     *
     * @return all employees, possibly empty
     */
    List<EmployeeDto> getAllEmployees();

    /**
     * Locate an employee by its unique identifier.
     *
     * @param id primary key of the employee
     * @return matching employee or {@code null} when not found
     */
    EmployeeDto getEmployeeById(Long id);

    /**
     * Remove an employee and return the deleted record.
     *
     * @param id primary key of the employee to delete
     * @return deleted employee or {@code null} when not found
     */
    EmployeeDto deleteEmployeeById(Long id);

    /**
     * Replace mutable fields of an existing employee.
     *
     * @param id         primary key of the target employee
     * @param employeeDto updated field values
     * @return updated employee or {@code null} when not found
     */
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
}