package com.jiangYang.cloud.module.iot.controller.admin.device;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.iot.controller.admin.device.vo.data.IotDeviceLogPageReqVO;
import com.jiangYang.cloud.module.iot.controller.admin.device.vo.data.IotDeviceLogRespVO;
import com.jiangYang.cloud.module.iot.dal.dataobject.device.IotDeviceLogDO;
import com.jiangYang.cloud.module.iot.service.device.data.IotDeviceLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - IoT 设备日志")
@RestController
@RequestMapping("/iot/device/log")
@Validated
public class IotDeviceLogController {

    @Resource
    private IotDeviceLogService deviceLogService;

    @GetMapping("/page")
    @Operation(summary = "获得设备日志分页")
    @PreAuthorize("@ss.hasPermission('iot:device:log-query')")
    public CommonResult<PageResult<IotDeviceLogRespVO>> getDeviceLogPage(@Valid IotDeviceLogPageReqVO pageReqVO) {
        PageResult<IotDeviceLogDO> pageResult = deviceLogService.getDeviceLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, IotDeviceLogRespVO.class));
    }

}