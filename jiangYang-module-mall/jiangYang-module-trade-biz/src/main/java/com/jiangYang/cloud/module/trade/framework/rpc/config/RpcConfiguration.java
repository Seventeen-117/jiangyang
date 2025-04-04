package com.jiangYang.cloud.module.trade.framework.rpc.config;

import com.jiangYang.cloud.module.member.api.address.MemberAddressApi;
import com.jiangYang.cloud.module.member.api.config.MemberConfigApi;
import com.jiangYang.cloud.module.member.api.level.MemberLevelApi;
import com.jiangYang.cloud.module.member.api.point.MemberPointApi;
import com.jiangYang.cloud.module.member.api.user.MemberUserApi;
import com.jiangYang.cloud.module.pay.api.order.PayOrderApi;
import com.jiangYang.cloud.module.pay.api.refund.PayRefundApi;
import com.jiangYang.cloud.module.pay.api.transfer.PayTransferApi;
import com.jiangYang.cloud.module.pay.api.wallet.PayWalletApi;
import com.jiangYang.cloud.module.product.api.category.ProductCategoryApi;
import com.jiangYang.cloud.module.product.api.comment.ProductCommentApi;
import com.jiangYang.cloud.module.product.api.sku.ProductSkuApi;
import com.jiangYang.cloud.module.product.api.spu.ProductSpuApi;
import com.jiangYang.cloud.module.promotion.api.bargain.BargainActivityApi;
import com.jiangYang.cloud.module.promotion.api.bargain.BargainRecordApi;
import com.jiangYang.cloud.module.promotion.api.combination.CombinationRecordApi;
import com.jiangYang.cloud.module.promotion.api.coupon.CouponApi;
import com.jiangYang.cloud.module.promotion.api.discount.DiscountActivityApi;
import com.jiangYang.cloud.module.promotion.api.point.PointActivityApi;
import com.jiangYang.cloud.module.promotion.api.reward.RewardActivityApi;
import com.jiangYang.cloud.module.promotion.api.seckill.SeckillActivityApi;
import com.jiangYang.cloud.module.system.api.notify.NotifyMessageSendApi;
import com.jiangYang.cloud.module.system.api.social.SocialClientApi;
import com.jiangYang.cloud.module.system.api.social.SocialUserApi;
import com.jiangYang.cloud.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {
        BargainActivityApi.class, BargainRecordApi.class, CombinationRecordApi.class,
        CouponApi.class, DiscountActivityApi.class, RewardActivityApi.class, SeckillActivityApi.class, PointActivityApi.class,
        MemberUserApi.class, MemberPointApi.class, MemberLevelApi.class, MemberAddressApi.class, MemberConfigApi.class,
        ProductSpuApi.class, ProductSkuApi.class, ProductCommentApi.class, ProductCategoryApi.class,
        PayOrderApi.class, PayRefundApi.class, PayTransferApi.class, PayWalletApi.class,
        AdminUserApi.class, NotifyMessageSendApi.class, SocialClientApi.class, SocialUserApi.class
})
public class RpcConfiguration {
}
