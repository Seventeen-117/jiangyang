package com.flink.jiangyang.module.flink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jiangyang.flink")
public class FlinkProperties {

    /**
     * Flink JobManager REST API 地址
     */
    private String jobManagerUrl ;

    /**
     * Flink 任务 Jar 包存储路径
     */
    private String jarPath = "/app/flink/jars";

    /**
     * Flink 任务超时时间（毫秒）
     */
    private long taskTimeout = 60000;

    // Getters and Setters
    public String getJobManagerUrl() {
        return jobManagerUrl;
    }

    public void setJobManagerUrl(String jobManagerUrl) {
        this.jobManagerUrl = jobManagerUrl;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public long getTaskTimeout() {
        return taskTimeout;
    }

    public void setTaskTimeout(long taskTimeout) {
        this.taskTimeout = taskTimeout;
    }
}