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

 Date: 21/04/2023 12:03:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生 或 教师 id',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加盐 md5 密码',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_password`(`id`, `password`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201931061400 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生 id',
  `coupon` double NULL DEFAULT NULL COMMENT '学生券',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201931061400 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学分券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生 或 教师 id',
  `role` tinyint(10) NULL DEFAULT 0 COMMENT '角色。0 - 学生，1 - 教师，2 - 管理员',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_role`(`id`, `role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201931061400 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `student_id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生 id',
  `course_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程号',
  `course_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课序号',
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成绩',
  `created_at` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`student_id`) USING BTREE,
  INDEX `id_courseId_courseNum`(`student_id`, `course_id`, `course_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201931061400 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生成绩表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学期 id',
  `semester` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学期信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生学号',
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
  UNIQUE INDEX `id_idCardNum`(`id`, `id_card_num`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201931061400 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teaching_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `teaching_evaluation`;
CREATE TABLE `teaching_evaluation`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评估 id',
  `semester_id` int(10) NULL DEFAULT NULL COMMENT '评估学期 id',
  `evaluatee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被评估人',
  `finished` tinyint(3) NULL DEFAULT NULL COMMENT '是否已完成评估。0 - 未完成，1 - 已完成',
  `content` json NULL COMMENT '教学评估内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_semester_id`(`id`, `semester_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教学评估表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tutor_info
-- ----------------------------
DROP TABLE IF EXISTS `tutor_info`;
CREATE TABLE `tutor_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师 id',
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
  INDEX `id_name`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101931061289 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
