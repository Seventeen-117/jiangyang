package com.jiangYang.cloud.module.pay.enums;

import com.jiangYang.cloud.framework.common.enums.RpcConstants;

/**
 * API 相关的枚举
 *
 * @author 芋道源码
 */
public class ApiConstants {

    /**
     * 服务名
     *
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "pay-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX + "/pay";

    public static final String VERSION = "1.0.0";

}
