package com.harsh.employee.service.impl;

import com.harsh.employee.entity.EmployeeEntity;
import com.harsh.employee.model.EmployeeDto;
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
    public EmployeeDto createEmployee(EmployeeDto employee) {
        employeeRepository.save(toEntity(employee));
        return employee;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::fromEntity)
                .orElse(null);
    }

    @Override
    public EmployeeDto deleteEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    employeeRepository.deleteById(id);
                    return fromEntity(entity);
                })
                .orElse(null);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employee) {
        EmployeeEntity entity = employeeRepository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        BeanUtils.copyProperties(employee, entity);
        employeeRepository.save(entity);

        return employee;
    }

    private EmployeeDto fromEntity(EmployeeEntity entity) {
        return new EmployeeDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailId()
        );
    }

    private EmployeeEntity toEntity(EmployeeDto employee) {
        EmployeeEntity employeeDto = new EmployeeEntity();

        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }
}
