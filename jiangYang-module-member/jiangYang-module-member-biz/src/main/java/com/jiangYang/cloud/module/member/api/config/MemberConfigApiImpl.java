package com.jiangYang.cloud.module.member.api.config;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.module.member.api.config.dto.MemberConfigRespDTO;
import com.jiangYang.cloud.module.member.convert.config.MemberConfigConvert;
import com.jiangYang.cloud.module.member.service.config.MemberConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

/**
 * 用户配置 API 实现类
 *
 * @author owen
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class MemberConfigApiImpl implements MemberConfigApi {

    @Resource
    private MemberConfigService memberConfigService;

    @Override
    public CommonResult<MemberConfigRespDTO> getConfig() {
        return success(MemberConfigConvert.INSTANCE.convert01(memberConfigService.getConfig()));
    }

}
