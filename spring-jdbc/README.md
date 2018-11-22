# Spring JDBC操作步骤
&emsp;&emsp;Spring的JDBC框架能够承担资源管理和异常处理的工作,从而简化我们的JDBC代码, 让我们只需编写从数据库读写数据所必需的代码。
<br>

&emsp;&emsp;Spring把数据访问的样板代码隐藏到模板类之下,
结合Spring的事务管理,可以大大简化我们的代码.
### 1、定义数据源
``` java
// 基于JDBC驱动的数据源
DriverManagerDataSource dataSource = new DriverManagerDataSource();
dataSource.setDriverClassName(MYSQL_JDBC_DRIVER);
dataSource.setUrl(URL);
dataSource.setUsername(USERNAME);
dataSource.setPassword(PASSWORD);
```

或者配置数据库连接池

``` java
<!--配置数据源: 使用Alibaba连接池-->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
    <property name="driverClassName" value="${driverClassName}"/>
    <property name="url" value="${url}"/>
    <property name="username" value="${user}"/>
    <property name="password" value="${password}"/>
    <!-- 连接池启动时的初始值 -->
    <property name="initialSize" value="${initialSize}"/>
    <!-- 连接池的最大值 -->
    <property name="maxActive" value="${maxActive}"/>
    <!-- 最大空闲值.当经过一个高峰时间后，连接池可以慢慢将已经用不到的连接慢慢释放一部分，一直减少到maxIdle为止 -->
    <property name="maxIdle" value="${maxIdle}"/>
    <!--  最小空闲值.当空闲的连接数少于阀值时，连接池就会预申请去一些连接，以免洪峰来时来不及申请 -->
    <property name="minIdle" value="${minIdle}"/>
</bean>
```
jdbc.properties
``` java
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://169.254.34.161:3306/mydb?useUnicode=true&characterEncoding=UTF-8
user=root
password=123456
initialSize=5
maxActive=20
maxIdle=2
minIdle=1

```

### 2、定义JdbcTemplate
``` java
// 创建JDBC模板
JdbcTemplate jdbcTemplate = new JdbcTemplate();
jdbcTemplate.setDataSource(dataSource);
```

或者在Spring中配置

``` java
<!-- 使用Spring提供的JdbcTemplate操作数据库 -->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <!-- 使用构造器注入数据源 -->
    <constructor-arg name="dataSource" ref="dataSource"></constructor-arg>
</bean>
```

### 3、 开启事务
``` java
<!-- 添加事务管理器组件DataSourceTransactionManager -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <!-- 使用set方法注入数据源 -->
    <property name="dataSource" ref="dataSource"></property>
</bean>
<!-- 开启基于注解声明式事务
         注意配置transaction-manager属性，它引用了我们事务管理组件对象，这里要和事务管理器组件id一致
         默认是transactionManager -->
<tx:annotation-driven transaction-manager="transactionManager"/>
```

### 4、@Transactional注解
``` java
@Transactional
public Long saveDepartment(Department department) {}
```

### 5、执行查询/更新/删除/插入
``` java
// 查询
jdbcTemplate.queryForObject()  jdbcTemplate.query()
OR
// 更新/删除/插入
jdbcTemplate.update()
```

无需开启事务、提交事务、回滚事务、关闭事务和关闭资源，Spring利用模板方法模式自动处理。
