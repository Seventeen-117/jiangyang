package com.jiangYang.cloud.framework.tracer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracer配置类
 *
 * @author 麻薯
 */
@ConfigurationProperties("jiangyang.tracer")
@Data
public class TracerProperties {
}
