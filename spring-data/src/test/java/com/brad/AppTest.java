package com.brad;

import com.brad.domain.Department;
import com.brad.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class AppTest {
    @Autowired
    private App app;

    @Test
    public void testGetDepartmentById() {
        app.getDepartmentById(2L);
    }

    @Test
    public void testSaveDepartment() {
        Department department = new Department();
        department.setName("研发部");

        Set<Employee> employees = new HashSet<>();
        Employee employee1 = new Employee("brad", 10000, department);
        Employee employee2 = new Employee("lily", 9000, department);
        Employee employee3 = new Employee("lucy", 8000, department);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department.setEmployees(employees);

        app.saveDepartment(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setId(20L);
        department.setName("研发部123");

        Set<Employee> employees = new HashSet<>();
        Employee employee1 = new Employee(49L, "lily123", 10000, department);
        Employee employee2 = new Employee(50L, "lucy123", 10000, department);
        Employee employee3 = new Employee(51L, "brad123", 13000, department);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department.setEmployees(employees);

        app.updateDepartment(department);
    }

    @Test
    public void testRemoveDepartment() {
        app.removeDepartment(19L);
    }
}