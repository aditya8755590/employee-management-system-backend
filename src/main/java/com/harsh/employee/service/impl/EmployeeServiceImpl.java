package com.harsh.employee.service.impl;

import com.harsh.employee.entity.EmployeeDto;
import com.harsh.employee.model.Employee;
import com.harsh.employee.repository.EmployeeRepository;
import com.harsh.employee.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        BeanUtils.copyProperties(employee, employeeDto);
        employeeRepository.save(employeeDto);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<EmployeeDto> employeesDto = employeeRepository.findAll();

        return employeesDto
                .stream()
                .map((empDto) ->
                        new Employee(empDto.getId(),
                                empDto.getFirstName(),
                                empDto.getLastName(),
                                empDto.getEmailId()))
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(long id) {
        EmployeeDto employeeDto = employeeRepository.findById(id).orElse(null);

        if (employeeDto == null) {
            return null;
        }

        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmailId()
        );
    }

    @Override
    public Employee deleteEmployeeById(long id) {
        EmployeeDto employeeDto = employeeRepository.findById(id).orElse(null);
        employeeRepository.deleteById(id);

        if(employeeDto != null) {
            return new Employee(
                    employeeDto.getId(),
                    employeeDto.getFirstName(),
                    employeeDto.getLastName(),
                    employeeDto.getEmailId()
            );
        }

        return null;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeDto employeeDto = employeeRepository.findById(id).orElse(null);

        if (employeeDto == null) {
            return null;
        }

        BeanUtils.copyProperties(employee, employeeDto);

        employeeRepository.save(employeeDto);

        return employee;
    }


}
