package com.flink.jiangyang.module.flink.pojo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Flink 任务 StarRocks 数据加载记录
 */
@Data
@TableName(value = "flink_job_load", autoResultMap = true)
public class FlinkJobLoadDO {
    /**
     * 记录 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Flink 任务 ID
     */
    private String jobId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 写入记录数
     */
    private Long recordCount;

    /**
     * 状态：SUCCESS-成功，FAILED-失败
     */
    private String status;

    /**
     * 完成时间
     */
    private Date completionTime;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 元数据信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> metadata;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
