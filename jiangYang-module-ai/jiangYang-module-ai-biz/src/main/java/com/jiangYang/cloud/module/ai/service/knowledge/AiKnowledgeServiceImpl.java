package com.jiangYang.cloud.module.ai.service.knowledge;

import cn.hutool.core.util.ObjUtil;
import com.jiangYang.cloud.framework.common.enums.CommonStatusEnum;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.framework.common.util.object.BeanUtils;
import com.jiangYang.cloud.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgePageReqVO;
import com.jiangYang.cloud.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgeSaveReqVO;
import com.jiangYang.cloud.module.ai.dal.dataobject.knowledge.AiKnowledgeDO;
import com.jiangYang.cloud.module.ai.dal.dataobject.knowledge.AiKnowledgeDocumentDO;
import com.jiangYang.cloud.module.ai.dal.dataobject.model.AiModelDO;
import com.jiangYang.cloud.module.ai.dal.mysql.knowledge.AiKnowledgeMapper;
import com.jiangYang.cloud.module.ai.service.model.AiModelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jiangYang.cloud.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.jiangYang.cloud.module.ai.enums.ErrorCodeConstants.KNOWLEDGE_NOT_EXISTS;

/**
 * AI 知识库-基础信息 Service 实现类
 *
 * @author xiaoxin
 */
@Service
@Slf4j
public class AiKnowledgeServiceImpl implements AiKnowledgeService {

    @Resource
    private AiKnowledgeMapper knowledgeMapper;

    @Resource
    private AiModelService modelService;
    @Resource
    private AiKnowledgeSegmentService knowledgeSegmentService;

    @Override
    public Long createKnowledge(AiKnowledgeSaveReqVO createReqVO) {
        // 1. 校验模型配置
        AiModelDO model = modelService.validateModel(createReqVO.getEmbeddingModelId());

        // 2. 插入知识库
        AiKnowledgeDO knowledge = BeanUtils.toBean(createReqVO, AiKnowledgeDO.class)
                .setEmbeddingModel(model.getModel());
        knowledgeMapper.insert(knowledge);
        return knowledge.getId();
    }

    @Override
    public void updateKnowledge(AiKnowledgeSaveReqVO updateReqVO) {
        // 1.1 校验知识库存在
        AiKnowledgeDO oldKnowledge = validateKnowledgeExists(updateReqVO.getId());
        // 1.2 校验模型配置
        AiModelDO model = modelService.validateModel(updateReqVO.getEmbeddingModelId());

        // 2. 更新知识库
        AiKnowledgeDO updateObj = BeanUtils.toBean(updateReqVO, AiKnowledgeDO.class)
                .setEmbeddingModel(model.getModel());
        knowledgeMapper.updateById(updateObj);

        // 3. 如果模型变化，需要 reindex 所有的文档
        if (ObjUtil.notEqual(oldKnowledge.getEmbeddingModelId(), updateReqVO.getEmbeddingModelId())) {
            knowledgeSegmentService.reindexByKnowledgeIdAsync(updateReqVO.getId());
        }
    }

    @Override
    public AiKnowledgeDO getKnowledge(Long id) {
        return knowledgeMapper.selectById(id);
    }

    @Override
    public AiKnowledgeDO validateKnowledgeExists(Long id) {
        AiKnowledgeDO knowledgeBase = knowledgeMapper.selectById(id);
        if (knowledgeBase == null) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
        return knowledgeBase;
    }

    @Override
    public PageResult<AiKnowledgeDO> getKnowledgePage(AiKnowledgePageReqVO pageReqVO) {
        return knowledgeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AiKnowledgeDO> getKnowledgeSimpleListByStatus(Integer status) {
        return knowledgeMapper.selectListByStatus(status);
    }

}
