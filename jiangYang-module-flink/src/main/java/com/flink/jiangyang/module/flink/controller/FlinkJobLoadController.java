package com.flink.jiangyang.module.flink.controller;



import com.flink.jiangyang.module.flink.mapper.FlinkJobLoadMapper;
import com.flink.jiangyang.module.flink.pojo.FlinkJobLoadDO;
import com.flink.jiangyang.module.flink.service.StarRocksCompletionService;
import com.flink.jiangyang.module.flink.vo.FlinkJobLoadInfoRespVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobLoadStatusRespVO;
import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

        import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Flink StarRocks 数据加载")
@RestController
@RequestMapping("/flink/load")
public class FlinkJobLoadController {

    @Resource
    private StarRocksCompletionService starRocksCompletionService;

    @Resource
    private FlinkJobLoadMapper flinkJobLoadMapper;

    @GetMapping("/status")
    @Operation(summary = "获取 Flink 任务数据加载状态")
    @Parameter(name = "jobId", description = "任务ID", required = true)
    public CommonResult<FlinkJobLoadStatusRespVO> getLoadStatus(@RequestParam("jobId") String jobId) {
        String status = starRocksCompletionService.getJobLoadStatus(jobId);

        FlinkJobLoadStatusRespVO respVO = new FlinkJobLoadStatusRespVO();
        respVO.setJobId(jobId);
        respVO.setStatus(status);
        return success(respVO);
    }

    @GetMapping("/info")
    @Operation(summary = "获取 Flink 任务数据加载详情")
    @Parameter(name = "jobId", description = "任务ID", required = true)
    public CommonResult<List<FlinkJobLoadInfoRespVO>> getLoadInfo(@RequestParam("jobId") String jobId) {
        List<FlinkJobLoadDO> jobLoads = flinkJobLoadMapper.selectListByJobId(jobId);

        List<FlinkJobLoadInfoRespVO> result = jobLoads.stream()
                .map(jobLoad -> {
                    FlinkJobLoadInfoRespVO vo = new FlinkJobLoadInfoRespVO();
                    vo.setJobId(jobLoad.getJobId());
                    vo.setTableName(jobLoad.getTableName());
                    vo.setStatus(jobLoad.getStatus());
                    vo.setRecordCount(jobLoad.getRecordCount());
                    vo.setCompletionTime(jobLoad.getCompletionTime());
                    vo.setErrorMessage(jobLoad.getErrorMessage());
                    return vo;
                })
                .collect(Collectors.toList());
        return success(result);
    }
}