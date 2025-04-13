package com.jiangYang.cloud.module.system.dal.mysql.qcr;

import com.jiangYang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangYang.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.jiangYang.cloud.module.system.controller.admin.auth.vo.AuthQrCodeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthQrCodeMapper extends BaseMapperX<AuthQrCodeDO> {

    default int updateByIdAndStatus(String id, Integer status, AuthQrCodeDO updateObj) {
        return update(updateObj, new LambdaQueryWrapperX<AuthQrCodeDO>()
                .eq(AuthQrCodeDO::getId, id)
                .eq(AuthQrCodeDO::getStatus, status));
    }
}