package com.jiangYang.cloud.module.bpm.framework.flowable.core.candidate.strategy.user;

import com.jiangYang.cloud.framework.common.util.string.StrUtils;
import com.jiangYang.cloud.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import com.jiangYang.cloud.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import com.jiangYang.cloud.module.system.api.dept.PostApi;
import com.jiangYang.cloud.module.system.api.user.AdminUserApi;
import com.jiangYang.cloud.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static com.jiangYang.cloud.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 岗位 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidatePostStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private PostApi postApi;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.POST;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> postIds = StrUtils.splitToLongSet(param);
        postApi.validPostList(postIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> postIds = StrUtils.splitToLongSet(param);
        List<AdminUserRespDTO> users = adminUserApi.getUserListByPostIds(postIds).getCheckedData();
        return convertSet(users, AdminUserRespDTO::getId);
    }

}