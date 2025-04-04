package com.jiangYang.cloud.framework.apilog.config;

import com.jiangYang.cloud.module.infra.api.logger.ApiAccessLogApi;
import com.jiangYang.cloud.module.infra.api.logger.ApiErrorLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * API 日志使用到 Feign 的配置项
 *
 * @author 芋道源码
 */
@AutoConfiguration
@EnableFeignClients(clients = {ApiAccessLogApi.class, ApiErrorLogApi.class}) // 主要是引入相关的 API 服务
public class JiangYangApiLogRpcAutoConfiguration {
}
