package com.brad;

import com.brad.domain.Department;
import com.brad.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Hello world!
 */
@Component
@ComponentScan(basePackages = "com.brad")
public class App {
    @Autowired
    private AppRepository repository;

    public Department getDepartmentById(Long id) {
        Optional<Department> optional = repository.findById(id);

        Department department = null;
        if (optional.isPresent()) {
            department = optional.get();
        }
        return department;
    }

    @Transactional
    public Long saveDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        repository.save(department);

//        int i = 1 / 0;

        return department.getId();
    }

    @Transactional
    public void updateDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        repository.save(department);
//        int i = 1 / 0;
    }

    @Transactional
    public void removeDepartment(Long id) {
        if (null == id || id <= 0) {
            throw new RuntimeException("");
        }

        repository.deleteById(id);
//        int i = 1 / 0;
    }
}