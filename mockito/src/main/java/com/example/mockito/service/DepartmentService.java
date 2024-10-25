package com.example.mockito.service;

import com.example.mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public List<Employee> getEmployeesByDepartment(int departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    public int getSumOfSalariesByDepartment(int departmentId) {
        return employeeService.getSumOfSalariesByDepartment(departmentId);
    }

    public Employee getEmployeeWithMaxSalary(int departmentId) {
        return employeeService.getMaxSalaryEmployeeByDepartment(departmentId).orElse(null);
    }

    public Employee getEmployeeWithMinSalary(int departmentId) {
        return employeeService.getMinSalaryEmployeeByDepartment(departmentId).orElse(null);
    }

    public Map<Integer, List<Employee>> getAllEmployeesGroupedByDepartment() {
        return employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartmentId)); // Изменено на getDepartmentId
    }
}
