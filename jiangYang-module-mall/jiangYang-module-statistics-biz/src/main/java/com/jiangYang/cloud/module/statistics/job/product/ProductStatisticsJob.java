package com.jiangYang.cloud.module.statistics.job.product;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.jiangYang.cloud.framework.tenant.core.job.TenantJob;
import com.jiangYang.cloud.module.statistics.service.product.ProductStatisticsService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 商品统计 Job
 *
 * @author owen
 */
@Component
public class ProductStatisticsJob {

    @Resource
    private ProductStatisticsService productStatisticsService;

    /**
     * 执行商品统计任务
     *
     * @param param 要统计的天数，只能是正整数，1 代表昨日数据
     * @return 统计结果
     */
    @XxlJob("productStatisticsJob")
    @TenantJob
    public String execute(String param) {
        // 默认昨日
        param = ObjUtil.defaultIfBlank(param, "1");
        // 校验参数的合理性
        if (!NumberUtil.isInteger(param)) {
            throw new RuntimeException("商品统计任务的参数只能为是正整数");
        }
        Integer days = Convert.toInt(param, 0);
        if (days < 1) {
            throw new RuntimeException("商品统计任务的参数只能为是正整数");
        }
        String result = productStatisticsService.statisticsProduct(days);
        return StrUtil.format("商品统计:\n{}", result);
    }

}
