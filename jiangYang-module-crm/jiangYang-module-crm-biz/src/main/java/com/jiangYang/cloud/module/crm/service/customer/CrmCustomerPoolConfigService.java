package com.jiangYang.cloud.module.crm.service.customer;

import com.jiangYang.cloud.module.crm.controller.admin.customer.vo.poolconfig.CrmCustomerPoolConfigSaveReqVO;
import com.jiangYang.cloud.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;

import javax.validation.Valid;

/**
 * 客户公海配置 Service 接口
 *
 * @author Wanwan
 */
public interface CrmCustomerPoolConfigService {

    /**
     * 获得客户公海配置
     *
     * @return 客户公海配置
     */
    CrmCustomerPoolConfigDO getCustomerPoolConfig();

    /**
     * 保存客户公海配置
     *
     * @param saveReqVO 更新信息
     */
    void saveCustomerPoolConfig(@Valid CrmCustomerPoolConfigSaveReqVO saveReqVO);

}
