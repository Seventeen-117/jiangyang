package com.flink.jiangyang.module.flink.controller;



import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.flink.jiangyang.module.flink.vo.FlinkJobSubmitReqVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobInfoRespVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobStatusRespVO;
import com.flink.jiangyang.module.flink.service.FlinkJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Flink 任务管理")
@RestController
@RequestMapping("/flink/job")
public class FlinkJobController {

    @Resource
    private FlinkJobService flinkJobService;

    @PostMapping("/submit")
    @Operation(summary = "提交 Flink 任务")
    public CommonResult<String> submitJob(@RequestBody FlinkJobSubmitReqVO reqVO) throws Exception {
        return success(flinkJobService.submitJob(reqVO));
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消 Flink 任务")
    @Parameter(name = "jobId", description = "任务ID", required = true)
    public CommonResult<Boolean> cancelJob(@RequestParam("jobId") String jobId) throws Exception {
        return success(flinkJobService.cancelJob(jobId));
    }

    @GetMapping("/status")
    @Operation(summary = "获取 Flink 任务状态")
    @Parameter(name = "jobId", description = "任务ID", required = true)
    public CommonResult<FlinkJobStatusRespVO> getJobStatus(@RequestParam("jobId") String jobId) throws Exception {
        return success(flinkJobService.getJobStatus(jobId));
    }

    @GetMapping("/info")
    @Operation(summary = "获取 Flink 任务详情")
    @Parameter(name = "jobId", description = "任务ID", required = true)
    public CommonResult<FlinkJobInfoRespVO> getJobInfo(@RequestParam("jobId") String jobId) throws Exception {
        return success(flinkJobService.getJobInfo(jobId));
    }

    @GetMapping("/list-running")
    @Operation(summary = "获取所有运行中的 Flink 任务")
    public CommonResult<List<FlinkJobInfoRespVO>> listRunningJobs() throws Exception {
        return success(flinkJobService.listRunningJobs());
    }
}