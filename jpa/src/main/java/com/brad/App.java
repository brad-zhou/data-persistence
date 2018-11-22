package com.brad;

import com.brad.domain.Department;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Hello world!
 */
@EnableTransactionManagement
@ComponentScan
@Component
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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        entityManager.merge(department);
//        int i = 1 / 0;
    }

    @Transactional
    public void removeDepartment(Long id) {
        if (null == id || id <= 0) {
            throw new RuntimeException("");
        }

        Department department = entityManager.find(Department.class, id);
        entityManager.remove(department);

//        int i = 1 / 0;
    }
}
