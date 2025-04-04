package com.jiangYang.cloud.module.product.framework.rpc.config;

import com.jiangYang.cloud.module.member.api.level.MemberLevelApi;
import com.jiangYang.cloud.module.member.api.user.MemberUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {MemberUserApi.class, MemberLevelApi.class})
public class RpcConfiguration {
}
