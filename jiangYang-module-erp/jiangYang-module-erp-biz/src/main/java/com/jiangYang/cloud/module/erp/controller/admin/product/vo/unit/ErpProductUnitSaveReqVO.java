package com.jiangYang.cloud.module.erp.controller.admin.product.vo.unit;

import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - ERP 产品单位新增/修改 Request VO")
@Data
public class ErpProductUnitSaveReqVO {

    @Schema(description = "单位编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31254")
    private Long id;

    @Schema(description = "单位名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "单位名字不能为空")
    private String name;

    @Schema(description = "单位状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "单位状态不能为空")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

}