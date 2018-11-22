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

    @Autowired
    private App app;

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setId(16L);
        department.setName("研发部123");

        Set<Employee> employees = new HashSet<>();
        Employee employee1 = new Employee(37L, "lily123", 10000);
        Employee employee2 = new Employee(38L, "lucy123", 10000);
        Employee employee3 = new Employee(39L, "brad123", 13000);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department.setEmployees(employees);
//        new App().updateDepartment(department);

//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        App app = (App) ctx.getBean("app");
        app.updateDepartment(department);
    }

    @Test
    public void testRemoveDepartment() {
        app.removeDepartment(14L);
    }
}
