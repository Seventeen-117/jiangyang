package com.jiangYang.cloud.module.erp.controller.admin.product;

import com.jiangYang.cloud.framework.apilog.core.annotation.ApiAccessLog;
import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.pojo.PageParam;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.framework.excel.core.util.ExcelUtils;
import com.jiangYang.cloud.module.erp.controller.admin.product.vo.unit.ErpProductUnitPageReqVO;
import com.jiangYang.cloud.module.erp.controller.admin.product.vo.unit.ErpProductUnitRespVO;
import com.jiangYang.cloud.module.erp.controller.admin.product.vo.unit.ErpProductUnitSaveReqVO;
import com.jiangYang.cloud.module.erp.dal.dataobject.product.ErpProductUnitDO;
import com.jiangYang.cloud.module.erp.service.product.ErpProductUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.jiangYang.cloud.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;
import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - ERP 产品单位")
@RestController
@RequestMapping("/erp/product-unit")
@Validated
public class ErpProductUnitController {

    @Resource
    private ErpProductUnitService productUnitService;

    @PostMapping("/create")
    @Operation(summary = "创建产品单位")
    @PreAuthorize("@ss.hasPermission('erp:product-unit:create')")
    public CommonResult<Long> createProductUnit(@Valid @RequestBody ErpProductUnitSaveReqVO createReqVO) {
        return success(productUnitService.createProductUnit(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品单位")
    @PreAuthorize("@ss.hasPermission('erp:product-unit:update')")
    public CommonResult<Boolean> updateProductUnit(@Valid @RequestBody ErpProductUnitSaveReqVO updateReqVO) {
        productUnitService.updateProductUnit(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品单位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('erp:product-unit:delete')")
    public CommonResult<Boolean> deleteProductUnit(@RequestParam("id") Long id) {
        productUnitService.deleteProductUnit(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品单位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:product-unit:query')")
    public CommonResult<ErpProductUnitRespVO> getProductUnit(@RequestParam("id") Long id) {
        ErpProductUnitDO productUnit = productUnitService.getProductUnit(id);
        return success(BeanUtils.toBean(productUnit, ErpProductUnitRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品单位分页")
    @PreAuthorize("@ss.hasPermission('erp:product-unit:query')")
    public CommonResult<PageResult<ErpProductUnitRespVO>> getProductUnitPage(@Valid ErpProductUnitPageReqVO pageReqVO) {
        PageResult<ErpProductUnitDO> pageResult = productUnitService.getProductUnitPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ErpProductUnitRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得产品单位精简列表", description = "只包含被开启的单位，主要用于前端的下拉选项")
    public CommonResult<List<ErpProductUnitRespVO>> getProductUnitSimpleList() {
        List<ErpProductUnitDO> list = productUnitService.getProductUnitListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, unit -> new ErpProductUnitRespVO().setId(unit.getId()).setName(unit.getName())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品单位 Excel")
    @PreAuthorize("@ss.hasPermission('erp:product-unit:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductUnitExcel(@Valid ErpProductUnitPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErpProductUnitDO> list = productUnitService.getProductUnitPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "产品单位.xls", "数据", ErpProductUnitRespVO.class,
                        BeanUtils.toBean(list, ErpProductUnitRespVO.class));
    }

}