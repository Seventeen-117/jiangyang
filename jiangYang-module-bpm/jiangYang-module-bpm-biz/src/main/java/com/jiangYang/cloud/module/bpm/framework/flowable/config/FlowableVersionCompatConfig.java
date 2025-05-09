package com.jiangYang.cloud.module.bpm.framework.flowable.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable 版本兼容配置
 * 使用官方提供的EngineConfigurationConfigurer来修改Flowable配置，
 * 解决数据库schema版本为7.0.1.1与当前Flowable 6.x版本不匹配的问题
 */
@Configuration
@Slf4j
public class FlowableVersionCompatConfig {

    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> flowableVersionCompatibilityConfigurer() {
        return engineConfiguration -> {
            // 禁用数据库schema更新
            engineConfiguration.setDatabaseSchemaUpdate("false");
            
            // 跳过表锁定，减少版本兼容性问题
            try {
                // 使用反射动态调用方法，以处理不同版本的Flowable API
                java.lang.reflect.Method compatMethod = engineConfiguration.getClass()
                        .getMethod("setSchemaTableLockingEnabled", boolean.class);
                compatMethod.invoke(engineConfiguration, false);
                log.info("成功设置schemaTableLockingEnabled为false，减少版本兼容性问题");
            } catch (Exception e) {
                log.warn("无法设置schemaTableLockingEnabled: {}", e.getMessage());
            }
            
            // 关闭自动部署，避免初始化问题
            engineConfiguration.setDeploymentName("manual-deployment");
            engineConfiguration.setDatabaseType("mysql"); // 明确设置数据库类型
            
            log.info("已配置Flowable版本兼容设置，禁用schema更新");
        };
    }
} 