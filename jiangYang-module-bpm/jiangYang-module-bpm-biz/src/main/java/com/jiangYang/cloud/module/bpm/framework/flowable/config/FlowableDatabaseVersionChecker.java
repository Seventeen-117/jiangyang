package com.jiangYang.cloud.module.bpm.framework.flowable.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Flowable 数据库版本检查器
 * 诊断工具，用于启动时检查数据库版本信息，确认实际的Flowable数据库版本
 */
@Component
@Slf4j
public class FlowableDatabaseVersionChecker implements ApplicationRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        try {
            // 查询ACT_GE_PROPERTY表，获取数据库的schema版本信息
            String sql = "SELECT * FROM ACT_GE_PROPERTY WHERE NAME_ = 'schema.version'";
            jdbcTemplate.query(sql, rs -> {
                String schemaVersion = rs.getString("VALUE_");
                log.info("检测到Flowable数据库版本: {}", schemaVersion);
                return null;
            });
        } catch (Exception e) {
            log.warn("检查Flowable数据库版本时发生错误: {}", e.getMessage());
        }
        
        // 提示解决方案
        log.info("如果仍然出现版本不兼容问题，可以考虑以下解决方案：");
        log.info("1. 通过SQL脚本更新数据库版本号: UPDATE ACT_GE_PROPERTY SET VALUE_='6.8.0' WHERE NAME_='schema.version'");
        log.info("2. 使用与数据库版本号对应的Flowable版本，当前使用的是Flowable 6.8.0");
        log.info("3. 创建新数据库，让Flowable自动创建表结构");
    }
} 