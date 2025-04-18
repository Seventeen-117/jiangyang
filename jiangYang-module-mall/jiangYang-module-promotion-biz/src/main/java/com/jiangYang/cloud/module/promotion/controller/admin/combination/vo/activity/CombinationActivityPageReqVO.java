package com.jiangYang.cloud.module.promotion.controller.admin.combination.vo.activity;

import com.jiangYang.cloud.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 拼团活动分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityPageReqVO extends PageParam {

    @Schema(description = "拼团名称", example = "赵六")
    private String name;

    @Schema(description = "活动状态", example = "0")
    private Integer status;

}
