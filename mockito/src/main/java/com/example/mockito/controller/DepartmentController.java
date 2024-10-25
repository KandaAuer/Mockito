package com.example.mockito.controller;

import com.example.mockito.model.Employee;
import com.example.mockito.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final EmployeeService employeeService;

    public DepartmentController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByDepartment(@PathVariable int id) {
        return employeeService.getEmployeesByDepartment(id);
    }

    @GetMapping("/{id}/salary/sum")
    public int getSumOfSalariesByDepartment(@PathVariable int id) {
        return employeeService.getSumOfSalariesByDepartment(id);
    }

    @GetMapping("/{id}/salary/max")
    public Optional<Employee> getMaxSalaryEmployeeByDepartment(@PathVariable int id) {
        return employeeService.getMaxSalaryEmployeeByDepartment(id);
    }

    @GetMapping("/{id}/salary/min")
    public Optional<Employee> getMinSalaryEmployeeByDepartment(@PathVariable int id) {
        return employeeService.getMinSalaryEmployeeByDepartment(id);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> getAllEmployeesGroupedByDepartment() {
        return employeeService.getAllEmployeesGroupedByDepartment();
    }
}
