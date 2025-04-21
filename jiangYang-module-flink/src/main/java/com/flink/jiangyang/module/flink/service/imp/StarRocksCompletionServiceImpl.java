package com.flink.jiangyang.module.flink.service.imp;




import com.flink.jiangyang.module.flink.mapper.FlinkJobLoadMapper;
import com.flink.jiangyang.module.flink.message.StarRocksCompletionMessage;
import com.flink.jiangyang.module.flink.pojo.FlinkJobLoadDO;
import com.flink.jiangyang.module.flink.service.FlinkJobService;
import com.flink.jiangyang.module.flink.service.StarRocksCompletionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * StarRocks 数据写入完成服务实现
 */
@Slf4j
@Service
public class StarRocksCompletionServiceImpl implements StarRocksCompletionService {

    @Resource
    private FlinkJobLoadMapper flinkJobLoadMapper;

    @Resource
    private FlinkJobService flinkJobService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processCompletionMessage(StarRocksCompletionMessage message) {
        // 1. 先查询是否已存在相同记录
        FlinkJobLoadDO existingLoad = flinkJobLoadMapper.selectByJobIdAndTable(
                message.getJobId(), message.getTableName());

        if (existingLoad != null) {
            // 更新记录
            existingLoad.setRecordCount(message.getRecordCount());
            existingLoad.setStatus(message.getStatus());
            existingLoad.setCompletionTime(message.getCompletionTime());
            existingLoad.setErrorMessage(message.getErrorMessage());
            existingLoad.setMetadata(message.getMetadata());
            existingLoad.setUpdateTime(new Date());

            flinkJobLoadMapper.updateById(existingLoad);
            log.info("更新 StarRocks 数据写入记录: jobId={}, tableName={}, status={}",
                    message.getJobId(), message.getTableName(), message.getStatus());
        } else {
            // 创建新记录
            FlinkJobLoadDO jobLoad = new FlinkJobLoadDO();
            jobLoad.setJobId(message.getJobId());
            jobLoad.setTableName(message.getTableName());
            jobLoad.setRecordCount(message.getRecordCount());
            jobLoad.setStatus(message.getStatus());
            jobLoad.setCompletionTime(message.getCompletionTime());
            jobLoad.setErrorMessage(message.getErrorMessage());
            jobLoad.setMetadata(message.getMetadata());
            jobLoad.setCreateTime(new Date());
            jobLoad.setUpdateTime(new Date());

            flinkJobLoadMapper.insert(jobLoad);
            log.info("新增 StarRocks 数据写入记录: jobId={}, tableName={}, status={}",
                    message.getJobId(), message.getTableName(), message.getStatus());
        }

        // 2. 如果写入失败，发送通知（可根据需求添加）
        if ("FAILED".equals(message.getStatus())) {
            // TODO: 发送通知
            log.warn("StarRocks 数据写入失败: jobId={}, tableName={}, error={}",
                    message.getJobId(), message.getTableName(), message.getErrorMessage());
        }
    }

    @Override
    public String getJobLoadStatus(String jobId) {
        List<FlinkJobLoadDO> jobLoads = flinkJobLoadMapper.selectListByJobId(jobId);
        if (jobLoads.isEmpty()) {
            return "UNKNOWN";
        }

        // 如果有任何一个失败，则整体失败
        for (FlinkJobLoadDO jobLoad : jobLoads) {
            if ("FAILED".equals(jobLoad.getStatus())) {
                return "FAILED";
            }
        }

        // 所有都成功，则整体成功
        return "SUCCESS";
    }

    @Override
    public Long getJobLoadRecordCount(String jobId, String tableName) {
        FlinkJobLoadDO jobLoad = flinkJobLoadMapper.selectByJobIdAndTable(jobId, tableName);
        if (jobLoad == null) {
            return 0L;
        }
        return jobLoad.getRecordCount();
    }
}