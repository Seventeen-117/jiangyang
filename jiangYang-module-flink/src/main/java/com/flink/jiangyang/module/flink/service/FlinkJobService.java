package com.flink.jiangyang.module.flink.service;




import com.flink.jiangyang.module.flink.vo.FlinkJobInfoRespVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobStatusRespVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobSubmitReqVO;

import java.util.List;

public interface FlinkJobService {

    /**
     * 提交 Flink 任务
     *
     * @param reqVO 任务提交请求
     * @return 任务ID
     */
    String submitJob(FlinkJobSubmitReqVO reqVO) throws Exception;

    /**
     * 取消 Flink 任务
     *
     * @param jobId 任务ID
     * @return 是否成功
     */
    boolean cancelJob(String jobId) throws Exception;

    /**
     * 获取任务状态
     *
     * @param jobId 任务ID
     * @return 任务状态信息
     */
    FlinkJobStatusRespVO getJobStatus(String jobId) throws Exception;

    /**
     * 获取任务详细信息
     *
     * @param jobId 任务ID
     * @return 任务详细信息
     */
    FlinkJobInfoRespVO getJobInfo(String jobId) throws Exception;

    /**
     * 获取所有运行中的任务
     *
     * @return 任务列表
     */
    List<FlinkJobInfoRespVO> listRunningJobs() throws Exception;
}