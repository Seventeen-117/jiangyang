package com.jiangYang.cloud.module.erp.service.stock;

import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.module.erp.controller.admin.stock.vo.in.ErpStockInPageReqVO;
import com.jiangYang.cloud.module.erp.controller.admin.stock.vo.in.ErpStockInSaveReqVO;
import com.jiangYang.cloud.module.erp.dal.dataobject.stock.ErpStockInDO;
import com.jiangYang.cloud.module.erp.dal.dataobject.stock.ErpStockInItemDO;
import javax.validation.Valid;

import java.util.Collection;
import java.util.List;

/**
 * ERP 其它入库单 Service 接口
 *
 * @author 芋道源码
 */
public interface ErpStockInService {

    /**
     * 创建其它入库单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStockIn(@Valid ErpStockInSaveReqVO createReqVO);

    /**
     * 更新其它入库单
     *
     * @param updateReqVO 更新信息
     */
    void updateStockIn(@Valid ErpStockInSaveReqVO updateReqVO);

    /**
     * 更新其它入库单的状态
     *
     * @param id 编号
     * @param status 状态
     */
    void updateStockInStatus(Long id, Integer status);

    /**
     * 删除其它入库单
     *
     * @param ids 编号数组
     */
    void deleteStockIn(List<Long> ids);

    /**
     * 获得其它入库单
     *
     * @param id 编号
     * @return 其它入库单
     */
    ErpStockInDO getStockIn(Long id);

    /**
     * 获得其它入库单分页
     *
     * @param pageReqVO 分页查询
     * @return 其它入库单分页
     */
    PageResult<ErpStockInDO> getStockInPage(ErpStockInPageReqVO pageReqVO);

    // ==================== 入库项 ====================

    /**
     * 获得其它入库单项列表
     *
     * @param inId 入库编号
     * @return 其它入库单项列表
     */
    List<ErpStockInItemDO> getStockInItemListByInId(Long inId);

    /**
     * 获得其它入库单项 List
     *
     * @param inIds 入库编号数组
     * @return 其它入库单项 List
     */
    List<ErpStockInItemDO> getStockInItemListByInIds(Collection<Long> inIds);

}