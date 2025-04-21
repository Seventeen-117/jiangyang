package com.flink.jiangyang.module.flink.service.imp;

import com.jiangYang.cloud.framework.common.exception.ServiceException;
import com.flink.jiangyang.module.flink.config.FlinkProperties;
import com.flink.jiangyang.module.flink.vo.FlinkJobSubmitReqVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobInfoRespVO;
import com.flink.jiangyang.module.flink.vo.FlinkJobStatusRespVO;
import com.flink.jiangyang.module.flink.service.FlinkJobService;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.JobStatus;
import org.apache.flink.client.deployment.StandaloneClusterId;
import org.apache.flink.client.program.rest.RestClusterClient;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.RestOptions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.apache.flink.runtime.client.JobStatusMessage;
import org.apache.flink.runtime.messages.Acknowledge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class FlinkJobServiceImpl implements FlinkJobService {

    private static final Logger log = LoggerFactory.getLogger(FlinkJobServiceImpl.class);

    @Resource
    private FlinkProperties flinkProperties;

    @Override
    public String submitJob(FlinkJobSubmitReqVO reqVO) throws Exception {
        try {
            // 获取 Flink REST API 地址
            String restUrl = flinkProperties.getJobManagerUrl();
            if (!restUrl.startsWith("http://")) {
                restUrl = "http://" + restUrl;
            }

            // 1. 获取 JAR 路径
            String jarFilePath = reqVO.getJarPath();
            if (!StringUtils.hasText(jarFilePath)) {
                jarFilePath = flinkProperties.getJarPath() + File.separator + reqVO.getJarName();
            }

            // 2. 上传 JAR 文件
            RestTemplate restTemplate = new RestTemplate();

            // 准备上传 JAR 文件
            File jarFile = new File(jarFilePath);
            if (!jarFile.exists()) {
                throw new Exception("JAR 文件不存在: " + jarFilePath);
            }

            // 创建 MultipartFile 请求
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            FileSystemResource fileResource = new FileSystemResource(jarFile);
            body.add("jarfile", fileResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 上传 JAR 文件
            ResponseEntity<Map> uploadResponse = restTemplate.postForEntity(
                    restUrl + "/jars/upload",
                    requestEntity,
                    Map.class
            );

            if (!uploadResponse.getStatusCode().is2xxSuccessful()) {
                throw new Exception("上传 JAR 文件失败: " + uploadResponse.getStatusCode());
            }

            // 获取上传后的 JAR 文件名
            Map<String, Object> uploadResult = uploadResponse.getBody();
            String jarId = (String) uploadResult.get("filename");

            // 3. 运行 JAR 文件
            Map<String, Object> runRequest = new HashMap<>();
            if (reqVO.getEntryClass() != null) {
                runRequest.put("entryClass", reqVO.getEntryClass());
            }
            if (reqVO.getProgramArgs() != null) {
                runRequest.put("programArgs", reqVO.getProgramArgs());
            }
            if (reqVO.getParallelism() != null) {
                runRequest.put("parallelism", reqVO.getParallelism());
            }

            HttpEntity<Map<String, Object>> runEntity = new HttpEntity<>(runRequest, new HttpHeaders());

            ResponseEntity<Map> runResponse = restTemplate.postForEntity(
                    restUrl + "/jars/" + jarId + "/run",
                    runEntity,
                    Map.class
            );

            if (!runResponse.getStatusCode().is2xxSuccessful()) {
                throw new Exception("运行 JAR 文件失败: " + runResponse.getStatusCode());
            }

            // 获取作业 ID
            Map<String, Object> runResult = runResponse.getBody();
            return (String) runResult.get("jobid");
        } catch (Exception e) {
            log.error("提交 Flink 任务失败", e);
            throw new Exception("提交 Flink 任务失败: " + e.getMessage());
        }
    }

    @Override
    public boolean cancelJob(String jobId) throws Exception {
        try {
            // 创建 Flink 客户端
            Configuration configuration = createFlinkConfiguration();
            try (RestClusterClient<StandaloneClusterId> client = new RestClusterClient<>(
                    configuration, StandaloneClusterId.getInstance())) {

                // 取消任务
                CompletableFuture<Acknowledge> cancelFuture = client.cancel(JobID.fromHexString(jobId));
                cancelFuture.get(flinkProperties.getTaskTimeout(), TimeUnit.MILLISECONDS);
                return true;
            }
        } catch (Exception e) {
            log.error("取消 Flink 任务失败", e);
            throw new Exception("取消 Flink 任务失败: " + e.getMessage());
        }
    }

    @Override
    public FlinkJobStatusRespVO getJobStatus(String jobId) throws Exception {
        try {
            Configuration configuration = createFlinkConfiguration();
            try (RestClusterClient<StandaloneClusterId> client = new RestClusterClient<>(
                    configuration, StandaloneClusterId.getInstance())) {

                CompletableFuture<JobStatus> statusFuture = client.getJobStatus(JobID.fromHexString(jobId));
                JobStatus jobStatus = statusFuture.get(flinkProperties.getTaskTimeout(), TimeUnit.MILLISECONDS);

                FlinkJobStatusRespVO respVO = new FlinkJobStatusRespVO();
                respVO.setJobId(jobId);
                respVO.setStatus(jobStatus.name());

                // 修改为直接使用枚举值判断
                respVO.setRunning(jobStatus == JobStatus.RUNNING || jobStatus == JobStatus.CREATED ||
                        jobStatus == JobStatus.RESTARTING || jobStatus == JobStatus.RECONCILING);
                respVO.setFinished(jobStatus == JobStatus.FINISHED || jobStatus == JobStatus.FAILED ||
                        jobStatus == JobStatus.CANCELED);
                return respVO;
            }
        } catch (Exception e) {
            log.error("获取 Flink 任务状态失败", e);
            throw new Exception("获取 Flink 任务状态失败: " + e.getMessage());
        }
    }

    @Override
    public FlinkJobInfoRespVO getJobInfo(String jobId) throws Exception {
        try {
            // 创建 Flink 配置
            Configuration configuration = createFlinkConfiguration();

            // 获取 Flink REST API 地址
            String restUrl = flinkProperties.getJobManagerUrl();
            if (!restUrl.startsWith("http://")) {
                restUrl = "http://" + restUrl;
            }

            // 使用 Spring RestTemplate 直接调用 Flink REST API
            RestTemplate restTemplate = new RestTemplate();
            String jobDetailsUrl = restUrl + "/jobs/" + jobId;

            // 调用 REST API 获取任务详情
            Map<String, Object> jobDetails = restTemplate.getForObject(jobDetailsUrl, Map.class);

            // 构建返回值
            FlinkJobInfoRespVO respVO = new FlinkJobInfoRespVO();
            respVO.setJobId(jobId);
            respVO.setJobName((String) jobDetails.get("name"));
            respVO.setStatus((String) jobDetails.get("state"));
            respVO.setStartTime(((Number) jobDetails.get("start-time")).longValue());

            Number endTime = (Number) jobDetails.get("end-time");
            if (endTime != null) {
                respVO.setEndTime(endTime.longValue());
            }

            Number duration = (Number) jobDetails.get("duration");
            if (duration != null) {
                respVO.setDuration(duration.longValue());
            }

            // 获取任务数量和并行度
            List<Map<String, Object>> vertices = (List<Map<String, Object>>) jobDetails.get("vertices");
            respVO.setTasks(vertices.size());

            int maxParallelism = 1;
            for (Map<String, Object> vertex : vertices) {
                int parallelism = ((Number) vertex.get("parallelism")).intValue();
                if (parallelism > maxParallelism) {
                    maxParallelism = parallelism;
                }
            }
            respVO.setParallelism(maxParallelism);

            return respVO;
        } catch (Exception e) {
            log.error("获取 Flink 任务信息失败", e);
            throw new Exception("获取 Flink 任务信息失败: " + e.getMessage());
        }
    }

    @Override
    public List<FlinkJobInfoRespVO> listRunningJobs() throws Exception {
        try {
            // 创建 Flink 客户端
            Configuration configuration = createFlinkConfiguration();
            try (RestClusterClient<StandaloneClusterId> client = new RestClusterClient<>(
                    configuration, StandaloneClusterId.getInstance())) {

                // 获取所有任务
                CompletableFuture<Collection<JobStatusMessage>> jobsFuture = client.listJobs();
                List<JobStatusMessage> jobs = new ArrayList<>(jobsFuture.get(flinkProperties.getTaskTimeout(), TimeUnit.MILLISECONDS));

                // 过滤出运行中的任务 - 修改为使用枚举值判断
                return jobs.stream()
                        .filter(job -> {
                            JobStatus state = job.getJobState();
                            return state == JobStatus.RUNNING || state == JobStatus.CREATED ||
                                    state == JobStatus.RESTARTING || state == JobStatus.RECONCILING;
                        })
                        .map(job -> {
                            FlinkJobInfoRespVO respVO = new FlinkJobInfoRespVO();
                            respVO.setJobId(job.getJobId().toString());
                            respVO.setJobName(job.getJobName());
                            respVO.setStatus(job.getJobState().name());
                            respVO.setStartTime(job.getStartTime());
                            return respVO;
                        })
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("获取运行中的 Flink 任务列表失败", e);
            throw new Exception("获取运行中的 Flink 任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建 Flink 配置
     */
    private Configuration createFlinkConfiguration() {
        Configuration configuration = new Configuration();

        // 解析 JobManager URL
        String jobManagerUrl = flinkProperties.getJobManagerUrl();
        if (jobManagerUrl.startsWith("http://")) {
            jobManagerUrl = jobManagerUrl.substring(7);
        }

        String[] hostAndPort = jobManagerUrl.split(":");
        String host = hostAndPort[0];
        int port = hostAndPort.length > 1 ? Integer.parseInt(hostAndPort[1]) : 8081;

        configuration.setString(JobManagerOptions.ADDRESS, host);
        configuration.setInteger(RestOptions.PORT, port);
        configuration.setString(RestOptions.BIND_ADDRESS, host);

        return configuration;
    }
}