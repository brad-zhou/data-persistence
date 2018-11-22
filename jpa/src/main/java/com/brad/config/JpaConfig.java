package com.brad.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author zhoubd
 * @Date 2018/11/22 16:01
 * @@Description
 */
@EnableTransactionManagement
@PropertySource(value = "classpath:jdbc.properties")
@Import(DruidConfig.class)
@Configuration
public class JpaConfig {
    @Value("${packagesToScan}")
    private String packagesToScan;

    @Value("${showSql}")
    private boolean showSql;

    @Value("${generateDdl}")
    private boolean generateDdl;

    @Value("${databasePlatform}")
    private String databasePlatform;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        // 配置数据源
        entityManagerFactoryBean.setDataSource(dataSource);
        // 配置JPA厂商适配器
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        // 设置扫描Entity的路径
        entityManagerFactoryBean.setPackagesToScan(packagesToScan);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setShowSql(showSql);
        jpaVendorAdapter.setGenerateDdl(generateDdl);
        jpaVendorAdapter.setDatabasePlatform(databasePlatform);

        return jpaVendorAdapter;
    }

    /**
     * 配置事务管理器
     *
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
