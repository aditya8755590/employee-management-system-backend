package com.harsh.employee.service.impl;

import com.harsh.employee.entity.EmployeeEntity;
import com.harsh.employee.model.EmployeeDto;
import com.harsh.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployeeSavesEntityAndReturnsInput() {
        EmployeeDto dto = dto();

        EmployeeDto result = employeeService.createEmployee(dto);

        assertSame(dto, result);
        verify(employeeRepository).save(any(EmployeeEntity.class));
    }

    @Test
    void getAllEmployeesMapsEntitiesToDtos() {
        when(employeeRepository.findAll()).thenReturn(List.of(entity(1L), entity(2L)));

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void getEmployeeByIdReturnsMappedEmployeeWhenFound() {
        when(employeeRepository.findById(7L)).thenReturn(Optional.of(entity(7L)));

        EmployeeDto result = employeeService.getEmployeeById(7L);

        assertEquals(7L, result.getId());
        assertEquals("ada", result.getFirstName());
    }

    @Test
    void getEmployeeByIdReturnsNullWhenMissing() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(employeeService.getEmployeeById(99L));
    }

    @Test
    void updateEmployeeSavesUpdatedEntityPreservingId() {
        EmployeeDto dto = dto();
        dto.setId(7L);
        when(employeeRepository.findById(7L)).thenReturn(Optional.of(entity(7L)));

        EmployeeDto result = employeeService.updateEmployee(7L, dto);

        ArgumentCaptor<EmployeeEntity> captor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepository).save(captor.capture());
        assertEquals(7L, captor.getValue().getId());
        assertEquals("ada", captor.getValue().getFirstName());
        assertSame(dto, result);
    }

    @Test
    void updateEmployeeReturnsNullWhenMissing() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(employeeService.updateEmployee(99L, dto()));
    }

    @Test
    void deleteEmployeeByIdDeletesAndReturnsMappedEmployeeWhenFound() {
        when(employeeRepository.findById(7L)).thenReturn(Optional.of(entity(7L)));

        EmployeeDto result = employeeService.deleteEmployeeById(7L);

        assertEquals(7L, result.getId());
        verify(employeeRepository).deleteById(7L);
    }

    @Test
    void deleteEmployeeByIdDoesNotDeleteWhenMissing() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(employeeService.deleteEmployeeById(99L));
        verify(employeeRepository, never()).deleteById(any());
    }

    private EmployeeEntity entity(Long id) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(id);
        entity.setFirstName("ada");
        entity.setLastName("lovelace");
        entity.setEmailId("ada@example.com");
        return entity;
    }

    private EmployeeDto dto() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("ada");
        dto.setLastName("lovelace");
        dto.setEmailId("ada@example.com");
        return dto;
    }
}