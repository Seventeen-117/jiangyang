package com.jiangYang.cloud.module.trade.job.order;

import com.jiangYang.cloud.framework.tenant.core.job.TenantJob;
import com.jiangYang.cloud.module.trade.service.order.TradeOrderUpdateService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 交易订单的自动收货 Job
 *
 * @author 芋道源码
 */
@Component
public class TradeOrderAutoReceiveJob {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;

    @XxlJob("tradeOrderAutoReceiveJob")
    @TenantJob // 多租户
    public String execute() {
        int count = tradeOrderUpdateService.receiveOrderBySystem();
        return String.format("自动收货 %s 个", count);
    }

}
