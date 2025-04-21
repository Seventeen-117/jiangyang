package com.flink.jiangyang.module.flink.mq;



import com.flink.jiangyang.module.flink.message.StarRocksCompletionMessage;

import com.flink.jiangyang.module.flink.service.StarRocksCompletionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * StarRocks 数据写入完成消息监听器
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${jiangYang.flink.rocketmq.starRocksCompletionTopic}",
        consumerGroup = "${jiangYang.flink.rocketmq.consumerGroup}"
)
public class StarRocksCompletionListener implements RocketMQListener<StarRocksCompletionMessage> {

    @Resource
    private StarRocksCompletionService starRocksCompletionService;

    @Override
    public void onMessage(StarRocksCompletionMessage message) {
        log.info("接收到 StarRocks 数据写入完成消息: {}", message);

        try {
            starRocksCompletionService.processCompletionMessage(message);
        } catch (Exception e) {
            log.error("处理 StarRocks 数据写入完成消息失败: {}", message, e);
        }
    }
}