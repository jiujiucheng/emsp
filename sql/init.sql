-- 以docker 启动不用创建数据库，不以docker 启动，请先创建数据库
-- create database  emsp;
-- 账号
CREATE TABLE `emsp_account`
(
    `id`          int(10) unsigned    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `email`       varchar(18)        NOT NULL DEFAULT '' COMMENT '邮箱（唯一）',
    `contract_id` varchar(14)         NOT NULL DEFAULT '' COMMENT '合约ID',
    `status`      tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态（1-已创建，2-已激活,3-未激活）',
    `create_time` timestamp           NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_idx_email` (`email`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
-- 卡片
CREATE TABLE `emsp_card`
(
    `id`             int(10) unsigned    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uid`            varchar(64)         NOT NULL DEFAULT '' COMMENT 'uid 遵循EMAID format',
    `visible_number` varchar(64)         NOT NULL DEFAULT '' COMMENT '卡号',
    `status`         tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态（1-已创建 ，2-已分配,3-已激活，4-未激活）',
    `email`          varchar(18)         NOT NULL DEFAULT '' COMMENT '邮箱',
    `create_time`    timestamp           NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `update_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
