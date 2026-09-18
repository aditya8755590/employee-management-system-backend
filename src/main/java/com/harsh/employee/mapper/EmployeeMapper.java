package com.harsh.employee.mapper;

import com.harsh.employee.entity.EmployeeEntity;
import com.harsh.employee.model.EmployeeDto;
import org.springframework.beans.BeanUtils;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static EmployeeDto fromEntity(EmployeeEntity entity) {
        return new EmployeeDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmailId()
        );
    }

    public static EmployeeEntity toEntity(EmployeeDto employeeDto) {
        EmployeeEntity entity = new EmployeeEntity();

        BeanUtils.copyProperties(employeeDto, entity);
        return entity;
    }

    public static EmployeeEntity copyInto(EmployeeEntity entity, EmployeeDto employeeDto) {
        BeanUtils.copyProperties(employeeDto, entity);
        return entity;
    }
}