package com.brad.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Table 标注类对应的表
 * 若表名和类型相同时，省略@Table,比如类Users 和表 users;
 * 若不相同时，必须有@Table，并设置name,为该类对应的表名。@Table(name="users")
 * @Entity 标注实体
 * @Id 标注id
 * @Transient 标注该属性不做与表的映射(原因：可能表中没有该属性对应的字段)
 * 有该注解，在执行sql语句时，就不会出现该属性，否则会有，若表中没有该字段则会报错
 * @Basic 默认所有属性都有该注解(主键需要单独使用@Id)，所以可以省略
 * 该注解可以放在属性上，也可以放在对应的getter方法上。
 * 注意：要么统一将@Basic放在属性上，要么统一放在对应的getter方法上。（一般都放在属性上，可读性比较好）
 * @Column 类中属性名和表中对应字段名不相同时，会使用该注解，指明在类中对应的字段
 * @Column(name="对应的表中字段名")
 */

/**
 * 部门
 *
 * @author zhoubd
 * @Date 2018/11/20 10:32
 * @@Description
 */

@Table(name = "department")
@Entity
public class Department implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    // “Only the parent side of an association makes sense to cascade its entity state transitions to children.
    // ”官方文档中明确说明，只有parent端声明cascade属性有效
    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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