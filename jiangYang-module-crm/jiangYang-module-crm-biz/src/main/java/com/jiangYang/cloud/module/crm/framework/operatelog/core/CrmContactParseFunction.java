package com.jiangYang.cloud.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import com.jiangYang.cloud.module.crm.dal.dataobject.contact.CrmContactDO;
import com.jiangYang.cloud.module.crm.service.contact.CrmContactService;
import com.mzt.logapi.service.IParseFunction;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * CRM 联系人的 {@link IParseFunction} 实现类
 *
 * @author HUIHUI
 */
@Component
@Slf4j
public class CrmContactParseFunction implements IParseFunction {

    public static final String NAME = "getContactById";

    @Resource
    private CrmContactService contactService;

    @Override
    public boolean executeBefore() {
        return true; // 先转换值后对比
    }

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }
        CrmContactDO contactDO = contactService.getContact(Long.parseLong(value.toString()));
        return contactDO == null ? "" : contactDO.getName();
    }

}
