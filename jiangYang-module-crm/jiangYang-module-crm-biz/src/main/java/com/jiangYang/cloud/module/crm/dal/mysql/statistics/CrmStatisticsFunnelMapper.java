package com.jiangYang.cloud.module.crm.dal.mysql.statistics;

import com.jiangYang.cloud.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsBusinessInversionRateSummaryByDateRespVO;
import com.jiangYang.cloud.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsBusinessSummaryByDateRespVO;
import com.jiangYang.cloud.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsBusinessSummaryByEndStatusRespVO;
import com.jiangYang.cloud.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsFunnelReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CRM 销售漏斗 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface CrmStatisticsFunnelMapper {

    Long selectCustomerCountByDate(CrmStatisticsFunnelReqVO reqVO);

    Long selectBusinessCountByDateAndEndStatus(@Param("reqVO") CrmStatisticsFunnelReqVO reqVO, @Param("status") Integer status);

    List<CrmStatisticsBusinessSummaryByEndStatusRespVO> selectBusinessSummaryListGroupByEndStatus(CrmStatisticsFunnelReqVO reqVO);

    List<CrmStatisticsBusinessSummaryByDateRespVO> selectBusinessSummaryGroupByDate(CrmStatisticsFunnelReqVO reqVO);

    List<CrmStatisticsBusinessInversionRateSummaryByDateRespVO> selectBusinessInversionRateSummaryByDate(CrmStatisticsFunnelReqVO reqVO);

}
