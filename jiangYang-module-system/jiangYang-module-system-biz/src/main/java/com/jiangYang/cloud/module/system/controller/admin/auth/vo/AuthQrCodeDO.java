package com.jiangYang.cloud.module.system.controller.admin.auth.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jiangYang.cloud.framework.mybatis.core.dataobject.BaseDO;
import com.jiangYang.cloud.module.system.controller.admin.auth.qcr.QrCodeStatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@TableName("system_auth_qrcode")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthQrCodeDO extends BaseDO {

    /**
     * 二维码ID
     */
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户标识
     */
    private String tenantName;

    /**
     * 二维码状态
     *
     * 枚举 {@link QrCodeStatusEnum}
     */
    private Integer status;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
