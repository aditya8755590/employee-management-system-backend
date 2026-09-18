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
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::fromEntity)
                .orElse(null);
    }

    @Override
    public Employee deleteEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    employeeRepository.deleteById(id);
                    return fromEntity(entity);
                })
                .orElse(null);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeDto entity = employeeRepository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        BeanUtils.copyProperties(employee, entity);
        employeeRepository.save(entity);

        return employee;
    }

    private Employee fromEntity(EmployeeDto entity) {
        return new Employee(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailId()
        );
    }

    private EmployeeDto toEntity(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }
}
