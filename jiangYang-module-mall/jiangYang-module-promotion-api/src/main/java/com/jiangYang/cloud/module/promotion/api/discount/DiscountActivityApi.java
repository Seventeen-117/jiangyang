package com.jiangYang.cloud.module.promotion.api.discount;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.module.promotion.api.discount.dto.DiscountProductRespDTO;
import com.jiangYang.cloud.module.promotion.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 限时折扣")
public interface DiscountActivityApi {

    String PREFIX = ApiConstants.PREFIX + "/discount-activity";

    @GetMapping(PREFIX + "/list-by-sku-id")
    @Operation(summary = "获得商品匹配的的限时折扣信息")
    @Parameter(name = "skuIds", description = "商品 SKU 编号数组", required = true, example = "[1, 2]")
    CommonResult<List<DiscountProductRespDTO>> getMatchDiscountProductListBySkuIds(@RequestParam("skuIds") Collection<Long> skuIds);

}
