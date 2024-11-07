package com.example.mockito.controller;


import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.WrongNameException;
import com.example.mockito.model.Employee;
import com.example.mockito.service.EmployeeService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    @GetMapping("/add")
    public String add(@RequestParam String firstName,
                      @RequestParam String lastName,
                      @RequestParam double salary,
                      @RequestParam int departmentId) {
        check(firstName, lastName);
        Employee employee = new Employee(firstName, lastName, salary, departmentId);
        service.addEmployee(firstName, lastName, salary, departmentId);
        return "Сотрудник " + employee.toString() + " успешно внесён в коллекцию";
    }
    @GetMapping("/get")
    public String get(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            check(firstName, lastName);
            return "Сотрудник " + service.findEmployee(firstName, lastName) + " в списке значится";
        } catch (WrongNameException | EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }
    @GetMapping("/remove")
    public String remove(@RequestParam String firstName, @RequestParam String lastName) {
        check(firstName, lastName);
        service.removeEmployee(firstName, lastName);
        return "Сотрудник успешно удалён из коллекции";
    }
    @GetMapping("/all")
    public Collection<Employee> getAll() {
        return service.getAll();
    }
    private void check(String... args) {
        for (String arg : args) {
            if (!StringUtils.isBlank(arg)) {
                throw new WrongNameException("В параметрах имени и(или) фамилии сотрудника есть небуквенные символы");
            }
        }
    }
}
