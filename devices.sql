/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : feibiao

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-03-31 17:27:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for devices
-- ----------------------------
DROP TABLE IF EXISTS `devices`;
CREATE TABLE `devices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Deviceid` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Controllerid` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Devicepassword` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `Devicebinding` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `Onlinestatus` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `Company` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `Cardnumber` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Activate` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Gps` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Controllerid2` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Devicebinding2` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of devices
-- ----------------------------
INSERT INTO `devices` VALUES ('1', '00000060', '18888888888', '12345678', 'true', '掉线', '深圳市天涯同行', '粤88888', '已激活', 'DID00000067$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '13425144857', 'true');
INSERT INTO `devices` VALUES ('2', '00000061', '', '12345678', 'true', '掉线', '北京天涯同行', '粤9999', '已激活', 'DID00000067$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00', 'false');
INSERT INTO `devices` VALUES ('3', '00000066', '12345678922', '12345678', 'true', '掉线', '非洲天涯同行', '粤7777', '已激活', 'DID00000067$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00', 'false');
INSERT INTO `devices` VALUES ('4', '00000067', '18888888888', '12345678', 'true', '在线', '泰国天涯同行', '粤88888', '已激活', 'DID00000067$GPRMC,033405.00,A,2242.25381,N,11349.48962,E,1.219,,131216,,,A*78', '00000000001', 'true');
INSERT INTO `devices` VALUES ('5', '00000068', '00000000001', '12345678', 'true', '在线', '土耳其天涯同行注', '粤88888', '已激活', 'DID00000068$GPRMC,091120.00,A,2242.24556,N,11349.47112,E,0.107,,021216,,,A*72', '00000000004', 'true');
INSERT INTO `devices` VALUES ('6', '00000069', '13425144857', '12345678', 'true', '掉线', '天涯同行有限公司', '粤EJ88888', '已激活', 'DID00000069$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00000000004', 'true');
INSERT INTO `devices` VALUES ('7', '00000070', '00', '12345678', 'false', '掉线', '天涯同行有限公司', '粤EJ88888', '已激活', 'DID00000067$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00', 'false');
INSERT INTO `devices` VALUES ('8', '00000071', '13425144857', '12345678', 'true', '掉线', '天涯同行有限公司', '粤EJ88888', '已激活', 'DID00000071$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00', 'false');
INSERT INTO `devices` VALUES ('9', '10000000', '10000000000', '12345678', 'true', '掉线', '高远房车', '粤EJ88888', '已激活', 'DID10000000$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00', 'false');
INSERT INTO `devices` VALUES ('10', '10000001', '10000000000', '12345678', 'true', '掉线', '高远房车', '粤EJ88888', '已激活', 'DID10000001$GPRMC,085746.00,A,2242.24931,N,11349.47541,E,0.724,,251116,,,A*7F', '00', 'false');
