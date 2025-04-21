package com.flink.jiangyang.module.flink.service;


import com.flink.jiangyang.module.flink.message.StarRocksCompletionMessage;

/**
 * StarRocks 数据写入完成服务接口
 */
public interface StarRocksCompletionService {

    /**
     * 处理 StarRocks 数据写入完成消息
     *
     * @param message 消息内容
     */
    void processCompletionMessage(StarRocksCompletionMessage message);

    /**
     * 获取任务数据写入状态
     *
     * @param jobId Flink 任务 ID
     * @return 数据写入状态
     */
    String getJobLoadStatus(String jobId);

    /**
     * 获取任务数据写入记录数
     *
     * @param jobId Flink 任务 ID
     * @param tableName 表名
     * @return 写入记录数
     */
    Long getJobLoadRecordCount(String jobId, String tableName);
}
