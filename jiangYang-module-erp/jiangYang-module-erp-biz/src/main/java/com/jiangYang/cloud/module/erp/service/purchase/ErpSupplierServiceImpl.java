package com.jiangYang.cloud.module.erp.service.purchase;

import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierPageReqVO;
import com.jiangYang.cloud.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierSaveReqVO;
import com.jiangYang.cloud.module.erp.dal.dataobject.purchase.ErpSupplierDO;
import com.jiangYang.cloud.module.erp.dal.mysql.purchase.ErpSupplierMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

import static com.jiangYang.cloud.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jiangYang.cloud.module.erp.enums.ErrorCodeConstants.SUPPLIER_NOT_ENABLE;
import static com.jiangYang.cloud.module.erp.enums.ErrorCodeConstants.SUPPLIER_NOT_EXISTS;

/**
 * ERP 供应商 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ErpSupplierServiceImpl implements ErpSupplierService {

    @Resource
    private ErpSupplierMapper supplierMapper;

    @Override
    public Long createSupplier(ErpSupplierSaveReqVO createReqVO) {
        ErpSupplierDO supplier = BeanUtils.toBean(createReqVO, ErpSupplierDO.class);
        supplierMapper.insert(supplier);
        return supplier.getId();
    }

    @Override
    public void updateSupplier(ErpSupplierSaveReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        // 更新
        ErpSupplierDO updateObj = BeanUtils.toBean(updateReqVO, ErpSupplierDO.class);
        supplierMapper.updateById(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierMapper.deleteById(id);
    }

    private void validateSupplierExists(Long id) {
        if (supplierMapper.selectById(id) == null) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
    }

    @Override
    public ErpSupplierDO getSupplier(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public ErpSupplierDO validateSupplier(Long id) {
        ErpSupplierDO supplier = supplierMapper.selectById(id);
        if (supplier == null) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(supplier.getStatus())) {
            throw exception(SUPPLIER_NOT_ENABLE, supplier.getName());
        }
        return supplier;
    }

    @Override
    public List<ErpSupplierDO> getSupplierList(Collection<Long> ids) {
        return supplierMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ErpSupplierDO> getSupplierPage(ErpSupplierPageReqVO pageReqVO) {
        return supplierMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ErpSupplierDO> getSupplierListByStatus(Integer status) {
        return supplierMapper.selectListByStatus(status);
    }

}