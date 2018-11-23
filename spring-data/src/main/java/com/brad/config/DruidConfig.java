package com.brad.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author zhoubd
 * @Date 2018/11/22 15:49
 * @@Description
 */
@PropertySource(value = "classpath:jdbc.properties")
@Configuration
public class DruidConfig {
    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${initialSize}")
    private Integer initialSize;

    @Value("${maxActive}")
    private Integer maxActive;

    @Value("${maxWait}")
    private Integer maxWait;

    @Value("${minIdle}")
    private Integer minIdle;

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);

        return dataSource;
    }
}
