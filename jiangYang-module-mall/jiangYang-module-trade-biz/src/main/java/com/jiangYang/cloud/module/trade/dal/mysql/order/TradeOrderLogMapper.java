package com.jiangYang.cloud.module.trade.dal.mysql.order;

import com.jiangYang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangYang.cloud.module.trade.dal.dataobject.order.TradeOrderLogDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeOrderLogMapper extends BaseMapperX<TradeOrderLogDO> {

    default List<TradeOrderLogDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapper<TradeOrderLogDO>()
                .eq(TradeOrderLogDO::getOrderId, orderId)
                .orderByDesc(TradeOrderLogDO::getCreateTime));
    }

}
