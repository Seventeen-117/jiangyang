package com.jiangYang.cloud.module.iot.plugin.http.config;

import com.jiangYang.cloud.module.iot.api.device.IotDeviceUpstreamApi;
import com.jiangYang.cloud.module.iot.plugin.common.downstream.IotDeviceDownstreamHandler;
import com.jiangYang.cloud.module.iot.plugin.http.downstream.IotDeviceDownstreamHandlerImpl;
import com.jiangYang.cloud.module.iot.plugin.http.upstream.IotDeviceUpstreamServer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IoT 插件 HTTP 的专用自动配置类
 *
 * @author haohao
 */
@Configuration
@EnableConfigurationProperties(IotPluginHttpProperties.class)
public class IotPluginHttpAutoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public IotDeviceUpstreamServer deviceUpstreamServer(IotDeviceUpstreamApi deviceUpstreamApi,
                                                        IotPluginHttpProperties properties) {
        return new IotDeviceUpstreamServer(properties, deviceUpstreamApi);
    }

    @Bean
    public IotDeviceDownstreamHandler deviceDownstreamHandler() {
        return new IotDeviceDownstreamHandlerImpl();
    }

}
