package com.jiangYang.cloud.module.member.controller.app.level;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.module.member.controller.app.level.vo.level.AppMemberLevelRespVO;
import com.jiangYang.cloud.module.member.convert.level.MemberLevelConvert;
import com.jiangYang.cloud.module.member.dal.dataobject.level.MemberLevelDO;
import com.jiangYang.cloud.module.member.service.level.MemberLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 会员等级")
@RestController
@RequestMapping("/member/level")
@Validated
public class AppMemberLevelController {

    @Resource
    private MemberLevelService levelService;

    @GetMapping("/list")
    @Operation(summary = "获得会员等级列表")
    @PermitAll
    public CommonResult<List<AppMemberLevelRespVO>> getLevelList() {
        List<MemberLevelDO> result = levelService.getEnableLevelList();
        return success(MemberLevelConvert.INSTANCE.convertList02(result));
    }

}
