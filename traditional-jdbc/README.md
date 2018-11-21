# JDBC操作步骤
### 1、加载驱动
``` java
Class.forName("com.mysql.jdbc.Driver);
```

### 2、获取连接
``` java
Connection conn = DriverManager.getConnection(url, username, password);
```

### 3、 开启事务
``` java
conn.setAutoCommit(false);
```

### 4、创建Statement并设置参数
``` java
PreparedStatement statement = conn.prepareStatement(sql);
statement.setString(1, "aaa");
```

### 5、执行查询/更新/删除/插入
``` java
// 查询
ResultSet resultSet = statement.executeQuery();
OR
// 更新/删除/插入
statement.executeUpdate();
```

###  6、获取数据(ResultSet) OR 提交事务 OR 回滚事务
``` java
// 获取数据
while(resultSet.next()){
  resultSet.getLong("id");
  resultSet.getString("name");
}

// 提交事务
conn.commit();
// 回滚事务
conn.rollback();
```

###  7、关闭资源
``` java
try{
  resultSet.close();
  statement.close();
  conn.close();
} catch (SQLException e){
  // do something.
}
```
