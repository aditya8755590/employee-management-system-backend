package com.harsh.employee.service.impl;

import com.harsh.employee.entity.EmployeeDto;
import com.harsh.employee.model.Employee;
import com.harsh.employee.repository.EmployeeRepository;
import com.harsh.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        employeeRepository.save(toEntity(employee));
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .map(this::fromEntity)
                .orElse(null);
    }

    @Override
    public Employee deleteEmployeeById(long id) {
        EmployeeDto employeeDto = employeeRepository.findById(id).orElse(null);
        employeeRepository.deleteById(id);

        if(employeeDto != null) {
            return fromEntity(employeeDto);
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

    private Employee fromEntity(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmailId()
        );
    }

    private EmployeeDto toEntity(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }
}
