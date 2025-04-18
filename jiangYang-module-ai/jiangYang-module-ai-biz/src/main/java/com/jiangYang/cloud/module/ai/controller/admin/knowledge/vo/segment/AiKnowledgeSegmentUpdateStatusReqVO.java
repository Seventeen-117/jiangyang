package com.jiangYang.cloud.module.ai.controller.admin.knowledge.vo.segment;

import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Schema(description = "管理后台 - AI 知识库段落的更新状态 Request VO")
@Data
public class AiKnowledgeSegmentUpdateStatusReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24790")
    private Long id;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "是否启用不能为空")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

}
