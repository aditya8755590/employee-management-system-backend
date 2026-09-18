package com.harsh.employee.controller;

import com.harsh.employee.model.Employee;
import com.harsh.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "https://employee-management-system-frontend-hazel.vercel.app/"})
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(
            @RequestBody
            Employee employee
    ) {
        Employee savedEmployee = employeeService.createEmployee(employee);

        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> allEmployee = employeeService.getAllEmployee();

        if(!allEmployee.isEmpty()) {
            return new ResponseEntity<>(allEmployee, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(allEmployee, HttpStatus.NO_CONTENT);
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployee(
            @PathVariable
            long id
    ) {
        Employee employee = employeeService.getEmployeeById(id);

        if(employee == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("employees/{id}")
    public ResponseEntity<Employee> deleteEmployee(
            @PathVariable
            long id
    ) {
        Employee employee = employeeService.deleteEmployeeById(id);

        if(employee == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable
            Long id,
            @RequestBody
            Employee employee
    ) {
        employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
