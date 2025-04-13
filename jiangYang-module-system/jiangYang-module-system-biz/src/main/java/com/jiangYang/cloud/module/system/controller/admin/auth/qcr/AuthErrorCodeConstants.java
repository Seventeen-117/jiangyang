package com.jiangYang.cloud.module.system.controller.admin.auth.qcr;

import com.jiangYang.cloud.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类 - 认证相关
 */
public interface AuthErrorCodeConstants {

    // ========== 二维码登录相关 ==========
    ErrorCode AUTH_QR_CODE_NOT_FOUND = new ErrorCode(1120001000, "二维码不存在");
    ErrorCode AUTH_QR_CODE_NOT_AUTHORIZED = new ErrorCode(1120001001, "二维码未被授权");
    ErrorCode AUTH_QR_CODE_USER_NOT_EXISTS = new ErrorCode(1120001002, "二维码用户不存在");
    ErrorCode AUTH_QR_CODE_STATUS_ERROR = new ErrorCode(1120001003, "二维码状态错误");
    ErrorCode AUTH_QR_CODE_EXPIRED = new ErrorCode(1002016001, "二维码已过期");
}