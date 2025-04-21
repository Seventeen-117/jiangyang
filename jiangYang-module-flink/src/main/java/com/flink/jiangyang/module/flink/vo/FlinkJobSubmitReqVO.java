package com.flink.jiangyang.module.flink.vo;


import lombok.Data;

@Data
public class FlinkJobSubmitReqVO {

    /**
     * Jar 包名称
     */
    private String jarName;

    /**
     * 完整 Jar 路径（优先级高于 jarName）
     */
    private String jarPath;

    /**
     * 入口类
     */
    private String entryClass;

    /**
     * 程序参数，空格分隔
     */
    private String programArgs;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 并行度
     */
    private Integer parallelism;

}