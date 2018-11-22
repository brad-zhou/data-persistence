package com.brad;

import com.brad.domain.Department;
import com.brad.domain.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testGetDepartmentById() {
        new App().getDepartmentById(1L);
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

//        new App().saveDepartment(department);

        // 因为此时的事务由Spring管理, 所以需要从Spring中获取bean之后调用bean的方法, 不能new
        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
        App app = (App) act.getBean("app");
        app.saveDepartment(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setId(5L);
        department.setName("研发部1");

        Set<Employee> employees = new HashSet<>();
        Employee employee1 = new Employee(13L, "lily1", 10000);
        Employee employee2 = new Employee(14L, "lucy1", 10000);
        Employee employee3 = new Employee(15L, "brad1", 13000);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department.setEmployees(employees);
//        new App().updateDepartment(department);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        App app = (App) ctx.getBean("app");
        app.updateDepartment(department);
    }

    @Test
    public void testRemoveDepartment() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        App app = (App) ctx.getBean("app");
        app.removeDepartment(7L);
//        new App().removeDepartment(5L);
    }

}
