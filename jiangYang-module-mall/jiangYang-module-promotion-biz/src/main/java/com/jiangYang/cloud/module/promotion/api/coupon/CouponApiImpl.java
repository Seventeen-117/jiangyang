package com.jiangYang.cloud.module.promotion.api.coupon;


import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.promotion.api.coupon.dto.CouponRespDTO;
import com.jiangYang.cloud.module.promotion.api.coupon.dto.CouponUseReqDTO;
import com.jiangYang.cloud.module.promotion.service.coupon.CouponService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

/**
 * 优惠劵 API 实现类
 *
 * @author 芋道源码
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class CouponApiImpl implements CouponApi {

    @Resource
    private CouponService couponService;

    @Override
    public CommonResult<List<CouponRespDTO>> getCouponListByUserId(Long userId, Integer status) {
        return success(BeanUtils.toBean(couponService.getCouponList(userId, status), CouponRespDTO.class));
    }

    @Override
    public CommonResult<Boolean> useCoupon(CouponUseReqDTO useReqDTO) {
        couponService.useCoupon(useReqDTO.getId(), useReqDTO.getUserId(), useReqDTO.getOrderId());
        return success(true);
    }

    @Override
    public CommonResult<Boolean> returnUsedCoupon(Long id) {
        couponService.returnUsedCoupon(id);
        return success(true);
    }

    @Override
    public CommonResult<List<Long>> takeCouponsByAdmin(Map<Long, Integer> giveCoupons, Long userId) {
        return success(couponService.takeCouponsByAdmin(giveCoupons, userId));
    }

    @Override
    public CommonResult<Boolean> invalidateCouponsByAdmin(List<Long> giveCouponIds, Long userId) {
        couponService.invalidateCouponsByAdmin(giveCouponIds, userId);
        return success(true);
    }

}
