package com.jiangYang.cloud.module.system.controller.admin.auth.vo;

import lombok.Data;

@Data
public class QrCodeRespVO {

    /**
     * 二维码ID
     */
    private String id;

    /**
     * 二维码图片URL
     */
    private String url;

    /**
     * 二维码图片Base64
     */
    private String qrCodeBase64;

}
