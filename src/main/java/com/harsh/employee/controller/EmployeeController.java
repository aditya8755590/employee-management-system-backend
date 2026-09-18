package com.harsh.employee.controller;

import com.harsh.employee.model.EmployeeDto;
import com.harsh.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "https://employee-management-system-frontend-hazel.vercel.app/"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employee) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> allEmployees = employeeService.getAllEmployees();

        if(!allEmployees.isEmpty()) {
            return ResponseEntity.ok(allEmployees);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);

        if(employee == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable long id) {
        EmployeeDto employee = employeeService.deleteEmployeeById(id);

        if(employee == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employee);
    }
}
