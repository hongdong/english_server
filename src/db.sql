/*
SQLyog v10.2 
MySQL - 5.0.45-community-nt : Database - sm
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

   
/*Table structure for table `TbsMenu` */

DROP TABLE IF EXISTS `TbsMenu`;

CREATE TABLE `TbsMenu` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '唯一',
  `parentId` char(20) default NULL COMMENT '父节点',
  `name` varchar(50) collate utf8_unicode_ci default NULL COMMENT '名称',
  `isMenu` int(1) default '0' COMMENT '菜单类型',
  `type` int(1) default '0' COMMENT '加载方式',
  `sortNumber` int(9) default '0' COMMENT '排序',
  `url` varchar(200) collate utf8_unicode_ci default NULL COMMENT '地址',
  `button` varchar(500) collate utf8_unicode_ci default NULL COMMENT '按钮',
  `status` int(9) default '0' COMMENT '状态',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='菜单导航';

/*Data for the table `TbsMenu` */



/*Table structure for table `TbsRole` */

DROP TABLE IF EXISTS `TbsRole`;

CREATE TABLE `TbsRole` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '主键',
  `name` varchar(20) collate utf8_unicode_ci default NULL COMMENT '角色',
  `text` varchar(500) collate utf8_unicode_ci default NULL COMMENT '所有权限',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='权限角色';



/*Table structure for table `TbsRoleMenu` */

DROP TABLE IF EXISTS `TbsRoleMenu`;

CREATE TABLE `TbsRoleMenu` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '主键',
  `roleId` char(20) collate utf8_unicode_ci default NULL COMMENT '角色主键',
  `menuIdFun` char(20) collate utf8_unicode_ci default NULL COMMENT '功能主键',
  `menuId` char(20) collate utf8_unicode_ci default NULL COMMENT '菜单主键',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色与资源关联表';

/*Data for the table `TbsRoleMenu` */


/*Table structure for table `TbsRoleUser` */

DROP TABLE IF EXISTS `TbsRoleUser`;

CREATE TABLE `TbsRoleUser` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '主键',
  `userId` char(20) collate utf8_unicode_ci default NULL COMMENT '用户主键',
  `roleId` char(20) collate utf8_unicode_ci default NULL COMMENT '角色主键',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户与角色关联表';

/*Data for the table `TbsRoleUser` */


/*Table structure for table `TbsUser` */

DROP TABLE IF EXISTS `TbsUser`;

CREATE TABLE `TbsUser` (
  `id` CHAR(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `username` VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL  COMMENT '用户名',
  `password` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '时间',
  `ip` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ip',
  `count` INT(9) DEFAULT '0' COMMENT '次数',
  `isLock` INT(1) DEFAULT '0' COMMENT '锁定',
  `lockTime` DATETIME DEFAULT NULL COMMENT '锁定时间',
  `failCount` INT(1) DEFAULT '0' COMMENT '失败次数',
  `isAdmin` INT(1) DEFAULT '1' COMMENT '权限类型',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统用户';

/*Data for the table `TbsUser` */



/*Table structure for table `TbsLoginLog` */
DROP TABLE IF EXISTS `TbsLoginLog`;
CREATE TABLE `TbsLoginLog` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '主键',
  `username` varchar(50) collate utf8_unicode_ci default NULL COMMENT '用户名',
  `password` varchar(50) collate utf8_unicode_ci default NULL COMMENT '密码',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  `ip` varchar(20) collate utf8_unicode_ci default NULL COMMENT 'IP',
  `userAgent` varchar(300) collate utf8_unicode_ci default NULL COMMENT '设备',
  `status` int(1) default '1' COMMENT '状态',
  `msg` varchar(50) collate utf8_unicode_ci default NULL COMMENT '消息',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='登录日志';

/*Table structure for table `TbsBrowserName` */
DROP TABLE IF EXISTS `TbsBrowserName`;
CREATE TABLE `TbsBrowserName` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '主键',
  `browser` varchar(50) collate utf8_unicode_ci default NULL COMMENT '搜索',
  `nickname` varchar(20) collate utf8_unicode_ci default NULL COMMENT '昵称',
  `type` int(1) default '0' COMMENT '类型',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='浏览器表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

/*Table structure for table `TbsMenu` */


DROP TABLE IF EXISTS `TbcMenu`;

CREATE TABLE `TbcMenu` (
  `id` char(20) collate utf8_unicode_ci NOT NULL COMMENT '唯一',
  `parentId` char(20) default NULL COMMENT '父节点',
  `name` varchar(50) collate utf8_unicode_ci default NULL COMMENT '名称',
  `isMenu` int(1) default '0' COMMENT '菜单类型',
  `type` int(1) default '0' COMMENT '系统类型',
  `sortNumber` int(9) default '0' COMMENT '排序',
  `url` varchar(200) collate utf8_unicode_ci default NULL COMMENT '地址',
  `button` varchar(500) collate utf8_unicode_ci default NULL COMMENT '按钮',
  `status` int(9) default '0' COMMENT '状态',
  `createTime` timestamp NOT NULL default CURRENT_TIMESTAMP  COMMENT '时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='主页导航';

 