package com.example.mockito.service;


import com.example.mockito.exception.EmployeeAlreadyAddException;
import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.EmployeeStorageIsFullException;
import com.example.mockito.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();
    @Test
    public void testAdd() {
        employeeService.addEmployee("test", "test2", 55_000d, 1);
        var allEmployees = employeeService.getAll();
        Assertions.assertEquals(1, allEmployees.size());
        var employee = allEmployees.iterator().next();
        Assertions.assertEquals("Test", employee.getFirstName());
        Assertions.assertEquals("Test2", employee.getLastName());
        Assertions.assertEquals(55_000d, employee.getSalary());
        Assertions.assertEquals(1, employee.getDepartment());
        org.assertj.core.api.Assertions.assertThat("Test2").isEqualTo(employee.getLastName());
    }
    @Test
    public void testAddWhenStorageIsFull() {
        for (int i = 0; i < 10; i++) {
            employeeService.addEmployee("test_", "test_test_" + i, 0d, 0);
        }
        Assertions.assertThrows(EmployeeStorageIsFullException.class, () -> employeeService.addEmployee("test", "test", 0d, 0));
    }
    @Test
    public void testAddWhenAlreadyExists() {
        employeeService.addEmployee("test", "test", 0d, 0);
        Assertions.assertThrows(EmployeeAlreadyAddException.class, () -> employeeService.addEmployee("test", "test", 0d, 0));
    }
    @Test
    public void testFinde() {
        employeeService.addEmployee("test", "test2", 50_000d, 1);
        var actual = employeeService.findEmployee("test", "test2");
        Assertions.assertEquals("Test", actual.getFirstName());
        Assertions.assertEquals("Test2", actual.getLastName());
        Assertions.assertEquals(55_000d, actual.getSalary());
        Assertions.assertEquals(1, actual.getDepartment());
    }
    @Test
    public void testFindWhenNotExist() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("test", "test2"));
    }
    @Test
    public void testRemove() {
        //test:
        employeeService.addEmployee("test", "test2", 55_000d, 1);
        Assertions.assertEquals(1, employeeService.getAll().size());
        Assertions.assertTrue(employeeService.removeEmployee("test", "test2"));
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.removeEmployee("not_add", "not_add"));
    }
    @Test
    public void testGetAll() {
        employeeService.addEmployee("test1", "test_test1", 55_000d, 1);
        employeeService.addEmployee("test2", "test_test2", -55_000d, 1);
        employeeService.addEmployee("test3", "test_test3", 55_000d, -1);
        var all = employeeService.getAll();
        org.assertj.core.api.Assertions.assertThat(all.size()).isEqualTo(3);
        org.assertj.core.api.Assertions.assertThat(all)
                .containsExactlyInAnyOrder(
                        new Employee("Test1", "Test_test1", 55_000d, 1),
                        new Employee("Test2", "Test_test2", -55_000d, 1),
                        new Employee("Test3", "Test_test3", 55_000d, -1));
    }
}
