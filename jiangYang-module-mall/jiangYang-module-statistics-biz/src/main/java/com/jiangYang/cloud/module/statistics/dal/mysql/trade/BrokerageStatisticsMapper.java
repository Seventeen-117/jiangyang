package com.jiangYang.cloud.module.statistics.dal.mysql.trade;

import com.jiangYang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangYang.cloud.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 订单分销的统计 Mapper
 *
 * @author owen
 */
@Mapper
public interface BrokerageStatisticsMapper extends BaseMapperX<TradeStatisticsDO> {

    Integer selectSummaryPriceByStatusAndUnfreezeTimeBetween(@Param("bizType") Integer bizType,
                                                             @Param("status") Integer status,
                                                             @Param("beginTime") LocalDateTime beginTime,
                                                             @Param("endTime") LocalDateTime endTime);

    Long selectWithdrawCountByStatus(@Param("status") Integer status);

}
