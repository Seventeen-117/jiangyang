package com.jiangYang.cloud.module.iot.controller.admin.ota.vo.upgrade.task;

import com.jiangYang.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "管理后台 - IoT OTA 升级任务分页 Request VO")
public class IotOtaUpgradeTaskPageReqVO extends PageParam {

    /**
     * 任务名称字段，用于描述任务的名称
     */
    @Schema(description = "任务名称", example = "升级任务")
    private String name;

    /**
     * 固件编号字段，用于唯一标识固件，不能为空
     */
    @NotNull(message = "固件编号不能为空")
    @Schema(description = "固件编号", requiredMode = REQUIRED, example = "1024")
    private Long firmwareId;

}
