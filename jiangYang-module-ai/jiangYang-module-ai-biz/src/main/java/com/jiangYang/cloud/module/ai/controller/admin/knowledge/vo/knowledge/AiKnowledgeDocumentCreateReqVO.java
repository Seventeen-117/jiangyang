package com.jiangYang.cloud.module.ai.controller.admin.knowledge.vo.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Schema(description = "管理后台 - AI 知识库文档的创建 Request VO")
@Data
public class AiKnowledgeDocumentCreateReqVO {

    @Schema(description = "知识库编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1204")
    @NotNull(message = "知识库编号不能为空")
    private Long knowledgeId;

    @Schema(description = "文档名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "三方登陆")
    @NotBlank(message = "文档名称不能为空")
    private String name;

    @Schema(description = "文档 URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://doc.iocoder.cn")
    @URL(message = "文档 URL 格式不正确")
    private String url;

    @Schema(description = "分段的最大 Token 数", requiredMode = Schema.RequiredMode.REQUIRED, example = "800")
    @NotNull(message = "分段的最大 Token 数不能为空")
    private Integer segmentMaxTokens;

}
