package com.jiangYang.cloud.module.promotion.controller.admin.kefu;

import com.jiangYang.cloud.framework.common.pojo.CommonResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.member.api.user.MemberUserApi;
import com.jiangYang.cloud.module.member.api.user.dto.MemberUserRespDTO;
import com.jiangYang.cloud.module.promotion.controller.admin.kefu.vo.conversation.KeFuConversationRespVO;
import com.jiangYang.cloud.module.promotion.controller.admin.kefu.vo.conversation.KeFuConversationUpdatePinnedReqVO;
import com.jiangYang.cloud.module.promotion.dal.dataobject.kefu.KeFuConversationDO;
import com.jiangYang.cloud.module.promotion.service.kefu.KeFuConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.jiangYang.cloud.framework.common.pojo.CommonResult.success;
import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertSet;
import static com.jiangYang.cloud.framework.common.util.collection.MapUtils.findAndThen;

@Tag(name = "管理后台 - 客服会话")
@RestController
@RequestMapping("/promotion/kefu-conversation")
@Validated
public class KeFuConversationController {

    @Resource
    private KeFuConversationService conversationService;
    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/get")
    @Operation(summary = "获得客服会话")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:kefu-conversation:query')")
    public CommonResult<KeFuConversationRespVO> getConversation(@RequestParam("id") Long id) {
        KeFuConversationDO conversation = conversationService.getConversation(id);
        if (conversation == null) {
            return success(null);
        }

        // 拼接数据
        KeFuConversationRespVO result = BeanUtils.toBean(conversation, KeFuConversationRespVO.class);
        MemberUserRespDTO memberUser = memberUserApi.getUser(conversation.getUserId()).getCheckedData();
        if (memberUser != null) {
            result.setUserAvatar(memberUser.getAvatar()).setUserNickname(memberUser.getNickname());
        }
        return success(result);
    }

    @PutMapping("/update-conversation-pinned")
    @Operation(summary = "置顶/取消置顶客服会话")
    @PreAuthorize("@ss.hasPermission('promotion:kefu-conversation:update')")
    public CommonResult<Boolean> updateConversationPinned(@Valid @RequestBody KeFuConversationUpdatePinnedReqVO updateReqVO) {
        conversationService.updateConversationPinnedByAdmin(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客服会话")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:kefu-conversation:delete')")
    public CommonResult<Boolean> deleteConversation(@RequestParam("id") Long id) {
        conversationService.deleteKefuConversation(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得客服会话列表")
    @PreAuthorize("@ss.hasPermission('promotion:kefu-conversation:query')")
    public CommonResult<List<KeFuConversationRespVO>> getConversationList() {
        // 查询会话列表
        List<KeFuConversationRespVO> respList = BeanUtils.toBean(conversationService.getKefuConversationList(),
                KeFuConversationRespVO.class);

        // 拼接数据
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(respList, KeFuConversationRespVO::getUserId));
        respList.forEach(item-> findAndThen(userMap, item.getUserId(),
                memberUser-> item.setUserAvatar(memberUser.getAvatar()).setUserNickname(memberUser.getNickname())));
        return success(respList);
    }

}