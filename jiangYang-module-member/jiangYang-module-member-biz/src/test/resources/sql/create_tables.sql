CREATE TABLE IF NOT EXISTS `member_user` (
                                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                             `nickname` varchar(30) NOT NULL DEFAULT '' COMMENT '用户昵称',
                                             `name` varchar(30) NULL COMMENT '真实名字',
                                             `sex` tinyint NULL COMMENT '性别',
                                             `birthday` datetime NULL COMMENT '出生日期',
                                             `area_id` int NULL COMMENT '所在地',
                                             `mark` varchar(255) NULL COMMENT '用户备注',
                                             `point` int DEFAULT 0 NULL COMMENT '积分',
                                             `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
                                             `status` tinyint NOT NULL COMMENT '状态',
                                             `mobile` varchar(11) NOT NULL COMMENT '手机号',
                                             `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
                                             `register_ip` varchar(32) NOT NULL COMMENT '注册 IP',
                                             `login_ip` varchar(50) NULL DEFAULT '' COMMENT '最后登录IP',
                                             `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
                                             `tag_ids` varchar(255) NULL DEFAULT NULL COMMENT '用户标签编号列表,以逗号分隔',
                                             `level_id` bigint NULL DEFAULT NULL COMMENT '等级编号',
                                             `experience` bigint NULL DEFAULT NULL COMMENT '经验',
                                             `group_id` bigint NULL DEFAULT NULL COMMENT '用户分组编号',
                                             `creator` varchar(64) NULL DEFAULT '' COMMENT '创建者',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `updater` varchar(64) NULL DEFAULT '' COMMENT '更新者',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                             `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                             `tenant_id` bigint NOT NULL DEFAULT 0,
                                             PRIMARY KEY (`id`)
) COMMENT '会员表';

CREATE TABLE IF NOT EXISTS `member_address` (
                                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                `user_id` bigint(20) NOT NULL,
                                                `name` varchar(10) NOT NULL,
                                                `mobile` varchar(20) NOT NULL,
                                                `area_id` bigint(20) NOT NULL,
                                                `detail_address` varchar(250) NOT NULL,
                                                `default_status` bit NOT NULL,
                                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                `creator` varchar(64) DEFAULT '',
                                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                `deleted` bit NOT NULL DEFAULT b'0',
                                                `updater` varchar(64) DEFAULT '',
                                                PRIMARY KEY (`id`)
) COMMENT '用户收件地址';

CREATE TABLE IF NOT EXISTS `member_tag` (
                                            `id` bigint NOT NULL AUTO_INCREMENT,
                                            `name` varchar(255) NOT NULL,
                                            `creator` varchar(64) DEFAULT '',
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `updater` varchar(64) DEFAULT '',
                                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            `deleted` bit NOT NULL DEFAULT b'0',
                                            `tenant_id` bigint NOT NULL DEFAULT 0,
                                            PRIMARY KEY (`id`)
) COMMENT '会员标签';

CREATE TABLE IF NOT EXISTS `member_level` (
                                              `id`             bigint   NOT NULL AUTO_INCREMENT,
                                              `name`           varchar(255)  NOT NULL,
                                              `experience`     int      NOT NULL,
                                              `level`          int      NOT NULL,
                                              `discount_percent` int      NOT NULL,
                                              `icon`           varchar(255)  NOT NULL,
                                              `background_url` varchar(255)  NOT NULL,
                                              `creator`        varchar(255)  DEFAULT '',
                                              `create_time`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `updater`        varchar(255)  DEFAULT '',
                                              `update_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              `deleted`        boolean  NOT NULL DEFAULT FALSE,
                                              `tenant_id`      bigint   NOT NULL DEFAULT 0,
                                              `status`         tinyint  NOT NULL DEFAULT 0,
                                              PRIMARY KEY (`id`)
) COMMENT '会员等级';

CREATE TABLE IF NOT EXISTS `member_group` (
                                              `id`          bigint   NOT NULL AUTO_INCREMENT,
                                              `name`        varchar(255)  NOT NULL,
                                              `remark`      varchar(255)  NOT NULL,
                                              `status`      tinyint  NOT NULL DEFAULT 0,
                                              `creator`     varchar(255)  DEFAULT '',
                                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `updater`     varchar(255)  DEFAULT '',
                                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              `deleted`     boolean  NOT NULL DEFAULT FALSE,
                                              `tenant_id`   bigint   NOT NULL DEFAULT 0,
                                              PRIMARY KEY (`id`)
) COMMENT '用户分组';
CREATE TABLE IF NOT EXISTS `member_brokerage_record` (
                                                         `id`            int      NOT NULL AUTO_INCREMENT,
                                                         `user_id`       bigint   NOT NULL,
                                                         `biz_id`        varchar(255)  NOT NULL,
                                                         `biz_type`      varchar(255)  NOT NULL,
                                                         `title`         varchar(255)  NOT NULL,
                                                         `price`         int      NOT NULL,
                                                         `total_price`   int      NOT NULL,
                                                         `description`   varchar(255)  NOT NULL,
                                                         `status`        varchar(255)  NOT NULL,
                                                         `frozen_days`   int      NOT NULL,
                                                         `unfreeze_time` varchar(255),
                                                         `creator`       varchar(255)  DEFAULT '',
                                                         `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                         `updater`       varchar(255)  DEFAULT '',
                                                         `update_time`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                         `deleted`       boolean  NOT NULL DEFAULT FALSE,
                                                         `tenant_id`     bigint   NOT NULL DEFAULT 0,
                                                         PRIMARY KEY (`id`)
) COMMENT '佣金记录';
-- 创建会员配置表
-- 创建会员配置表
CREATE TABLE IF NOT EXISTS `member_config` (
    -- 自增主键
                                               `id` bigint NOT NULL AUTO_INCREMENT,
    -- 积分抵扣开关
                                               `point_trade_deduct_enable` boolean NOT NULL DEFAULT false,
    -- 积分抵扣，单位：分，1 积分抵扣多少分
                                               `point_trade_deduct_unit_price` int NOT NULL DEFAULT 0,
    -- 积分抵扣最大值
                                               `point_trade_deduct_max_price` int NOT NULL DEFAULT 0,
    -- 1 元赠送多少分
                                               `point_trade_give_point` int NOT NULL DEFAULT 0,
    -- 创建人
                                               `creator` varchar(255) DEFAULT '',
    -- 更新人
                                               `updater` varchar(255) DEFAULT '',
    -- 创建时间，继承自 BaseDO
                                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除标记，0 表示未删除，1 表示已删除',
                                               `tenant_id` int NOT NULL DEFAULT 0 COMMENT '租户 ID',
    -- 更新时间，继承自 BaseDO
                                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员配置';


CREATE TABLE `member_point_record` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                       `user_id` bigint NOT NULL COMMENT '用户编号',
                                       `biz_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务编码',
                                       `biz_type` tinyint NOT NULL COMMENT '业务类型',
                                       `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '积分标题',
                                       `description` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '积分描述',
                                       `point` int NOT NULL COMMENT '积分',
                                       `total_point` int NOT NULL COMMENT '变动后的积分',
                                       `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除标记，0 表示未删除，1 表示已删除',
                                       `tenant_id` int NOT NULL DEFAULT 0 COMMENT '租户 ID',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `index_userId` (`user_id`) USING BTREE,
                                       KEY `index_title` (`title`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户积分记录';

CREATE TABLE `member_sign_in_config` (
                                         `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
                                         `day` int NOT NULL COMMENT '第几天',
                                         `point` int NOT NULL COMMENT '奖励积分',
                                         `experience` int NOT NULL DEFAULT '0' COMMENT '奖励经验',
                                         `status` tinyint NOT NULL COMMENT '状态',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- 创建人
                                         `creator` varchar(255) DEFAULT '',
    -- 更新人
                                         `updater` varchar(255) DEFAULT '',
                                         `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除标记，0 表示未删除，1 表示已删除',
                                         `tenant_id` int NOT NULL DEFAULT 0 COMMENT '租户 ID',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签到规则';


CREATE TABLE `member_sign_in_record` (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '签到自增id',

                                         `user_id` int DEFAULT NULL COMMENT '签到用户',

                                         `day` int DEFAULT NULL COMMENT '第几天签到',

                                         `point` int NOT NULL DEFAULT '0' COMMENT '签到的分数',
                                         `experience` int NOT NULL DEFAULT '0' COMMENT '奖励经验',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- 创建人
                                         `creator` varchar(255) DEFAULT '',
    -- 更新人
                                         `updater` varchar(255) DEFAULT '',
                                         `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除标记，0 表示未删除，1 表示已删除',
                                         `tenant_id` int NOT NULL DEFAULT 0 COMMENT '租户 ID',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签到记录';


CREATE TABLE `member_experience_record` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                            `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户编号',
                                            `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务编号',
                                            `biz_type` tinyint NOT NULL DEFAULT '0' COMMENT '业务类型',
                                            `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标题',
                                            `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
                                            `experience` int NOT NULL DEFAULT '0' COMMENT '经验',
                                            `total_experience` int NOT NULL DEFAULT '0' COMMENT '变更后的经验',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '会员经验记录-用户编号',
                                            KEY `idx_user_biz_type` (`user_id`,`biz_type`) USING BTREE COMMENT '会员经验记录-用户业务类型'
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员经验记录';



CREATE TABLE `member_level_record` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                       `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户编号',
                                       `level_id` bigint NOT NULL DEFAULT '0' COMMENT '等级编号',
                                       `level` int NOT NULL DEFAULT '0' COMMENT '会员等级',
                                       `discount_percent` tinyint NOT NULL DEFAULT '100' COMMENT '享受折扣',
                                       `experience` int NOT NULL DEFAULT '0' COMMENT '升级经验',
                                       `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
                                       `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',

                                       `user_experience` int NOT NULL DEFAULT '0' COMMENT '会员此时的经验',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '会员等级记录-用户编号'
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员等级记录';
