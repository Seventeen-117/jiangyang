package com.flink.jiangyang.module.flink.config;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jiangyang.flink.rocketmq")
public class FlinkRocketMqProperties {

    /**
     * StarRocks 数据写入完成消息 Topic
     */
    private String starRocksCompletionTopic = "TOPIC_STARROCKS_COMPLETION";

    /**
     * 消费者组
     */
    private String consumerGroup = "CG_FLINK_STARROCKS_COMPLETION";

    // Getters and Setters
    public String getStarRocksCompletionTopic() {
        return starRocksCompletionTopic;
    }

    public void setStarRocksCompletionTopic(String starRocksCompletionTopic) {
        this.starRocksCompletionTopic = starRocksCompletionTopic;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }
}