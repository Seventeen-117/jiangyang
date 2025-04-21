package com.flink.jiangyang.module.flink.vo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * Flink 任务数据加载详情 VO
 */
@Data
@Schema(description = "Flink 任务数据加载详情 VO")
public class FlinkJobLoadInfoRespVO {

    @Schema(description = "任务ID")
    private String jobId;

    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "状态: SUCCESS-成功, FAILED-失败")
    private String status;

    @Schema(description = "记录数")
    private Long recordCount;

    @Schema(description = "完成时间")
    private Date completionTime;

    @Schema(description = "错误信息")
    private String errorMessage;
}
