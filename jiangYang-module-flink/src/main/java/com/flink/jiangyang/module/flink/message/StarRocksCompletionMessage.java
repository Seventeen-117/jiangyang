package com.flink.jiangyang.module.flink.message;



import lombok.Data;
import java.util.Date;
import java.util.Map;

/**
 * StarRocks 数据写入完成消息
 */
@Data
public class StarRocksCompletionMessage {
    /**
     * Flink 任务 ID
     */
    private String jobId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 完成时间
     */
    private Date completionTime;

    /**
     * 写入记录数
     */
    private Long recordCount;

    /**
     * 写入状态, SUCCESS/FAILED
     */
    private String status;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 额外信息
     */
    private Map<String, Object> metadata;
}