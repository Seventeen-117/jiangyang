package com.flink.jiangyang.module.flink.vo;


import lombok.Data;

@Data
public class FlinkJobInfoRespVO {

    /**
     * 任务ID
     */
    private String jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 运行时长
     */
    private long duration;

    /**
     * 任务数量
     */
    private int tasks;

    /**
     * 并行度
     */
    private int parallelism;

    // Getters and Setters
    // ...
}