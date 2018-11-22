package com.brad;

import com.brad.domain.Department;

import javax.persistence.*;

/**
 * Hello world!
 */
public class App {
    public Department getDepartmentById(Long id) {
        // 1.获得Factory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");

        // 2.获取Manager
        EntityManager manager = factory.createEntityManager();

        // 3.执行sql
        Department department = manager.find(Department.class, id);

        // 4.关闭资源
        manager.close();
        factory.close();

        return department;
    }

    public Long saveDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        // 1.获取EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");

        // 2.获取EntityManager
        EntityManager manager = factory.createEntityManager();

        // 3.开启事务
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // 4.执行sql
        manager.persist(department);

        // 5.提交事务
        transaction.commit();

        // 6.关闭资源
        manager.close();
        factory.close();

        return department.getId();
    }
}
