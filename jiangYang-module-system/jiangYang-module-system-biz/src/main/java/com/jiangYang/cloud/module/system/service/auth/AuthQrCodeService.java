package com.jiangYang.cloud.module.system.service.auth;

import com.jiangYang.cloud.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.jiangYang.cloud.module.system.controller.admin.auth.vo.QrCodeAuthReqVO;
import com.jiangYang.cloud.module.system.controller.admin.auth.vo.QrCodeRespVO;
import com.jiangYang.cloud.module.system.controller.admin.auth.vo.QrCodeStatusRespVO;

public interface AuthQrCodeService {

    QrCodeRespVO createQrCode(String tenantName);

    QrCodeStatusRespVO getQrCodeStatus(String id, String tenantName);

    void scanQrCode(String id);

    AuthLoginRespVO verifyQrCode(QrCodeAuthReqVO reqVO);
}