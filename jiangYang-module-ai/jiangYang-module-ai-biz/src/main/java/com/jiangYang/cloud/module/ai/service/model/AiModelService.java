package com.jiangYang.cloud.module.ai.service.model;

import com.jiangYang.cloud.framework.ai.core.model.midjourney.api.MidjourneyApi;
import com.jiangYang.cloud.framework.ai.core.model.suno.api.SunoApi;
import com.jiangYang.cloud.framework.common.pojo.PageResult;
import com.jiangYang.cloud.module.ai.controller.admin.model.vo.model.AiModelPageReqVO;
import com.jiangYang.cloud.module.ai.controller.admin.model.vo.model.AiModelSaveReqVO;
import com.jiangYang.cloud.module.ai.dal.dataobject.model.AiModelDO;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.vectorstore.VectorStore;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * AI 模型 Service 接口
 *
 * @author fansili
 * @since 2024/4/24 19:42
 */
public interface AiModelService {

    /**
     * 创建模型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createModel(@Valid AiModelSaveReqVO createReqVO);

    /**
     * 更新模型
     *
     * @param updateReqVO 更新信息
     */
    void updateModel(@Valid AiModelSaveReqVO updateReqVO);

    /**
     * 删除模型
     *
     * @param id 编号
     */
    void deleteModel(Long id);

    /**
     * 获得模型
     *
     * @param id 编号
     * @return 模型
     */
    AiModelDO getModel(Long id);

    /**
     * 获得默认的模型
     *
     * 如果获取不到，则抛出 {@link com.jiangYang.cloud.framework.common.exception.ServiceException} 业务异常
     *
     * @return 模型
     */
    AiModelDO getRequiredDefaultModel(Integer type);

    /**
     * 获得模型分页
     *
     * @param pageReqVO 分页查询
     * @return 模型分页
     */
    PageResult<AiModelDO> getModelPage(AiModelPageReqVO pageReqVO);

    /**
     * 校验模型是否可使用
     *
     * @param id 编号
     * @return 模型
     */
    AiModelDO validateModel(Long id);

    /**
     * 获得模型列表
     *
     * @param status 状态
     * @param type 类型
     * @param platform 平台，允许空
     * @return 模型列表
     */
    List<AiModelDO> getModelListByStatusAndType(Integer status, Integer type,
                                                @Nullable String platform);

    // ========== 与 Spring AI 集成 ==========

    /**
     * 获得 ChatModel 对象
     *
     * @param id 编号
     * @return ChatModel 对象
     */
    ChatModel getChatModel(Long id);

    /**
     * 获得 ImageModel 对象
     *
     * @param id 编号
     * @return ImageModel 对象
     */
    ImageModel getImageModel(Long id);

    /**
     * 获得 MidjourneyApi 对象
     *
     * @param id 编号
     * @return MidjourneyApi 对象
     */
    MidjourneyApi getMidjourneyApi(Long id);

    /**
     * 获得 SunoApi 对象
     *
     * @return SunoApi 对象
     */
    SunoApi getSunoApi();

    /**
     * 获得 VectorStore 对象
     *
     * @param id 编号
     * @param metadataFields 元数据的定义
     * @return VectorStore 对象
     */
    VectorStore getOrCreateVectorStore(Long id, Map<String, Class<?>> metadataFields);

}
