package pro.sky.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.demo.exception.EmployeeAlreadyAddedException;
import pro.sky.demo.exception.EmployeeNotFoundException;
import pro.sky.demo.exception.EmployeeStorageIsFullException;
import pro.sky.demo.model.Employee;
import pro.sky.demo.service.EmployeeService;


import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            employeeService.addEmployee(firstName, lastName);
            Employee employee = new Employee(firstName, lastName);
            return ResponseEntity.ok(employee);
        } catch (EmployeeStorageIsFullException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (EmployeeAlreadyAddedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Employee> removeEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            Employee employee = employeeService.findEmployee(firstName, lastName);
            employeeService.removeEmployee(firstName, lastName);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<Employee> findEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            Employee employee = employeeService.findEmployee(firstName, lastName);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/list")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}