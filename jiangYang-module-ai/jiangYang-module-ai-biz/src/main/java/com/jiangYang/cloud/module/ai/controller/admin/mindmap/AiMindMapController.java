package com.jiangYang.cloud.module.ai.controller.admin.mindmap;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.ai.controller.admin.mindmap.vo.AiMindMapGenerateReqVO;
import com.jiangYang.cloud.module.ai.controller.admin.mindmap.vo.AiMindMapPageReqVO;
import com.jiangYang.cloud.module.ai.controller.admin.mindmap.vo.AiMindMapRespVO;
import com.jiangYang.cloud.module.ai.dal.dataobject.mindmap.AiMindMapDO;
import com.jiangYang.cloud.module.ai.service.mindmap.AiMindMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;
import static com.jiangYang.cloud.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - AI 思维导图")
@RestController
@RequestMapping("/ai/mind-map")
public class AiMindMapController {

    @Resource
    private AiMindMapService mindMapService;

    @PostMapping(value = "/generate-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "导图生成（流式）", description = "流式返回，响应较快")
    public Flux<CommonResult<String>> generateMindMap(@RequestBody @Valid AiMindMapGenerateReqVO generateReqVO) {
        return mindMapService.generateMindMap(generateReqVO, getLoginUserId());
    }

    // ================ 导图管理 ================

    @DeleteMapping("/delete")
    @Operation(summary = "删除思维导图")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:mind-map:delete')")
    public CommonResult<Boolean> deleteMindMap(@RequestParam("id") Long id) {
        mindMapService.deleteMindMap(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得思维导图分页")
    @PreAuthorize("@ss.hasPermission('ai:mind-map:query')")
    public CommonResult<PageResult<AiMindMapRespVO>> getMindMapPage(@Valid AiMindMapPageReqVO pageReqVO) {
        PageResult<AiMindMapDO> pageResult = mindMapService.getMindMapPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiMindMapRespVO.class));
    }

}
