package com.jiangYang.cloud.module.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 二维码配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "system.qr-code.base-url")
public class QrCodeProperties {
    /**
     * 二维码基础URL
     */
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
