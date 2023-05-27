/*
 Navicat Premium Data Transfer

 Source Server         : mysql-5.7.35-localhost
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : eas_by_crpc

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 24/05/2023 23:08:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `unique_id` bigint(15) NOT NULL COMMENT '学生 或 教师 id',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加盐 md5 密码',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniqueId`(`unique_id`) USING BTREE,
  INDEX `password`(`password`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 123456, 'admin@swpu.edu.cn', 'e10adc3949ba59abbe56e057f20f883e', '2023-04-16 10:58:41', '2023-04-16 10:58:45');
INSERT INTO `account` VALUES (2, 101931061288, 'zhangjiaoshou@swpu.edu.cn', 'e10adc3949ba59abbe56e057f20f883e', '2023-04-11 15:56:00', '2023-04-11 15:56:05');
INSERT INTO `account` VALUES (3, 201931061399, 'lihua@swpu.edu.cn', 'e10adc3949ba59abbe56e057f20f883e', '2023-04-08 16:20:37', '2023-05-24 23:06:13');
INSERT INTO `account` VALUES (4, 201931061377, 'zhangxiaoshuai@swpu.edu.cn', 'e10adc3949ba59abbe56e057f20f883e', '2023-05-19 10:52:33', '2023-05-24 23:05:53');
INSERT INTO `account` VALUES (5, 101931066666, 'chenjiaoshou@swpu.edu.cn', 'e10adc3949ba59abbe56e057f20f883e', '2023-05-19 11:02:15', '2023-05-19 11:07:51');

-- ----------------------------
-- Table structure for class_schedule
-- ----------------------------
DROP TABLE IF EXISTS `class_schedule`;
CREATE TABLE `class_schedule`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程表 id',
  `course_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程号',
  `course_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课序号',
  `student_id` bigint(15) NULL DEFAULT NULL COMMENT '学生 id',
  `tutor_id` bigint(15) NULL DEFAULT NULL COMMENT '教师 id',
  `semester_id` int(10) NULL DEFAULT NULL COMMENT '学期 id',
  `score_id` int(10) NULL DEFAULT NULL COMMENT '成绩 id',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '选中' COMMENT '选课状态。\"选中\" | \"已退课\"',
  `class_info` json NULL COMMENT '课程信息',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `courseId_courseNum`(`course_id`, `course_num`) USING BTREE,
  INDEX `studentId_tutorId`(`student_id`, `tutor_id`) USING BTREE,
  INDEX `semesterId_scoreId`(`semester_id`, `score_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for course_order
-- ----------------------------
DROP TABLE IF EXISTS `course_order`;
CREATE TABLE `course_order`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单表 id',
  `student_id` bigint(15) NOT NULL COMMENT '学生 id',
  `total_credit` double NULL DEFAULT NULL COMMENT '总学分',
  `classes` json NULL COMMENT '课程信息。如：[{\"course_id\":\"\",\"course_number\":\"\",\"credit\":0,\"course_name\":\"\"}]',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '选课订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_order
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `unique_id` bigint(15) NOT NULL COMMENT '学生 或 教师 id',
  `role` tinyint(10) NULL DEFAULT 0 COMMENT '角色。0 - 学生，1 - 教师，2 - 管理员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniqueId`(`unique_id`) USING BTREE,
  INDEX `role`(`role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 123456, 2);
INSERT INTO `role` VALUES (2, 101931061288, 1);
INSERT INTO `role` VALUES (3, 201931061399, 0);
INSERT INTO `role` VALUES (4, 201931061377, 0);
INSERT INTO `role` VALUES (5, 101931066666, 1);

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '成绩表 id',
  `score` double NULL DEFAULT 0 COMMENT '成绩',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生成绩表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学期 id',
  `semester` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `semester`(`semester`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学期信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (1, '2019-2020秋');
INSERT INTO `semester` VALUES (2, '2020-2021春');
INSERT INTO `semester` VALUES (3, '2021-2022秋');
INSERT INTO `semester` VALUES (4, '2022-2023春');

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `unique_id` bigint(15) NOT NULL COMMENT '学生 id',
  `coupon` double NULL DEFAULT NULL COMMENT '学分券',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `id_card_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别。\'男\' / \'女\'',
  `nationality` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '汉族' COMMENT '民族，默认：汉族',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期。年+月+日，如：20001230',
  `exam_from` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考区省份',
  `gaokao_score` double NULL DEFAULT 0 COMMENT '高考分数',
  `graduated_school` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业学校',
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '院系',
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `campus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '校区',
  `class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级',
  `trainning_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '本科' COMMENT '培养层次',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniqueId_idCardNum`(`unique_id`, `id_card_num`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_info
-- ----------------------------
INSERT INTO `student_info` VALUES (1, 201931061399, 100, '李华', '509876200010010010', '19829981063', '男', '汉族', '20001001', '江苏省', 517.15, '江苏省第一中学', '石工院', '计算结科学与技术', '成都校区', '海油1901', '本科', '2023-04-08 16:24:25', '2023-05-24 23:06:13');
INSERT INTO `student_info` VALUES (2, 201931061377, 100, '张小帅', '556681200012300030', '19812348769', '男', '汉族', '20001230', '广东省', 600, '广州一中', '计科院', '计算机科学与技术', '成都校区', '计科3班', '本科', '2023-05-19 10:52:33', '2023-05-24 23:05:53');

-- ----------------------------
-- Table structure for tutor_info
-- ----------------------------
DROP TABLE IF EXISTS `tutor_info`;
CREATE TABLE `tutor_info`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `unique_id` bigint(15) NOT NULL COMMENT '教师 id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别。\'男\' / \'女\'',
  `nationality` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '汉族' COMMENT '民族，默认：汉族',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期。年+月+日，如：20001230',
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '院系',
  `campus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '校区',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniqueId`(`unique_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tutor_info
-- ----------------------------
INSERT INTO `tutor_info` VALUES (1, 101931061288, '张教授', '19828901575', '男', '汉族', '19750901', '计科院', '成都校区', '2023-04-11 15:39:33', '2023-05-19 11:05:07');
INSERT INTO `tutor_info` VALUES (2, 101931066666, '陈教授', '13122213333', '女', '汉族', '19770910', '计科院', '成都校区', '2023-05-19 11:02:15', '2023-05-19 11:07:51');

SET FOREIGN_KEY_CHECKS = 1;
