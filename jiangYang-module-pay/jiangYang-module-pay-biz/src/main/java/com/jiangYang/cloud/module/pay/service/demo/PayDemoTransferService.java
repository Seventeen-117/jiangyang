package com.jiangYang.cloud.module.pay.service.demo;

import com.jiangYang.cloud.framework.common.pojo.PageParam;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.jiangYang.cloud.module.pay.dal.dataobject.demo.PayDemoTransferDO;

import javax.validation.Valid;

/**
 * 示例转账业务 Service 接口
 *
 * @author jason
 */
public interface PayDemoTransferService {

    /**
     * 创建转账业务示例订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDemoTransfer(@Valid PayDemoTransferCreateReqVO createReqVO);

    /**
     * 获得转账业务示例订单分页
     *
     * @param pageVO 分页查询参数
     */
    PageResult<PayDemoTransferDO> getDemoTransferPage(PageParam pageVO);

    /**
     * 更新转账业务示例订单的转账状态
     *
     * @param id 编号
     * @param payTransferId 转账单编号
     */
    void updateDemoTransferStatus(Long id, Long payTransferId);
}
