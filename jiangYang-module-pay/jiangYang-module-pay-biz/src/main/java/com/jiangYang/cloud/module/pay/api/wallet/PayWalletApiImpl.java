package com.jiangYang.cloud.module.pay.api.wallet;

import cn.hutool.core.lang.Assert;
import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.module.pay.api.wallet.dto.PayWalletAddBalanceReqDTO;
import com.jiangYang.cloud.module.pay.dal.dataobject.wallet.PayWalletDO;
import com.jiangYang.cloud.module.pay.enums.wallet.PayWalletBizTypeEnum;
import com.jiangYang.cloud.module.pay.service.wallet.PayWalletService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.*;

/**
 * 钱包 API 实现类
 *
 * @author jason
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class PayWalletApiImpl implements PayWalletApi {

    @Resource
    private PayWalletService payWalletService;

    @Override
    public CommonResult<Boolean> addWalletBalance(PayWalletAddBalanceReqDTO reqDTO) {
        // 创建或获取钱包
        PayWalletDO wallet = payWalletService.getOrCreateWallet(reqDTO.getUserId(), reqDTO.getUserType());
        Assert.notNull(wallet, "钱包({}/{})不存在", reqDTO.getUserId(), reqDTO.getUserType());

        // 增加余额
        PayWalletBizTypeEnum bizType = PayWalletBizTypeEnum.valueOf(reqDTO.getBizType());
        payWalletService.addWalletBalance(wallet.getId(), reqDTO.getBizId(), bizType, reqDTO.getPrice());
        return success(true);
    }

}
