package com.jiangYang.cloud.module.iot.job.plugin;

import cn.hutool.core.util.StrUtil;
import com.jiangYang.cloud.framework.tenant.core.job.TenantJob;
import com.jiangYang.cloud.module.iot.service.plugin.IotPluginInstanceService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * IoT 插件实例离线检查 Job
 *
 * @author 芋道源码
 */
@Component
public class IotPluginInstancesJob {

    /**
     * 插件离线超时时间
     *
     * TODO 芋艿：暂定 10 分钟，后续看要不要做配置
     */
    public static final Duration OFFLINE_TIMEOUT = Duration.ofMinutes(10);

    @Resource
    private IotPluginInstanceService pluginInstanceService;

    @XxlJob("iotPluginInstancesJob")
    @TenantJob
    public String execute(String param) {
        int count = pluginInstanceService.offlineTimeoutPluginInstance(
                LocalDateTime.now().minus(OFFLINE_TIMEOUT));
        return StrUtil.format("离线超时插件实例数量为: {}", count);
    }

}