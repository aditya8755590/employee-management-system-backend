package com.harsh.employee.mapper;

import com.harsh.employee.entity.EmployeeEntity;
import com.harsh.employee.model.EmployeeDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class EmployeeMapperTest {

    @Test
    void mapsEntityToDtoWithAllFields() {
        EmployeeDto dto = EmployeeMapper.fromEntity(entity(7L));

        assertEquals(7L, dto.getId());
        assertEquals("ada", dto.getFirstName());
        assertEquals("lovelace", dto.getLastName());
        assertEquals("ada@example.com", dto.getEmailId());
    }

    @Test
    void mapsDtoToEntityWithAllFields() {
        EmployeeEntity entity = EmployeeMapper.toEntity(dto());

        assertNotSame(dto(), entity);
        assertEquals("ada", entity.getFirstName());
        assertEquals("lovelace", entity.getLastName());
        assertEquals("ada@example.com", entity.getEmailId());
    }

    @Test
    void copiesFieldsOntoProvidedEntityWithoutReplacingIt() {
        EmployeeDto dto = dto();
        dto.setId(9L);

        EmployeeEntity result = EmployeeMapper.copyInto(entity(1L), dto);

        assertEquals(entity(9L), result);
        assertEquals(9L, result.getId());
        assertEquals("ada", result.getFirstName());
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