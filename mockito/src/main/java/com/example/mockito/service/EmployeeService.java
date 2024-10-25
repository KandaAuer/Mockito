package com.example.mockito.service;

import com.example.mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeService() {
        employees.add(new Employee("Канда", "Ауэр", 50000, 1));
        employees.add(new Employee("Юлия", "Тарасюк", 55000, 1));
        employees.add(new Employee("Илья", "Яровой", 60000, 2));
        employees.add(new Employee("Наталья", "Громова", 45000, 2));
        employees.add(new Employee("Алексей", "Попов", 70000, 3));
        }

    public List<Employee> getEmployeesByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .collect(Collectors.toList());
    }

    public int getSumOfSalariesByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Optional<Employee> getMaxSalaryEmployeeByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .max(Comparator.comparingInt(Employee::getSalary));
    }

    public Optional<Employee> getMinSalaryEmployeeByDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparingInt(Employee::getSalary));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Map<Integer, List<Employee>> getAllEmployeesGroupedByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartmentId));
    }
}
