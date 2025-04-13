package com.jiangYang.cloud.module.system.controller.admin.auth.qcr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QrCodeStatusEnum {

    UNUSED(0, "未扫描"),
    SCANNED(1, "已扫描"),
    VERIFYING(2, "验证中"),
    AUTHORIZED(3, "已授权"),
    AUTH_FAILED(4, "验证失败"),
    EXPIRED(5, "已过期");

    private final Integer status;
    private final String description;
}