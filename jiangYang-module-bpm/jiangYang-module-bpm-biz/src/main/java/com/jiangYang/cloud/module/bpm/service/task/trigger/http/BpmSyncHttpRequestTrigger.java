package com.jiangYang.cloud.module.bpm.service.task.trigger.http;

import com.jiangYang.cloud.framework.common.util.json.JsonUtils;
import com.jiangYang.cloud.module.bpm.controller.admin.definition.vo.model.simple.BpmSimpleModelNodeVO.TriggerSetting.HttpRequestTriggerSetting;
import com.jiangYang.cloud.module.bpm.enums.definition.BpmTriggerTypeEnum;
import com.jiangYang.cloud.module.bpm.framework.flowable.core.util.BpmHttpRequestUtils;
import com.jiangYang.cloud.module.bpm.service.task.BpmProcessInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * BPM 发送同步 HTTP 请求触发器
 *
 * @author jason
 */
@Component
@Slf4j
public class BpmSyncHttpRequestTrigger extends BpmAbstractHttpRequestTrigger {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTriggerTypeEnum getType() {
        return BpmTriggerTypeEnum.HTTP_REQUEST;
    }

    @Override
    public void execute(String processInstanceId, String param) {
        // 1. 解析 http 请求配置
        HttpRequestTriggerSetting setting = JsonUtils.parseObject(param, HttpRequestTriggerSetting.class);
        if (setting == null) {
            log.error("[execute][流程({}) HTTP 触发器请求配置为空]", processInstanceId);
            return;
        }

        // 2. 发起请求
        ProcessInstance processInstance = processInstanceService.getProcessInstance(processInstanceId);
        BpmHttpRequestUtils.executeBpmHttpRequest(processInstance,
                setting.getUrl(), setting.getHeader(), setting.getBody(), true, setting.getResponse());
    }

}
