package com.jiangYang.cloud.module.trade.service.delivery;

import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.system.api.user.AdminUserApi;
import com.jiangYang.cloud.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpBindReqVO;
import com.jiangYang.cloud.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStoreCreateReqVO;
import com.jiangYang.cloud.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStorePageReqVO;
import com.jiangYang.cloud.module.trade.controller.admin.delivery.vo.pickup.DeliveryPickUpStoreUpdateReqVO;
import com.jiangYang.cloud.module.trade.convert.delivery.DeliveryPickUpStoreConvert;
import com.jiangYang.cloud.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import com.jiangYang.cloud.module.trade.dal.mysql.delivery.DeliveryPickUpStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.jiangYang.cloud.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jiangYang.cloud.module.trade.enums.ErrorCodeConstants.PICK_UP_STORE_NOT_EXISTS;

/**
 * 自提门店 Service 实现类
 *
 * @author jason
 */
@Service
@Validated
public class DeliveryPickUpStoreServiceImpl implements DeliveryPickUpStoreService {

    @Resource
    private DeliveryPickUpStoreMapper deliveryPickUpStoreMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createDeliveryPickUpStore(DeliveryPickUpStoreCreateReqVO createReqVO) {
        // 插入
        DeliveryPickUpStoreDO deliveryPickUpStore = DeliveryPickUpStoreConvert.INSTANCE.convert(createReqVO);
        deliveryPickUpStoreMapper.insert(deliveryPickUpStore);
        // 返回
        return deliveryPickUpStore.getId();
    }

    @Override
    public void updateDeliveryPickUpStore(DeliveryPickUpStoreUpdateReqVO updateReqVO) {
        // 校验存在
        validateDeliveryPickUpStoreExists(updateReqVO.getId());
        // 更新
        DeliveryPickUpStoreDO updateObj = DeliveryPickUpStoreConvert.INSTANCE.convert(updateReqVO);
        deliveryPickUpStoreMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeliveryPickUpStore(Long id) {
        // 校验存在
        validateDeliveryPickUpStoreExists(id);
        // 删除
        deliveryPickUpStoreMapper.deleteById(id);
    }

    private void validateDeliveryPickUpStoreExists(Long id) {
        DeliveryPickUpStoreDO deliveryPickUpStore = deliveryPickUpStoreMapper.selectById(id);
        if (deliveryPickUpStore == null) {
            throw exception(PICK_UP_STORE_NOT_EXISTS);
        }
    }

    @Override
    public DeliveryPickUpStoreDO getDeliveryPickUpStore(Long id) {
        return deliveryPickUpStoreMapper.selectById(id);
    }

    @Override
    public List<DeliveryPickUpStoreDO> getDeliveryPickUpStoreList(Collection<Long> ids) {
        return deliveryPickUpStoreMapper.selectBatchIds(ids);
    }

    @Override
    public List<DeliveryPickUpStoreDO> getDeliveryPickUpStoreListByStatus(Integer status) {
        return deliveryPickUpStoreMapper.selectListByStatus(status);
    }

    @Override
    public PageResult<DeliveryPickUpStoreDO> getDeliveryPickUpStorePage(DeliveryPickUpStorePageReqVO pageReqVO) {
        return deliveryPickUpStoreMapper.selectPage(pageReqVO);
    }

    @Override
    public void bindDeliveryPickUpStore(DeliveryPickUpBindReqVO bindReqVO) {
        // 1.1 校验门店存在
        validateDeliveryPickUpStoreExists(bindReqVO.getId());
        // 1.2 校验用户存在
        adminUserApi.validateUserList(bindReqVO.getVerifyUserIds()).checkError();

        // 2. 更新
        DeliveryPickUpStoreDO updateObj = BeanUtils.toBean(bindReqVO, DeliveryPickUpStoreDO.class);
        deliveryPickUpStoreMapper.updateById(updateObj);
    }

}
