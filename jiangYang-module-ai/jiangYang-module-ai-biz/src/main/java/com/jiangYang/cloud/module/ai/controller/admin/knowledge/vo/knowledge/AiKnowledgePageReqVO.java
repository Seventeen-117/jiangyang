package com.jiangYang.cloud.module.ai.controller.admin.knowledge.vo.knowledge;

import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.pojo.PageParam;
import com.jiangYang.cloud.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.jiangYang.cloud.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - AI 知识库的分页 Request VO")
@Data
public class AiKnowledgePageReqVO extends PageParam {

    @Schema(description = "知识库名称", example = "芋艿")
    private String name;

    @Schema(description = "是否启用", example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
