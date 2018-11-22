package com.brad;

import com.brad.domain.Department;
import com.brad.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 */
@Service("app")
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 基于Druid连接池
     *
     * @param department
     * @return
     */
    @Transactional
    public Long saveDepartment(final Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        // jdbcTemplate需要注入, 不能这样获取, 不然事务不起作用 （可能不在同一个Spring容器内）
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        final JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        Long department_id;

        // 通过jdbcTemplate返回主键
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(SQL_SAVE_DEPARTMENT, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, department.getName());
                return pstmt;
            }
        }, keyHolder);
        department_id = keyHolder.getKey().longValue();

        Set<Employee> employees = department.getEmployees();
        if (null == employees || employees.isEmpty()) {
            return department_id;
        }

        List<Object[]> batchArgs = new ArrayList<>();
        for (Employee employee : employees) {
            batchArgs.add(new Object[]{employee.getName(), employee.getSalary(), department_id});
        }

        jdbcTemplate.batchUpdate(SQL_SAVE_EMPLOYEE, batchArgs);

//        int a = 1 / 0;

        return department_id;
    }

    @Transactional
    public void updateDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        jdbcTemplate.update(SQL_UPDATE_DEPARTMENT, department.getName(), department.getId());

        Set<Employee> employees = department.getEmployees();
        if (null == employees || employees.isEmpty()) {
            return;
        }

        List<Object[]> batchArgs = new ArrayList<>();
        for (Employee employee : employees) {
            batchArgs.add(new Object[]{employee.getName(), employee.getSalary(), employee.getId()});
        }

        jdbcTemplate.batchUpdate(SQL_UPDATE_EMPLOYEE, batchArgs);
//        int i = 1 / 0;
    }

    @Transactional
    public void removeDepartment(Long id) {
        if (null == id || id <= 0) {
            throw new RuntimeException("");
        }

        jdbcTemplate.update(SQL_DELETE_EMPLOYEE, id);

        jdbcTemplate.update(SQL_DELETE_DEPARTMENT, id);

//        int i = 1 / 0;
    }
}
