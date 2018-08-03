create database crawler_service;

use crawler_service;

CREATE TABLE `conf_info` (
  `conf_name` varchar(32) NOT NULL COMMENT '配置名',
  `conf_value` varchar(64) NOT NULL COMMENT '配置值',
  `conf_describe` varchar(32) DEFAULT NULL COMMENT '配置描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置项表';

CREATE TABLE `plate_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL COMMENT '关联网站id',
  `url` varchar(128) NOT NULL COMMENT '版块网址',
  `plate_name` varchar(64) NOT NULL COMMENT '版块名称',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版块表';

CREATE TABLE `rule_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plate_id` int(11) NOT NULL COMMENT '关联版块id',
  `url_dom` varchar(128) DEFAULT NULL COMMENT 'url节点',
  `content_dom` varchar(128) DEFAULT NULL COMMENT 'content节点',
  `list_time_dom` varchar(128) DEFAULT NULL COMMENT '列表页time节点',
  `detail_time_dom` varchar(128) DEFAULT NULL COMMENT '详情页time节点',
  `time_from_url` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否使用详情页url中取日期方式',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则表';

CREATE TABLE `site_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(128) NOT NULL COMMENT '首页网址',
  `site_name` varchar(64) NOT NULL COMMENT '网站名称',
  `charset` varchar(16) NOT NULL COMMENT '字符编码',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '网站类型',
  `area` bit(1) NOT NULL DEFAULT b'0' COMMENT '所属区域，0.境内 1.境外',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网站表';

CREATE TABLE `task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(32) NOT NULL COMMENT '任务名',
  `task_plate_ids` text NOT NULL COMMENT '任务版块id集合',
  `task_describe` varchar(64) DEFAULT NULL COMMENT '任务描述',
  `task_get_addresses` varchar(128) NOT NULL COMMENT '从节点取任务地址',
  `task_submit_addresses` varchar(128) NOT NULL COMMENT '数据提交地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_name` (`task_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';