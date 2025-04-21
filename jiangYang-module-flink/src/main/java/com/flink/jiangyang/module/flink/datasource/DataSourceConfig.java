package com.flink.jiangyang.module.flink.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSourceProperties masterDataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setGenerateUniqueName(false); // 防止自动生成名称
        return properties;
    }

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        // 获取URL并进行空值检查
        String url = masterDataSourceProperties().getUrl();
        if (url == null) {
            throw new IllegalStateException("数据源URL未配置");
        }

        // 显式验证驱动可用性（先检查URL非空）
        try {
            DriverManager.getDriver(url);
        } catch (SQLException e) {
            throw new IllegalStateException("无法加载JDBC驱动: " + masterDataSourceProperties().getDriverClassName(), e);
        }

        return masterDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(com.alibaba.druid.pool.DruidDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }
}
