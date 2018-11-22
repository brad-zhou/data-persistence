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
        new App().getDepartmentById(2L);
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

        new App().saveDepartment(department);
    }

}
