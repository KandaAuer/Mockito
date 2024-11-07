package com.example.mockito.controller;


import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.model.Employee;
import com.example.mockito.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }
    @GetMapping("{deptId}/salary/sum")
    public double sumByDept(@PathVariable int deptId) {
        return service.sum(deptId);
    }
    @GetMapping("{deptId}/salary/max")
    public double max(@PathVariable int deptId) {
        return service.maxSalary(deptId);
    }
    @GetMapping("{deptId}/salary/min")
    public String min(@PathVariable int deptId) {
        try {
            return "" + service.minSalary(deptId);
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }
    @GetMapping("{deptId}/employees")
    public List<Employee> find(@PathVariable int deptId) {
        return service.findAllByDept(deptId);
    }
    @GetMapping("/employees")
    public Map<Integer, List<Employee>> group() {
        return service.groupDeDept();
    }
}
