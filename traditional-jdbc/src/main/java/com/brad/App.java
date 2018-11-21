package com.brad;

import com.brad.domain.Department;
import com.brad.domain.Employee;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 步骤：
 * 1、加载驱动
 * 2、获取连接
 * 3、创建Statement
 * 4、执行查询
 * 5、获取数据(ResultSet)
 * 6、关闭资源
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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Department department = new Department();
        try {
            //加载MYSQL JDBC驱动程序
            Class.forName(MYSQL_JDBC_DRIVER);
            //获取连接
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //创建Statement
            statement = connection.prepareStatement(SQL_QUERY_DEPARTMENT);
            statement.setLong(1, id);
            //执行查询
            resultSet = statement.executeQuery();
            //获取数据
            if (resultSet.next()) {
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));
            }

            //创建Statement
            statement = connection.prepareStatement(SQL_QUERY_EMPLOYEE);
            statement.setLong(1, id);
            //执行查询
            resultSet = statement.executeQuery();
            //获取数据
            Set<Employee> employees = new HashSet<>();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDepartment(department);

                employees.add(employee);
            }
            department.setEmployees(employees);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return department;
    }

    public Long saveDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Long department_id = null;

        try {
            // 加载MYSQL JDBC驱动
            Class.forName(MYSQL_JDBC_DRIVER);
            // 获取连接
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 开启事务
            connection.setAutoCommit(false);

            // 创建statement
            statement = connection.prepareStatement(SQL_SAVE_DEPARTMENT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());
            // 执行操作
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (null != resultSet && resultSet.next()) {
                department_id = resultSet.getLong(1);
            }

            Set<Employee> employees = department.getEmployees();
            if (null == employees || employees.isEmpty()) {
                return department_id;
            }

            for (Employee employee : employees) {
                // 创建statement
                statement = connection.prepareStatement(SQL_SAVE_EMPLOYEE);
                statement.setString(1, employee.getName());
                statement.setDouble(2, employee.getSalary());
                statement.setLong(3, department_id);

                // 执行操作
                statement.executeUpdate();
            }

            // 提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();

            // 回滚事务
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            // 关闭资源
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return department_id;
    }

    public void updateDepartment(Department department) {
        if (null == department) {
            throw new RuntimeException("");
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 加载MYSQL JDBC驱动
            Class.forName(MYSQL_JDBC_DRIVER);
            // 获取连接
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 开启事务
            connection.setAutoCommit(false);

            // 创建statement
            statement = connection.prepareStatement(SQL_UPDATE_DEPARTMENT);
            statement.setString(1, department.getName());
            statement.setLong(2, department.getId());
            // 执行操作
            statement.executeUpdate();

            Set<Employee> employees = department.getEmployees();
            if (null == employees || employees.isEmpty()) {
                return;
            }

            for (Employee employee : employees) {
                // 创建statement
                statement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE);
                statement.setString(1, employee.getName());
                statement.setDouble(2, employee.getSalary());
                statement.setLong(3, employee.getId());
                // 执行操作
                statement.executeUpdate();
            }

            // 提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            // 关闭资源
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeDepartment(Long id) {
        if (null == id || id <= 0) {
            throw new RuntimeException("");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 加载驱动
            Class.forName(MYSQL_JDBC_DRIVER);
            // 获取连接
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 开启事务
            connection.setAutoCommit(false);

            // 创建statement
            statement = connection.prepareStatement(SQL_DELETE_EMPLOYEE);

            statement.setLong(1, id);
            // 执行操作
            statement.executeUpdate();

            // 创建statement
            statement = connection.prepareStatement(SQL_DELETE_DEPARTMENT);
            statement.setLong(1, id);
            // 执行操作
            statement.executeUpdate();

            // 提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // 回滚事务
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            // 关闭资源
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}