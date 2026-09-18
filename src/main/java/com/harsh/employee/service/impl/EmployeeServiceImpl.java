package com.harsh.employee.service.impl;

import com.harsh.employee.entity.EmployeeEntity;
import com.harsh.employee.mapper.EmployeeMapper;
import com.harsh.employee.model.EmployeeDto;
import com.harsh.employee.repository.EmployeeRepository;
import com.harsh.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        employeeRepository.save(EmployeeMapper.toEntity(employeeDto));
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper::fromEntity)
                .orElse(null);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        EmployeeEntity entity = employeeRepository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        employeeRepository.save(EmployeeMapper.copyInto(entity, employeeDto));

        return employeeDto;
    }

    @Override
    public EmployeeDto deleteEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    employeeRepository.deleteById(id);
                    return EmployeeMapper.fromEntity(entity);
                })
                .orElse(null);
    }
}