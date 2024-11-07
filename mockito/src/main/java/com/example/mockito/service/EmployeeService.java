package com.example.mockito.service;

import org.springframework.stereotype.Service;
import com.example.mockito.exception.EmployeeAlreadyAddException;
import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.EmployeeStorageIsFullException;
import com.example.mockito.model.Employee;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tomcat.util.IntrospectionUtils.capitalize;

@Service
public class EmployeeService {
    private static int SIZE = 10;
    private final Map<String, Employee> employees = new HashMap<>();
    public void addEmployee(String firstName, String lastName, double salary, int deportmentId) {
        if (employees.size() == SIZE) {
            throw new EmployeeStorageIsFullException();
        }
        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddException();
        }
        employees.put(key, new Employee(capitalize(firstName),
                capitalize(lastName),
                salary,
                deportmentId));
    }
    public Employee findEmployee(String firstName, String lastName) {
        var emp = employees.get(makeKey(firstName, lastName));
        if (emp == null) {
            throw new EmployeeNotFoundException("Сотрудник отсутствует!");
        }
        return emp;
    }
    public Boolean removeEmployee(String firstName, String lastName) {
        Employee removed = employees.remove(makeKey(firstName, lastName));
        if (removed == null) {
            throw new EmployeeNotFoundException("Сотрудник отсутствует!");
        }
        return true;
    }
    public Collection<Employee> getAll() {
        return employees.values();
    }
    private String makeKey(String firstName, String lastName) {
        return (firstName + " " + lastName).toLowerCase();
    }
}
