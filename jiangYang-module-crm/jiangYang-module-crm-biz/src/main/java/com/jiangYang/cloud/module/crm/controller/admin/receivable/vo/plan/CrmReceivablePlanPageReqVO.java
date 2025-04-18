package com.jiangYang.cloud.module.crm.controller.admin.receivable.vo.plan;

import com.jiangYang.cloud.framework.common.pojo.PageParam;
import com.jiangYang.cloud.framework.common.validation.InEnum;
import com.jiangYang.cloud.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - CRM 回款计划分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePlanPageReqVO extends PageParam {

    /**
     * 提醒类型 - 待回款
     */
    public final static Integer REMIND_TYPE_NEEDED = 1;
    /**
     * 提醒类型 - 已逾期
     */
    public final static Integer REMIND_TYPE_EXPIRED = 2;
    /**
     * 提醒类型 - 已回款
     */
    public final static Integer REMIND_TYPE_RECEIVED = 3;

    @Schema(description = "客户编号", example = "18026")
    private Long customerId;

    @Schema(description = "合同编号", example = "H3473")
    private String contractNo;

    @Schema(description = "合同编号", example = "3473")
    private Long contractId;

    @Schema(description = "场景类型", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // 场景类型，为 null 时则表示全部

    @Schema(description = "提醒类型", example = "1")
    private Integer remindType; // 提醒类型，为 null 时则表示全部

}
