package pro.sky.demo.service;

import org.springframework.stereotype.Service;
import pro.sky.demo.exception.EmployeeAlreadyAddedException;
import pro.sky.demo.exception.EmployeeNotFoundException;
import pro.sky.demo.exception.EmployeeStorageIsFullException;
import pro.sky.demo.model.Employee;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 10;
    private List<Employee> employees;

    public EmployeeService() {
        employees = new ArrayList<>();
        fillEmployeesList(); // Заполняем список сотрудников при создании сервиса
    }

    public void addEmployee(String firstName, String lastName) {
        Employee newEmployee = new Employee(firstName, lastName);
        if (employees.contains(newEmployee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник уже добавлен: " + newEmployee);
        }

        if (employees.size() >= MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Достигнут лимит количества сотрудников");
        }

        employees.add(newEmployee);
        System.out.println("Сотрудник добавлен: " + newEmployee);
    }

    public void removeEmployee(String firstName, String lastName) {
        Employee employeeToRemove = findEmployee(firstName, lastName);
        employees.remove(employeeToRemove);
        System.out.println("Сотрудник удален: " + employeeToRemove);
    }

    public Employee findEmployee(String firstName, String lastName) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName)) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Сотрудник не найден: " + firstName + " " + lastName);
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    private void fillEmployeesList() {
        try {
            addEmployee("Иван", "Иваныч");
            addEmployee("Никита", "Молька");
            addEmployee("Максим", "Викторович");
        } catch (EmployeeStorageIsFullException | EmployeeAlreadyAddedException e) {
        }
    }
}