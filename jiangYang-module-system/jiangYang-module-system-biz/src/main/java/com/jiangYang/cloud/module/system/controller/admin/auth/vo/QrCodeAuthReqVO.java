package com.jiangYang.cloud.module.system.controller.admin.auth.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@Valid
public class QrCodeAuthReqVO {

    @NotEmpty(message = "二维码ID不能为空")
    private String id;

    @NotEmpty(message = "租户名不能为空")
    private String tenantName;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;
}