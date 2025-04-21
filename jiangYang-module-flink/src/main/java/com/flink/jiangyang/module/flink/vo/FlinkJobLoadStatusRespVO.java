package com.flink.jiangyang.module.flink.vo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Flink 任务数据加载状态 VO
 */
@Data
@Schema(description = "Flink 任务数据加载状态 VO")
public class FlinkJobLoadStatusRespVO {

    @Schema(description = "任务ID")
    private String jobId;

    @Schema(description = "状态: SUCCESS-成功, FAILED-失败, UNKNOWN-未知")
    private String status;
}