package com.flink.jiangyang.module.flink.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flink.jiangyang.module.flink.pojo.FlinkJobLoadDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Flink 任务数据加载 Mapper
 */
@Mapper
public interface FlinkJobLoadMapper extends BaseMapper<FlinkJobLoadDO> {

    /**
     * 根据任务 ID 和表名查询
     *
     * @param jobId 任务 ID
     * @param tableName 表名
     * @return 数据加载记录
     */
    default FlinkJobLoadDO selectByJobIdAndTable(String jobId, String tableName) {
        return selectOne(new LambdaQueryWrapper<FlinkJobLoadDO>()
                .eq(FlinkJobLoadDO::getJobId, jobId)
                .eq(FlinkJobLoadDO::getTableName, tableName));
    }

    /**
     * 根据任务 ID 查询所有表的加载记录
     *
     * @param jobId 任务 ID
     * @return 加载记录列表
     */
    default List<FlinkJobLoadDO> selectListByJobId(String jobId) {
        return selectList(new LambdaQueryWrapper<FlinkJobLoadDO>()
                .eq(FlinkJobLoadDO::getJobId, jobId));
    }
}