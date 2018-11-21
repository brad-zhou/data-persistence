package com.brad;

import com.brad.domain.Department;
import com.brad.domain.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://169.254.34.161:3306/mydb?useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    private static final String SQL_QUERY_DEPARTMENT = "SELECT * FROM department WHERE id = ?";
    private static final String SQL_QUERY_EMPLOYEE = "SELECT * FROM employee WHERE department_id = ?";
    private static final String SQL_SAVE_DEPARTMENT = "INSERT INTO department(name) VALUES(?)";
    private static final String SQL_SAVE_EMPLOYEE = "INSERT INTO employee(name, salary, department_id) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE_DEPARTMENT = "UPDATE department SET name = ? WHERE id = ?";
    private static final String SQL_UPDATE_EMPLOYEE = "UPDATE employee SET name = ?, salary = ? WHERE id = ?";
    private static final String SQL_DELETE_DEPARTMENT = "DELETE FROM department WHERE id = ?";
    private static final String SQL_DELETE_EMPLOYEE = "DELETE FROM employee WHERE department_id = ?";

    public Department getDepartmentById(Long id) {
        // 基于JDBC驱动的数据源
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(MYSQL_JDBC_DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        final Department department = jdbcTemplate.queryForObject(SQL_QUERY_DEPARTMENT, new RowMapper<Department>() {
            @Override
            public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
                Department department = new Department();
                department.setId(rs.getLong("id"));
                department.setName(rs.getString("name"));
                return department;
            }
        }, id);

        List<Employee> employees = jdbcTemplate.query(SQL_QUERY_EMPLOYEE, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setName(rs.getString("name"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setDepartment(department);
                return employee;
            }
        }, id);

        department.setEmployees(new HashSet<Employee>(employees));

        return department;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
