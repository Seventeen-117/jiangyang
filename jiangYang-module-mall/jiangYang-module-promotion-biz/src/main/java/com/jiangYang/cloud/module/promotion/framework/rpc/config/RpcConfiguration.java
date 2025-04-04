package com.jiangYang.cloud.module.promotion.framework.rpc.config;

import com.jiangYang.cloud.module.infra.api.websocket.WebSocketSenderApi;
import com.jiangYang.cloud.module.member.api.user.MemberUserApi;
import com.jiangYang.cloud.module.product.api.category.ProductCategoryApi;
import com.jiangYang.cloud.module.product.api.sku.ProductSkuApi;
import com.jiangYang.cloud.module.product.api.spu.ProductSpuApi;
import com.jiangYang.cloud.module.system.api.social.SocialClientApi;
import com.jiangYang.cloud.module.system.api.user.AdminUserApi;
import com.jiangYang.cloud.module.trade.api.order.TradeOrderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ProductSkuApi.class, ProductSpuApi.class, ProductCategoryApi.class,
        MemberUserApi.class, TradeOrderApi.class, AdminUserApi.class, SocialClientApi.class,
        WebSocketSenderApi.class})
public class RpcConfiguration {
}
