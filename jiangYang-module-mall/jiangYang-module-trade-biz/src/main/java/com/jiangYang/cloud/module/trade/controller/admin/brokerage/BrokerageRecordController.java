package com.jiangYang.cloud.module.trade.controller.admin.brokerage;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.module.member.api.user.MemberUserApi;
import com.jiangYang.cloud.module.member.api.user.dto.MemberUserRespDTO;
import com.jiangYang.cloud.module.trade.controller.admin.brokerage.vo.record.BrokerageRecordPageReqVO;
import com.jiangYang.cloud.module.trade.controller.admin.brokerage.vo.record.BrokerageRecordRespVO;
import com.jiangYang.cloud.module.trade.convert.brokerage.BrokerageRecordConvert;
import com.jiangYang.cloud.module.trade.dal.dataobject.brokerage.BrokerageRecordDO;
import com.jiangYang.cloud.module.trade.service.brokerage.BrokerageRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;
import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertList;
import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 佣金记录")
@RestController
@RequestMapping("/trade/brokerage-record")
@Validated
public class BrokerageRecordController {

    @Resource
    private BrokerageRecordService brokerageRecordService;

    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/get")
    @Operation(summary = "获得佣金记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-record:query')")
    public CommonResult<BrokerageRecordRespVO> getBrokerageRecord(@RequestParam("id") Long id) {
        BrokerageRecordDO brokerageRecord = brokerageRecordService.getBrokerageRecord(id);
        return success(BrokerageRecordConvert.INSTANCE.convert(brokerageRecord));
    }

    @GetMapping("/page")
    @Operation(summary = "获得佣金记录分页")
    @PreAuthorize("@ss.hasPermission('trade:brokerage-record:query')")
    public CommonResult<PageResult<BrokerageRecordRespVO>> getBrokerageRecordPage(@Valid BrokerageRecordPageReqVO pageVO) {
        PageResult<BrokerageRecordDO> pageResult = brokerageRecordService.getBrokerageRecordPage(pageVO);

        // 查询用户信息
        Set<Long> userIds = convertSet(pageResult.getList(), BrokerageRecordDO::getUserId);
        userIds.addAll(convertList(pageResult.getList(), BrokerageRecordDO::getSourceUserId));
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(userIds);
        // 拼接数据
        return success(BrokerageRecordConvert.INSTANCE.convertPage(pageResult, userMap));
    }

}
