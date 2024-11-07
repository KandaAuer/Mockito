package com.example.mockito.service;


import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DepartmentService {
    private final EmployeeService employeeService;
    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public double maxSalary(int deptId) {
        return employeeService.getAll()  //List of employees from Mapa
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)
                .max(Comparator.comparingDouble(o->o))
                .orElseThrow(() -> new EmployeeNotFoundException("Нет значений"));
    }
    public double minSalary(int deptId) {
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)
                .min(Comparator.comparingDouble(o->o))
                .orElseThrow(() -> new EmployeeNotFoundException("Нет сотрудников в департаменте!"));
    }
    public List<Employee> findAllByDept(int deptId) {
        return (List<Employee>) employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .collect(Collectors.toList());
    }
    public Map<Integer, List<Employee>> groupDeDept() {
        Map<Integer, java.util.List<Employee>> map = employeeService.getAll()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        return map;
    }
    public double sum(int deptId) {
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .mapToDouble(Employee::getSalary)
                .sum();
    }
}
