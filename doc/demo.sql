/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-06-05 16:56:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `authority` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `last_longin_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
