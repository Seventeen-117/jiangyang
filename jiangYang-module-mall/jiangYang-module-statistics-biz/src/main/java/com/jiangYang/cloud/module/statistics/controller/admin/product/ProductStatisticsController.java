package com.jiangYang.cloud.module.statistics.controller.admin.product;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.pojo.SortablePageParam;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.framework.excel.core.util.ExcelUtils;
import com.jiangYang.cloud.module.product.api.spu.ProductSpuApi;
import com.jiangYang.cloud.module.product.api.spu.dto.ProductSpuRespDTO;
import com.jiangYang.cloud.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import com.jiangYang.cloud.module.statistics.controller.admin.product.vo.ProductStatisticsReqVO;
import com.jiangYang.cloud.module.statistics.controller.admin.product.vo.ProductStatisticsRespVO;
import com.jiangYang.cloud.module.statistics.dal.dataobject.product.ProductStatisticsDO;
import com.jiangYang.cloud.module.statistics.service.product.ProductStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;
import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertMap;
import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 商品统计")
@RestController
@RequestMapping("/statistics/product")
@Validated
public class ProductStatisticsController {

    @Resource
    private ProductStatisticsService productStatisticsService;

    @Resource
    private ProductSpuApi productSpuApi;

    @GetMapping("/analyse")
    @Operation(summary = "获得商品统计分析")
    @PreAuthorize("@ss.hasPermission('statistics:product:query')")
    public CommonResult<DataComparisonRespVO<ProductStatisticsRespVO>> getProductStatisticsAnalyse(ProductStatisticsReqVO reqVO) {
        return success(productStatisticsService.getProductStatisticsAnalyse(reqVO));
    }

    @GetMapping("/list")
    @Operation(summary = "获得商品统计明细（日期维度）")
    @PreAuthorize("@ss.hasPermission('statistics:product:query')")
    public CommonResult<List<ProductStatisticsRespVO>> getProductStatisticsList(ProductStatisticsReqVO reqVO) {
        List<ProductStatisticsDO> list = productStatisticsService.getProductStatisticsList(reqVO);
        return success(BeanUtils.toBean(list, ProductStatisticsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出获得商品统计明细 Excel（日期维度）")
    @PreAuthorize("@ss.hasPermission('statistics:product:export')")
    public void exportProductStatisticsExcel(ProductStatisticsReqVO reqVO, HttpServletResponse response) throws IOException {
        List<ProductStatisticsDO> list = productStatisticsService.getProductStatisticsList(reqVO);
        // 导出 Excel
        List<ProductStatisticsRespVO> voList = BeanUtils.toBean(list, ProductStatisticsRespVO.class);
        ExcelUtils.write(response, "商品状况.xls", "数据", ProductStatisticsRespVO.class, voList);
    }

    @GetMapping("/rank-page")
    @Operation(summary = "获得商品统计排行榜分页（商品维度）")
    @PreAuthorize("@ss.hasPermission('statistics:product:query')")
    public CommonResult<PageResult<ProductStatisticsRespVO>> getProductStatisticsRankPage(@Valid ProductStatisticsReqVO reqVO,
                                                                                          @Valid SortablePageParam pageParam) {
        PageResult<ProductStatisticsDO> pageResult = productStatisticsService.getProductStatisticsRankPage(reqVO, pageParam);
        // 处理商品信息
        Set<Long> spuIds = convertSet(pageResult.getList(), ProductStatisticsDO::getSpuId);
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(productSpuApi.getSpuList(spuIds).getCheckedData(), ProductSpuRespDTO::getId);
        return success(BeanUtils.toBean(pageResult, ProductStatisticsRespVO.class,
                item -> Optional.ofNullable(spuMap.get(item.getSpuId()))
                        .ifPresent(spu -> item.setName(spu.getName()).setPicUrl(spu.getPicUrl()))));
    }

}