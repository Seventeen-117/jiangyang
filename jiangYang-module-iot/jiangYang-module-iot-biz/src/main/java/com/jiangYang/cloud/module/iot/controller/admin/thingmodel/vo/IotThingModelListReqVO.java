package com.jiangYang.cloud.module.iot.controller.admin.thingmodel.vo;

import com.jiangYang.cloud.framework.common.validation.InEnum;
import com.jiangYang.cloud.module.iot.enums.thingmodel.IotThingModelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - IoT 产品物模型 List Request VO")
@Data
public class IotThingModelListReqVO {

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "产品编号不能为空")
    private Long productId;

    @Schema(description = "功能标识", example = "temperature")
    private String identifier;

    @Schema(description = "功能名称", example = "温度")
    private String name;

    @Schema(description = "功能类型", example = "1")
    @InEnum(IotThingModelTypeEnum.class)
    private Integer type;

}
