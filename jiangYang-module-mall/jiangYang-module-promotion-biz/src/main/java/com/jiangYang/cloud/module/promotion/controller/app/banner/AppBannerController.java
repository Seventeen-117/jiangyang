package com.jiangYang.cloud.module.promotion.controller.app.banner;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.module.promotion.controller.app.banner.vo.AppBannerRespVO;
import com.jiangYang.cloud.module.promotion.convert.banner.BannerConvert;
import com.jiangYang.cloud.module.promotion.dal.dataobject.banner.BannerDO;
import com.jiangYang.cloud.module.promotion.service.banner.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;

@RestController
@RequestMapping("/promotion/banner")
@Tag(name = "用户 APP - 首页 Banner")
@Validated
public class AppBannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/list")
    @Operation(summary = "获得 banner 列表")
    @Parameter(name = "position", description = "Banner position", example = "1")
    @PermitAll
    public CommonResult<List<AppBannerRespVO>> getBannerList(@RequestParam("position") Integer position) {
        List<BannerDO> bannerList = bannerService.getBannerListByPosition(position);
        return success(BannerConvert.INSTANCE.convertList01(bannerList));
    }

    @PutMapping("/add-browse-count")
    @Operation(summary = "增加 Banner 点击量")
    @Parameter(name = "id", description = "Banner 编号", example = "1024")
    @PermitAll
    public CommonResult<Boolean> addBrowseCount(@RequestParam("id") Long id) {
        bannerService.addBannerBrowseCount(id);
        return success(true);
    }

}
