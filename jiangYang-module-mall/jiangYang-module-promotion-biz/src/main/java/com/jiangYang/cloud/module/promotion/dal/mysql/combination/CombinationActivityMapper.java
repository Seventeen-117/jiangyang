package com.jiangYang.cloud.module.promotion.dal.mysql.combination;

import com.jiangYang.cloud.framework.common.pojo.PageParam;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangYang.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.jiangYang.cloud.module.promotion.controller.admin.combination.vo.activity.CombinationActivityPageReqVO;
import com.jiangYang.cloud.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 拼团活动 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface CombinationActivityMapper extends BaseMapperX<CombinationActivityDO> {

    default PageResult<CombinationActivityDO> selectPage(CombinationActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CombinationActivityDO>()
                .likeIfPresent(CombinationActivityDO::getName, reqVO.getName())
                .eqIfPresent(CombinationActivityDO::getStatus, reqVO.getStatus())
                .orderByDesc(CombinationActivityDO::getId));
    }

    default List<CombinationActivityDO> selectListByStatus(Integer status) {
        return selectList(CombinationActivityDO::getStatus, status);
    }

    default PageResult<CombinationActivityDO> selectPage(PageParam pageParam, Integer status) {
        return selectPage(pageParam, new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getStatus, status));
    }

    default CombinationActivityDO selectBySpuIdAndStatusAndNow(Long spuId, Integer status) {
        LocalDateTime now = LocalDateTime.now();
        return selectOne(new LambdaQueryWrapperX<CombinationActivityDO>()
                .eq(CombinationActivityDO::getSpuId, spuId)
                .eq(CombinationActivityDO::getStatus, status)
                .lt(CombinationActivityDO::getStartTime, now)
                .gt(CombinationActivityDO::getEndTime, now)); // 开始时间 < now < 结束时间，也就是说获取指定时间段的活动
    }

}