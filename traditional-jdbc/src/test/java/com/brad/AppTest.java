package com.brad;

import com.brad.domain.Department;
import com.brad.domain.Employee;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testGetDepartmentById() {
        new App().getDepartmentById(5L);
    }

    @Test
    public void testSaveDepartment() {
        Department department = new Department();
        department.setName("研发部");

        Set<Employee> employees = new HashSet<>();
        Employee employee1 = new Employee("brad", 10000);
        Employee employee2 = new Employee("lily", 9000);
        Employee employee3 = new Employee("lucy", 8000);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department.setEmployees(employees);

        new App().saveDepartment(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setId(5L);
        department.setName("研发部123");

        Set<Employee> employees = new HashSet<>();
        Employee employee1 = new Employee(1L,"brad123", 10000);
        employees.add(employee1);
        department.setEmployees(employees);
        new App().updateDepartment(department);
    }

    @Test
    public void testRemoveDepartment() {
        new App().removeDepartment(5L);
    }
}
