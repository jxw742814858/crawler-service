/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50614
 Source Host           : localhost:3306
 Source Schema         : crawler_service

 Target Server Type    : MySQL
 Target Server Version : 50614
 File Encoding         : 65001

 Date: 08/08/2018 17:17:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for conf_info
-- ----------------------------
DROP TABLE IF EXISTS `conf_info`;
CREATE TABLE `conf_info`  (
  `conf_name` varchar(32) NOT NULL COMMENT '配置名',
  `conf_value` varchar(64) NOT NULL COMMENT '配置值',
  `conf_describe` varchar(32) DEFAULT NULL COMMENT '配置描述'
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '配置项表';

-- ----------------------------
-- Table structure for data_set
-- ----------------------------
DROP TABLE IF EXISTS `data_set`;
CREATE TABLE `data_set`  (
  `id` varchar(32) NOT NULL,
  `url` varchar(128) NOT NULL,
  `title` varchar(128) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `release_time` datetime(0) NOT NULL COMMENT '发布时间',
  `site_id` int(11) NOT NULL,
  `site_name` varchar(64) NOT NULL,
  `plate_name` varchar(64) DEFAULT NULL,
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '数据存储表';

-- ----------------------------
-- Table structure for plate_rule_info
-- ----------------------------
DROP TABLE IF EXISTS `plate_rule_info`;
CREATE TABLE `plate_rule_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '关联网站id',
  `url` varchar(128) NOT NULL COMMENT '版块网址',
  `plate_name` varchar(64) NOT NULL COMMENT '版块名称',
  `url_dom` varchar(128) NULL COMMENT 'url规则',
  `content_dom` varchar(128) NULL COMMENT '内容规则',
  `list_time_dom` varchar(128) NULL COMMENT '列表页时间规则',
  `detail_time_dom` varchar(128) NULL COMMENT '详情页时间规则',
  `time_from_url` bit(1) NOT NULL COMMENT '是否在url中截取时间',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url`(`url`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '版块和规则表';

-- ----------------------------
-- Table structure for site_info
-- ----------------------------
DROP TABLE IF EXISTS `site_info`;
CREATE TABLE `site_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(128) NOT NULL COMMENT '首页网址',
  `site_name` varchar(64) NOT NULL COMMENT '网站名称',
  `charset` varchar(16) NOT NULL COMMENT '字符编码',
  `type` int(1) NOT NULL DEFAULT 0 COMMENT '网站类型',
  `area` bit(1) NOT NULL DEFAULT b'0' COMMENT '所属区域，0.境内 1.境外',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url`(`url`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '网站表';

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(32) NOT NULL COMMENT '任务名',
  `task_describe` varchar(64) NULL COMMENT '任务描述',
  `task_submit_addresses` varchar(128) NOT NULL COMMENT '数据提交地址',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `task_name`(`task_name`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '任务表';

-- ----------------------------
-- Table structure for task_plate_ids
-- ----------------------------
DROP TABLE IF EXISTS `task_plate_ids`;
CREATE TABLE `task_plate_ids`  (
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `plate_id` int(11) NOT NULL COMMENT '版块id'
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '任务版块关联表';

SET FOREIGN_KEY_CHECKS = 1;
