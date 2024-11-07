package com.example.mockito.service;


import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    DepartmentService departmentService;
    @BeforeEach
    void setUp() {
        var employees = List.of(
                new Employee("test", "test20", 55_000d, 1),
                new Employee("test1", "test21", 60_000d, 2),
                new Employee("test2", "test22", 70_000d, 1),
                new Employee("test3", "test23", 80_000d, 3),
                new Employee("test4", "test24", 90_000d, 4));
        when(employeeService.getAll()).thenReturn(employees);
    }
    @Test
    void testSum() {
        Assertions.assertThat(departmentService.sum(1)).isEqualTo(120_000d);
        verify(employeeService, times(1)).getAll();
    }
    @Test
    void testMaxSalary() {
        Assertions.assertThat(departmentService.maxSalary(1)).isEqualTo(70_000d);
        verify(employeeService, times(1)).getAll();
    }
    @Test
    void testMinSalary() {
        Assertions.assertThat(departmentService.minSalary(1)).isEqualTo(55_000d);
        verify(employeeService, times(1)).getAll();
    }
    @Test
    void testAllByDept() {
        var employees = departmentService.findAllByDept(4);
        Assertions.assertThat(employees.size()).isEqualTo(1);
        Assertions.assertThat(employees.get(0)).isEqualTo(new Employee("test4", "test24", 90_000d, 4));
        verify(employeeService, times(1)).getAll();
    }
    @Test
    void testWhenEmployeesIsEmpty() {
        when(employeeService.getAll()).thenReturn(emptyList());
        Assertions.assertThatThrownBy(() -> departmentService.minSalary(1))
                .isInstanceOf(EmployeeNotFoundException.class);
        Assertions.assertThatThrownBy(() -> departmentService.maxSalary(1))
                .isInstanceOf(EmployeeNotFoundException.class);
        assertTrue(departmentService.findAllByDept(1).isEmpty());
        verify(employeeService, times(3)).getAll();
    }
    @Test
    void testGroupByDept() {
        Map<Integer, List<Employee>> actual = departmentService.groupDeDept();

        Assertions.assertThat(actual.keySet()).containsExactly(1, 2, 3, 4);
        Assertions.assertThat(actual.get(1)).containsExactly(
                new Employee("test", "test20", 55_000d, 1),
                new Employee("test2", "test22", 70_000d, 1));
        verify(employeeService, times(1)).getAll();
    }
}
