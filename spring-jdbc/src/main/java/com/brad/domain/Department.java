package com.brad.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 *
 * @author zhoubd
 * @Date 2018/11/20 10:32
 * @@Description
 */
public class Department {
    private Long id;
    private String name;
    private Set<Employee> employees = new HashSet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}