/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.32 : Database - courseware
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`courseware` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `courseware`;

/*Table structure for table `cw_courseware` */

DROP TABLE IF EXISTS `cw_courseware`;

CREATE TABLE `cw_courseware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `count` int(11) DEFAULT '0',
  `url` varchar(10000) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `cover` varchar(255) NOT NULL,
  `is_carousel` int(11) DEFAULT '0',
  `carousel_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `cw_courseware` */

insert  into `cw_courseware`(`id`,`name`,`price`,`count`,`url`,`create_time`,`cover`,`is_carousel`,`carousel_url`) values 
(1,'测试ppt',0.02,1,'/resource/20210220045854mark.pptx','2021-02-18 13:55:11','/resource/20210218135442链表.png',1,'/resource/20210220045823q.jpg'),
(4,'微服务架构',9.99,0,'/resource/20210220045620微服务架构.pdf','2021-02-18 13:55:11','/resource/20210220050408noorder.png',2,'/resource/20210218185458starSky.png'),
(20,'wps网盘使用帮助',0.01,0,'/resource/20210220050216WPS网盘使用帮助.doc','2021-02-20 21:56:14','/resource/20210220050158log.png',0,NULL);

/*Table structure for table `cw_exchange_key` */

DROP TABLE IF EXISTS `cw_exchange_key`;

CREATE TABLE `cw_exchange_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ex_key` varchar(255) NOT NULL,
  `cw_id` int(11) NOT NULL,
  `is_used` tinyint(1) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `use_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cw_exchange_key_cw_courseware_id_fk` (`cw_id`),
  KEY `cw_exchange_key_user_id_fk` (`user_id`),
  CONSTRAINT `cw_exchange_key_cw_courseware_id_fk` FOREIGN KEY (`cw_id`) REFERENCES `cw_courseware` (`id`),
  CONSTRAINT `cw_exchange_key_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `cw_exchange_key` */

insert  into `cw_exchange_key`(`id`,`ex_key`,`cw_id`,`is_used`,`create_time`,`use_time`,`user_id`) values 
(2,'5daca046-2e63-4454-b673-751909de4b55',4,1,'2021-02-20 10:46:50','2021-02-20 10:48:34',4),
(3,'fd0dfb2a-dd1e-402c-9017-c2f3d6a53389',1,1,'2021-02-20 21:12:15','2021-02-20 21:13:57',4);

/*Table structure for table `cw_order` */

DROP TABLE IF EXISTS `cw_order`;

CREATE TABLE `cw_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(255) NOT NULL,
  `cw_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_time` timestamp NULL DEFAULT NULL,
  `is_pay` tinyint(1) DEFAULT '0',
  `pay_type` int(11) DEFAULT NULL COMMENT '0->小程序',
  `wx_order` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cw_order_order_sn_index` (`order_sn`),
  KEY `cw_order_cw_courseware_id_fk` (`cw_id`),
  KEY `cw_order_user_id_fk` (`user_id`),
  CONSTRAINT `cw_order_cw_courseware_id_fk` FOREIGN KEY (`cw_id`) REFERENCES `cw_courseware` (`id`),
  CONSTRAINT `cw_order_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `cw_order` */

insert  into `cw_order`(`id`,`order_sn`,`cw_id`,`user_id`,`price`,`create_time`,`pay_time`,`is_pay`,`pay_type`,`wx_order`) values 
(6,'c933cbd4-a784-4ed9-8aeb-6344f3899064',1,4,0.02,'2021-02-20 10:44:23',NULL,0,NULL,NULL),
(7,'58fac7bc-022d-4dc3-9123-d32fa7376536',1,4,0.02,'2021-02-20 10:53:58',NULL,0,NULL,NULL);

/*Table structure for table `cw_user_courseware` */

DROP TABLE IF EXISTS `cw_user_courseware`;

CREATE TABLE `cw_user_courseware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `cw_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `cw_user_courseware_cw_courseware_id_fk` (`cw_id`),
  KEY `cw_user_courseware_user_id_fk` (`user_id`),
  CONSTRAINT `cw_user_courseware_cw_courseware_id_fk` FOREIGN KEY (`cw_id`) REFERENCES `cw_courseware` (`id`),
  CONSTRAINT `cw_user_courseware_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `cw_user_courseware` */

insert  into `cw_user_courseware`(`id`,`user_id`,`cw_id`,`create_time`) values 
(4,4,4,'2021-02-20 10:48:34'),
(5,4,1,'2021-02-20 21:13:57');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `level` int(255) DEFAULT NULL COMMENT '角色级别',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`name`,`level`,`description`,`data_scope`,`create_by`,`update_by`,`create_time`,`update_time`) values 
(1,'管理员',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(2,'接单者',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_role_permission_sys_role_role_id_fk` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

/*Table structure for table `sys_users_roles` */

DROP TABLE IF EXISTS `sys_users_roles`;

CREATE TABLE `sys_users_roles` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

/*Data for the table `sys_users_roles` */

insert  into `sys_users_roles`(`user_id`,`role_id`) values 
(3,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `gender` enum('男','女','保密') NOT NULL DEFAULT '保密',
  `portrait` varchar(255) DEFAULT NULL COMMENT '头像',
  `background` varchar(255) DEFAULT NULL COMMENT '背景图片',
  `phone_number` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_uindex` (`username`),
  UNIQUE KEY `user_uuid_uindex` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`nickname`,`uuid`,`username`,`password`,`gender`,`portrait`,`background`,`phone_number`) values 
(1,'起凡','43dcbc5b-8776-429e-b12b-2cae6bd97020','起凡','25d55ad283aa400af464c76d713c07ad','男','/resource/img/1612235895184132.png','',''),
(2,'珂朵莉永生','43dcbc5b-8776-429e-b12b-2cae6bd97021','游客','25d55ad283aa400af464c76d713c07ad','保密','/resource/20201105012059-7892016800fa31cd.jpg',NULL,NULL),
(3,'管理员','43dcbc5b-8776-439e-b12b-2cae6bd97021','admin','25d55ad283aa400af464c76d713c07ad','保密',NULL,NULL,NULL),
(4,'起凡','fbca158c-722b-45d8-ad63-ddf44ad3b422','0m4lJ60b6w','j47izilk5b','男','https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLVvJDehJiaEMKnDpKzet1Fy2QMCATHaUdb6Lc6v0WZapn67IyTEGr8UrE16FeC6rvpcwibIylVu8Zg/132','','13656987994');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
