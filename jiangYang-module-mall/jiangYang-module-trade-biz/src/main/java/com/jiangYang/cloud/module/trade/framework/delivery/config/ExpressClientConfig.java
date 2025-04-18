package com.jiangYang.cloud.module.trade.framework.delivery.config;

import com.jiangYang.cloud.module.trade.framework.delivery.core.client.ExpressClient;
import com.jiangYang.cloud.module.trade.framework.delivery.core.client.ExpressClientFactory;
import com.jiangYang.cloud.module.trade.framework.delivery.core.client.impl.ExpressClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 快递客户端端配置类：
 *
 * 1. 快递客户端工厂 {@link ExpressClientFactory}
 * 2. 默认的快递客户端实现 {@link ExpressClient}
 *
 * @author jason
 */
@Configuration(proxyBeanMethods = false)
public class ExpressClientConfig {

    @Bean
    public ExpressClientFactory expressClientFactory(TradeExpressProperties tradeExpressProperties,
                                                     RestTemplate restTemplate) {
        return new ExpressClientFactoryImpl(tradeExpressProperties, restTemplate);
    }

    @Bean
    public ExpressClient defaultExpressClient(ExpressClientFactory expressClientFactory) {
        return expressClientFactory.getDefaultExpressClient();
    }

}
