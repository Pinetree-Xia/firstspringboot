/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : biz

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-01-19 22:19:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for coin_trade
-- ----------------------------
DROP TABLE IF EXISTS `coin_trade`;
CREATE TABLE `coin_trade` (
  `oid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NOT NULL,
  `tid` bigint(20) NOT NULL,
  `trade_time` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `amount` decimal(20,10) NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `unk_tid` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=91662 DEFAULT CHARSET=utf8;