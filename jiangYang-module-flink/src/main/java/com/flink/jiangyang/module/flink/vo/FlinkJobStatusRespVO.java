package com.flink.jiangyang.module.flink.vo;


import lombok.Data;

@Data
public class FlinkJobStatusRespVO {

    /**
     * 任务ID
     */
    private String jobId;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 是否运行中
     */
    private boolean running;

    /**
     * 是否已完成（包括成功和失败）
     */
    private boolean finished;

}