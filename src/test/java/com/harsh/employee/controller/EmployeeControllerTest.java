package com.harsh.employee.controller;

import com.harsh.employee.api.ApiPaths;
import com.harsh.employee.model.EmployeeDto;
import com.harsh.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private static final String BASE = ApiPaths.API_VERSION + ApiPaths.EMPLOYEES;

    @Test
    void createEmployeeReturnsCreatedWithBody() throws Exception {
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(dto(1L));

        mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"ada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllEmployeesReturnsOkWithList() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of(dto(1L), dto(2L)));

        mockMvc.perform(get(BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAllEmployeesReturnsNoContentWhenEmpty() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        mockMvc.perform(get(BASE))
                .andExpect(status().isNoContent());
    }

    @Test
    void getEmployeeReturnsOkWithBodyWhenFound() throws Exception {
        when(employeeService.getEmployeeById(7L)).thenReturn(dto(7L));

        mockMvc.perform(get(BASE + "/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void getEmployeeReturnsNoContentWhenMissing() throws Exception {
        when(employeeService.getEmployeeById(99L)).thenReturn(null);

        mockMvc.perform(get(BASE + "/99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateEmployeeReturnsOkWithBody() throws Exception {
        when(employeeService.updateEmployee(any(), any(EmployeeDto.class))).thenReturn(dto(7L));

        mockMvc.perform(put(BASE + "/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":7,\"firstName\":\"ada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void deleteEmployeeReturnsOkWithBodyWhenDeleted() throws Exception {
        when(employeeService.deleteEmployeeById(7L)).thenReturn(dto(7L));

        mockMvc.perform(delete(BASE + "/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }

    private EmployeeDto dto(Long id) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(id);
        dto.setFirstName("ada");
        dto.setLastName("lovelace");
        dto.setEmailId("ada@example.com");
        return dto;
    }
}