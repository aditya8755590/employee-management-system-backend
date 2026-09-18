package com.harsh.employee.controller;

import com.harsh.employee.model.Employee;
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
    public ResponseEntity<Employee> createEmployee(
            @RequestBody
            Employee employee
    ) {
        Employee savedEmployee = employeeService.createEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();

        if(!allEmployees.isEmpty()) {
            return ResponseEntity.ok(allEmployees);
        }
        
        return new ResponseEntity<>(allEmployees, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(
            @PathVariable
            long id
    ) {
        Employee employee = employeeService.getEmployeeById(id);

        if(employee == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> deleteEmployee(
            @PathVariable
            long id
    ) {
        Employee employee = employeeService.deleteEmployeeById(id);

        if(employee == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable
            Long id,
            @RequestBody
            Employee employee
    ) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(employee);
    }

}
