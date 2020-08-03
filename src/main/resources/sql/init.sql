
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for d_sys_file_attachment
-- ----------------------------
DROP TABLE IF EXISTS `d_sys_file_attachment`;
CREATE TABLE `d_sys_file_attachment` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `org_file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '原文件名',
  `tc_contentid` bigint(20) DEFAULT NULL COMMENT 'tc内容id',
  `operator` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `tc_attachmentids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'tc附件文件id',
  `av_times` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '音视频时长',
  `attach_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '附件类别1内嵌于content中的附件2独立附件',
  `img_width` bigint(11) DEFAULT NULL COMMENT '图片宽',
  `img_height` bigint(11) DEFAULT NULL COMMENT '图片高',
  `web_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '相对路径weburl文件访问的相对路径weburl',
  `ftp_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'ftp访问链接',
  `file_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '文件类型',
  `file_suffix` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '文件后缀名',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '文件相对路径不包括置顶的父目录',
  `file_alias` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '文件别名',
  `file_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '现文件名',
  `file_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '文件编码图片服务器返回的编码或系统生成的唯一编码',
  `rec_id_suffix` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主键和后缀名',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统资源文件';
-- ----------------------------
-- Table structure for d_sys_operatelog
-- ----------------------------
DROP TABLE IF EXISTS `d_sys_operatelog`;
CREATE TABLE `d_sys_operatelog` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `operate_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求地址',
  `operate_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作描述',
  `in_param` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `out_param` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '响应参数',
  `rec_person_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人名称',
  `operate_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作ID地址',
  `operate_result` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '响应结果编码',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统操作日志';

-- ----------------------------
-- Table structure for s_sysarea
-- ----------------------------
DROP TABLE IF EXISTS `s_sysarea`;
CREATE TABLE `s_sysarea` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `area_code` bigint(18) DEFAULT NULL COMMENT '区域编码',
  `area_sname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '短写名称',
  `area_lname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整名称',
  `parent_recid` bigint(18) DEFAULT NULL COMMENT '父ID',
  `sort` bigint(4) DEFAULT '0' COMMENT '排序',
  `area_type` bigint(1) DEFAULT '0' COMMENT '类型是否有子节点0没有1有',
  `enabled` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否有效0-无效，1-有效',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统区域表';

-- ----------------------------
-- Records of s_sysarea
-- ----------------------------
BEGIN;
INSERT INTO `s_sysarea` VALUES (494984884176027648, 35, '福建省', '福建省', 0, 13, 1, '1', '1', 'sys', '2018-09-27 21:33:12', '', '2019-06-10 15:36:07');
INSERT INTO `s_sysarea` VALUES (494984885581119488, 3501, '福州市', '福建省-福州市', 35, 1, 1, '1', '1', 'sys', '2018-09-27 21:33:13', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984886910713856, 350102, '鼓楼区', '福建省-福州市-鼓楼区', 3501, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:13', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984889293078528, 350103, '台江区', '福建省-福州市-台江区', 3501, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:14', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984891016937472, 350104, '仓山区', '福建省-福州市-仓山区', 3501, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:14', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984893533519872, 350105, '马尾区', '福建省-福州市-马尾区', 3501, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:15', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984894988943360, 350111, '晋安区', '福建省-福州市-晋安区', 3501, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:15', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984896318537728, 350121, '闽侯县', '福建省-福州市-闽侯县', 3501, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:15', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984897866235904, 350122, '连江县', '福建省-福州市-连江县', 3501, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:16', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984899355213824, 350123, '罗源县', '福建省-福州市-罗源县', 3501, 8, 0, '1', '1', 'sys', '2018-09-27 21:33:16', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984900810637312, 350124, '闽清县', '福建省-福州市-闽清县', 3501, 9, 0, '1', '1', 'sys', '2018-09-27 21:33:16', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984902282838016, 350125, '永泰县', '福建省-福州市-永泰县', 3501, 10, 0, '1', '1', 'sys', '2018-09-27 21:33:17', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984903796981760, 350128, '平潭县', '福建省-福州市-平潭县', 3501, 11, 0, '1', '1', 'sys', '2018-09-27 21:33:17', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984905122381824, 350181, '福清市', '福建省-福州市-福清市', 3501, 12, 0, '1', '1', 'sys', '2018-09-27 21:33:17', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984906632331264, 350182, '长乐市', '福建省-福州市-长乐市', 3501, 13, 0, '1', '1', 'sys', '2018-09-27 21:33:18', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984908129697792, 350183, '其它区', '福建省-福州市-其它区', 3501, 14, 0, '1', '1', 'sys', '2018-09-27 21:33:18', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984909673201664, 3502, '厦门市', '福建省-厦门市', 35, 2, 1, '1', '1', 'sys', '2018-09-27 21:33:18', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984911090876416, 350203, '思明区', '福建省-厦门市-思明区', 3502, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:19', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984912487579648, 350205, '海沧区', '福建省-厦门市-海沧区', 3502, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:19', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984913934614528, 350206, '湖里区', '福建省-厦门市-湖里区', 3502, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:20', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984915415203840, 350211, '集美区', '福建省-厦门市-集美区', 3502, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:20', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984916912570368, 350212, '同安区', '福建省-厦门市-同安区', 3502, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:20', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984918409936896, 350213, '翔安区', '福建省-厦门市-翔安区', 3502, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:21', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984919898914816, 350214, '其它区', '福建省-厦门市-其它区', 3502, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:21', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984921169788928, 3503, '莆田市', '福建省-莆田市', 35, 3, 1, '1', '1', 'sys', '2018-09-27 21:33:21', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984922843316224, 350302, '城厢区', '福建省-莆田市-城厢区', 3503, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:22', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984924202270720, 350303, '涵江区', '福建省-莆田市-涵江区', 3503, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:22', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984925741580288, 350304, '荔城区', '福建省-莆田市-荔城区', 3503, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:22', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984927096340480, 350305, '秀屿区', '福建省-莆田市-秀屿区', 3503, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:23', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984928530792448, 350322, '仙游县', '福建省-莆田市-仙游县', 3503, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:23', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984929969438720, 350323, '其它区', '福建省-莆田市-其它区', 3503, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:23', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984931877847040, 3504, '三明市', '福建省-三明市', 35, 4, 1, '1', '1', 'sys', '2018-09-27 21:33:24', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984933249384448, 350402, '梅列区', '福建省-三明市-梅列区', 3504, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:24', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984934746750976, 350403, '三元区', '福建省-三明市-三元区', 3504, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:24', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984936344780800, 350421, '明溪县', '福建省-三明市-明溪县', 3504, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:25', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984937733095424, 350423, '清流县', '福建省-三明市-清流县', 3504, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:25', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984939117215744, 350424, '宁化县', '福建省-三明市-宁化县', 3504, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:25', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984940597805056, 350425, '大田县', '福建省-三明市-大田县', 3504, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:26', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984942128726016, 350426, '尤溪县', '福建省-三明市-尤溪县', 3504, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:26', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984943563177984, 350427, '沙县', '福建省-三明市-沙县', 3504, 8, 0, '1', '1', 'sys', '2018-09-27 21:33:27', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984944917938176, 350428, '将乐县', '福建省-三明市-将乐县', 3504, 9, 0, '1', '1', 'sys', '2018-09-27 21:33:27', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984946281086976, 350429, '泰宁县', '福建省-三明市-泰宁县', 3504, 10, 0, '1', '1', 'sys', '2018-09-27 21:33:27', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984947581321216, 350430, '建宁县', '福建省-三明市-建宁县', 3504, 11, 0, '1', '1', 'sys', '2018-09-27 21:33:28', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984948902526976, 350481, '永安市', '福建省-三明市-永安市', 3504, 12, 0, '1', '1', 'sys', '2018-09-27 21:33:28', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984950437642240, 350482, '其它区', '福建省-三明市-其它区', 3504, 13, 0, '1', '1', 'sys', '2018-09-27 21:33:28', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984952597708800, 3505, '泉州市', '福建省-泉州市', 35, 5, 1, '1', '1', 'sys', '2018-09-27 21:33:29', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984961133117440, 350502, '鲤城区', '福建省-泉州市-鲤城区', 3505, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:31', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984963876192256, 350503, '丰泽区', '福建省-泉州市-丰泽区', 3505, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:31', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984965214175232, 350504, '洛江区', '福建省-泉州市-洛江区', 3505, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:32', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984966644432896, 350505, '泉港区', '福建省-泉州市-泉港区', 3505, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:32', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984968674476032, 350521, '惠安县', '福建省-泉州市-惠安县', 3505, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:33', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984970318643200, 350524, '安溪县', '福建省-泉州市-安溪县', 3505, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:33', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984972570984448, 350525, '永春县', '福建省-泉州市-永春县', 3505, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:33', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984975154675712, 350526, '德化县', '福建省-泉州市-德化县', 3505, 8, 0, '1', '1', 'sys', '2018-09-27 21:33:35', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984978556256256, 350527, '金门县', '福建省-泉州市-金门县', 3505, 9, 0, '1', '1', 'sys', '2018-09-27 21:33:35', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984980082982912, 350581, '石狮市', '福建省-泉州市-石狮市', 3505, 10, 0, '1', '1', 'sys', '2018-09-27 21:33:35', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984981634875392, 350582, '晋江市', '福建省-泉州市-晋江市', 3505, 11, 0, '1', '1', 'sys', '2018-09-27 21:33:36', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984983107076096, 350583, '南安市', '福建省-泉州市-南安市', 3505, 12, 0, '1', '1', 'sys', '2018-09-27 21:33:36', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984984520556544, 350584, '其它区', '福建省-泉州市-其它区', 3505, 13, 0, '1', '1', 'sys', '2018-09-27 21:33:36', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984985858539520, 3506, '漳州市', '福建省-漳州市', 35, 6, 1, '1', '1', 'sys', '2018-09-27 21:33:37', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984987435597824, 350602, '芗城区', '福建省-漳州市-芗城区', 3506, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:37', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984988932964352, 350603, '龙文区', '福建省-漳州市-龙文区', 3506, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:37', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984990379999232, 350622, '云霄县', '福建省-漳州市-云霄县', 3506, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:38', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984991873171456, 350623, '漳浦县', '福建省-漳州市-漳浦县', 3506, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:38', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984993383120896, 350624, '诏安县', '福建省-漳州市-诏安县', 3506, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:38', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984994888876032, 350625, '长泰县', '福建省-漳州市-长泰县', 3506, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:39', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984996403019776, 350626, '东山县', '福建省-漳州市-东山县', 3506, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:39', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984997904580608, 350627, '南靖县', '福建省-漳州市-南靖县', 3506, 8, 0, '1', '1', 'sys', '2018-09-27 21:33:40', '', NULL);
INSERT INTO `s_sysarea` VALUES (494984999418724352, 350628, '平和县', '福建省-漳州市-平和县', 3506, 9, 0, '1', '1', 'sys', '2018-09-27 21:33:40', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985002740613120, 350629, '华安县', '福建省-漳州市-华安县', 3506, 10, 0, '1', '1', 'sys', '2018-09-27 21:33:41', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985004246368256, 350681, '龙海市', '福建省-漳州市-龙海市', 3506, 11, 0, '1', '1', 'sys', '2018-09-27 21:33:41', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985007819915264, 350682, '其它区', '福建省-漳州市-其它区', 3506, 12, 0, '1', '1', 'sys', '2018-09-27 21:33:42', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985009107566592, 3507, '南平市', '福建省-南平市', 35, 7, 1, '1', '1', 'sys', '2018-09-27 21:33:42', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985010474909696, 350702, '延平区', '福建省-南平市-延平区', 3507, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:42', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985011867418624, 350721, '顺昌县', '福建省-南平市-顺昌县', 3507, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:43', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985013306064896, 350722, '浦城县', '福建省-南平市-浦城县', 3507, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:43', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985014820208640, 350723, '光泽县', '福建省-南平市-光泽县', 3507, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:44', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985016078499840, 350724, '松溪县', '福建省-南平市-松溪县', 3507, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:44', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985017336791040, 350725, '政和县', '福建省-南平市-政和县', 3507, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:44', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985018871906304, 350781, '邵武市', '福建省-南平市-邵武市', 3507, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:45', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985021224910848, 350782, '武夷山市', '福建省-南平市-武夷山市', 3507, 8, 0, '1', '1', 'sys', '2018-09-27 21:33:45', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985022630002688, 350783, '建瓯市', '福建省-南平市-建瓯市', 3507, 9, 0, '1', '1', 'sys', '2018-09-27 21:33:45', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985024135757824, 350784, '建阳市', '福建省-南平市-建阳市', 3507, 10, 0, '1', '1', 'sys', '2018-09-27 21:33:46', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985025599569920, 350785, '其它区', '福建省-南平市-其它区', 3507, 11, 0, '1', '1', 'sys', '2018-09-27 21:33:46', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985027122102272, 3508, '龙岩市', '福建省-龙岩市', 35, 8, 1, '1', '1', 'sys', '2018-09-27 21:33:47', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985028627857408, 350802, '新罗区', '福建省-龙岩市-新罗区', 3508, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:47', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985030112641024, 350821, '长汀县', '福建省-龙岩市-长汀县', 3508, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:47', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985031630979072, 350822, '永定县', '福建省-龙岩市-永定县', 3508, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:48', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985032897658880, 350823, '上杭县', '福建省-龙岩市-上杭县', 3508, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:48', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985034265001984, 350824, '武平县', '福建省-龙岩市-武平县', 3508, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:48', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985035779145728, 350825, '连城县', '福建省-龙岩市-连城县', 3508, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:49', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985037217792000, 350881, '漳平市', '福建省-龙岩市-漳平市', 3508, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:49', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985038522220544, 350882, '其它区', '福建省-龙岩市-其它区', 3508, 8, 0, '1', '1', 'sys', '2018-09-27 21:33:49', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985039931506688, 3509, '宁德市', '福建省-宁德市', 35, 9, 1, '1', '1', 'sys', '2018-09-27 21:33:50', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985041244323840, 350902, '蕉城区', '福建省-宁德市-蕉城区', 3509, 1, 0, '1', '1', 'sys', '2018-09-27 21:33:50', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985042653609984, 350921, '霞浦县', '福建省-宁德市-霞浦县', 3509, 2, 0, '1', '1', 'sys', '2018-09-27 21:33:50', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985044016758784, 350922, '古田县', '福建省-宁德市-古田县', 3509, 3, 0, '1', '1', 'sys', '2018-09-27 21:33:51', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985045476376576, 350923, '屏南县', '福建省-宁德市-屏南县', 3509, 4, 0, '1', '1', 'sys', '2018-09-27 21:33:51', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985046990520320, 350924, '寿宁县', '福建省-宁德市-寿宁县', 3509, 5, 0, '1', '1', 'sys', '2018-09-27 21:33:51', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985049318359040, 350925, '周宁县', '福建省-宁德市-周宁县', 3509, 6, 0, '1', '1', 'sys', '2018-09-27 21:33:52', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985050811531264, 350926, '柘荣县', '福建省-宁德市-柘荣县', 3509, 7, 0, '1', '1', 'sys', '2018-09-27 21:33:52', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985053307142144, 350981, '福安市', '福建省-宁德市-福安市', 3509, 8, 1, '1', '1', 'sys', '2018-09-27 21:33:53', '', '2019-06-10 15:35:39');
INSERT INTO `s_sysarea` VALUES (494985056217989120, 350982, '福鼎市', '福建省-宁德市-福鼎市', 3509, 9, 0, '1', '1', 'sys', '2018-09-27 21:33:53', '', NULL);
INSERT INTO `s_sysarea` VALUES (494985057639858176, 350983, '其它区', '福建省-宁德市-其它区', 3509, 10, 0, '1', '1', 'sys', '2018-09-27 21:33:54', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_sysconfig
-- ----------------------------
DROP TABLE IF EXISTS `s_sysconfig`;
CREATE TABLE `s_sysconfig` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `sc_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '配置项名称',
  `sc_value` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '配置项的值',
  `sc_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '配置项描述',
  `enabled` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '是否有效0无效 1 有效',
  `sc_group` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '配置项组',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统配置';

-- ----------------------------
-- Records of s_sysconfig
-- ----------------------------
BEGIN;
INSERT INTO `s_sysconfig` VALUES (559056349900570624, '1', 'System_Name', 'SSM示例平台', 'SSM架构源码示例平台', '1', 'SysCfg', 'admin', '2020-03-23 16:50:20', '', NULL);
INSERT INTO `s_sysconfig` VALUES (559056349963485184, '1', 'sysResVersion', '202000320165020', '系统资源版本号css，jss等', '1', 'SysCfg', 'admin', '2020-03-23 16:50:20', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_sysgroup
-- ----------------------------
DROP TABLE IF EXISTS `s_sysgroup`;
CREATE TABLE `s_sysgroup` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组类型：user-用户分组，org-组织机构分组',
  `is_show` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否显示：0-否、1-是',
  `is_default` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否默认分组：0-不是，1-是',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统分组表';

-- ----------------------------
-- Table structure for s_sysgroup_org
-- ----------------------------
DROP TABLE IF EXISTS `s_sysgroup_org`;
CREATE TABLE `s_sysgroup_org` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `group_id` bigint(18) DEFAULT NULL COMMENT '分组ID',
  `organization_id` bigint(18) DEFAULT NULL COMMENT '组织机构ID',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统分组关联组织机构表';

-- ----------------------------
-- Table structure for s_sysjob
-- ----------------------------
DROP TABLE IF EXISTS `s_sysjob`;
CREATE TABLE `s_sysjob` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(18) DEFAULT NULL COMMENT '父ID',
  `parent_ids` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父ID链',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `sort` bigint(18) DEFAULT NULL COMMENT '排序',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统工作职务表';

-- ----------------------------
-- Table structure for s_sysorganization
-- ----------------------------
DROP TABLE IF EXISTS `s_sysorganization`;
CREATE TABLE `s_sysorganization` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型：1-公司，2-部门',
  `area_code` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属区域，可为空',
  `parent_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父rec_id',
  `parent_ids` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父ID链用/斜杆分隔',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '记录状态',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片',
  `sort` bigint(3) DEFAULT NULL COMMENT '排序',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统组织机构表';

-- ----------------------------
-- Records of s_sysorganization
-- ----------------------------
BEGIN;
INSERT INTO `s_sysorganization` VALUES (574895547102527488, '示例公司', '1', NULL, '0', '0', '1', '', 1, 'admin', '2020-05-06 09:49:39', '', NULL);
INSERT INTO `s_sysorganization` VALUES (574895547102527489, '研发部', '2', NULL, '0', '0', '1', '', 1, 'admin', '2020-05-06 09:49:39', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_sysresource
-- ----------------------------
DROP TABLE IF EXISTS `s_sysresource`;
CREATE TABLE `s_sysresource` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型：moduel-模块，menu-菜单，button-按钮',
  `parent_id` bigint(18) DEFAULT NULL COMMENT '父ID',
  `parent_ids` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父ID链用/斜杆分隔',
  `authid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于权限控制的全局唯一编码，在页面和controller中配置要与此保持一致',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接地址',
  `previewurl` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `sort` bigint(11) DEFAULT NULL COMMENT '排序',
  `enabled` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否有效0-否1-是',
  `is_local` bigint(1) DEFAULT '1' COMMENT '是否本系统菜单1是 0否',
  `open_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '打开方式本窗口打开_self，新窗口打开_blank',
  `is_control` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否权限控制0-否、1-是',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统资源表';

-- ----------------------------
-- Records of s_sysresource
-- ----------------------------
BEGIN;
INSERT INTO `s_sysresource` VALUES (160791000658739201, '删除按钮', 'button', 560791000658739200, '0/560767076386471936/560791000658739200', 'userlist_toDelete', '', '', '', 18, '1', 1, '', '0', '1', 'admin', '2020-03-28 11:43:13', 'admin', '2020-04-26 10:56:25');
INSERT INTO `s_sysresource` VALUES (160791000658739202, '新增', 'button', 560791000658739200, '0/560767076386471936/560791000658739200', 'userlist_toAdd', '', '', '', 18, '1', 1, '', '0', '1', 'admin', '2020-03-28 11:43:13', 'admin', '2020-04-26 10:56:25');
INSERT INTO `s_sysresource` VALUES (160791000658739203, '修改', 'button', 560791000658739200, '0/560767076386471936/560791000658739200', 'userlist_toUpdate', '', '', '', 18, '1', 1, '', '0', '1', 'admin', '2020-03-28 11:43:13', 'admin', '2020-04-26 10:56:25');
INSERT INTO `s_sysresource` VALUES (260767076386471936, 'DEMO示例2', 'module', 0, '0', '', '', '', 'layui-icon-form', 18, '1', 1, '', '0', '1', 'admin', '2020-03-28 10:08:09', '', NULL);
INSERT INTO `s_sysresource` VALUES (260791000658739200, '修改用户密码', 'menu', 260767076386471936, '0/560767076386471936', '', '/system/user/toEditPwd', '/wengine/page/buildpage.shtml?pageId=560791000658739200', '', 18, '1', 1, '', '0', '1', 'admin', '2020-03-28 11:43:13', 'admin', '2020-04-26 10:56:25');
INSERT INTO `s_sysresource` VALUES (560767076386471936, 'DEMO示例', 'module', 0, '0', '', '', '', 'layui-icon-form', 17, '1', 1, '', '0', '1', 'admin', '2020-03-28 10:08:09', '', NULL);
INSERT INTO `s_sysresource` VALUES (560791000658739200, '用户管理列表', 'menu', 560767076386471936, '0/560767076386471936', '', '/system/userlist', '/wengine/page/buildpage.shtml?pageId=560791000658739200', '', 18, '1', 1, '', '0', '1', 'admin', '2020-03-28 11:43:13', 'admin', '2020-04-26 10:56:25');
INSERT INTO `s_sysresource` VALUES (561196302973534208, '新增用户信息', 'menu', 560767076386471936, '0/560767076386471936', '', '/system/userform', '/wengine/page/buildpage.shtml?pageId=561196302973534208', '', 22, '1', 1, '', '0', '1', 'admin', '2020-03-29 14:33:45', 'admin', '2020-04-17 12:49:02');
COMMIT;

-- ----------------------------
-- Table structure for s_sysrole
-- ----------------------------
DROP TABLE IF EXISTS `s_sysrole`;
CREATE TABLE `s_sysrole` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_mark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色描述',
  `is_authorized` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'false' COMMENT '是否可授权：false-否，true-是',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色表';

-- ----------------------------
-- Records of s_sysrole
-- ----------------------------
BEGIN;
INSERT INTO `s_sysrole` VALUES (587921646388510720, '全权限角色', 'test', 'false', '1', 'admin', '2020-04-11 08:30:43', 'admin', '2020-04-11 08:30:43');
INSERT INTO `s_sysrole` VALUES (629685823381438464, '部分权限角色', 'test3', 'false', '1', 'admin', '2020-04-11 08:30:43', 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_sysrole_relation
-- ----------------------------
DROP TABLE IF EXISTS `s_sysrole_relation`;
CREATE TABLE `s_sysrole_relation` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `role_id` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联类型：org-关联组织机构，job-职务，group-分组',
  `organization_id` bigint(20) DEFAULT NULL COMMENT '组织机构ID',
  `job_id` bigint(20) DEFAULT NULL COMMENT '工作职务ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '分组ID',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色关联表';

-- ----------------------------
-- Records of s_sysrole_relation
-- ----------------------------
BEGIN;
INSERT INTO `s_sysrole_relation` VALUES (598180107848253440, 587921646388510720, 'org', 574895547102527488, NULL, NULL, '1', 'admin', '2020-04-09 10:01:25', 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_sysrole_resource
-- ----------------------------
DROP TABLE IF EXISTS `s_sysrole_resource`;
CREATE TABLE `s_sysrole_resource` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `role_id` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(18) DEFAULT NULL COMMENT '资源ID',
  `haspermission_ids` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '*' COMMENT '页面有权限的btn资源合，逗号分隔。（选择资源表中当前resource_id的类型为button的子资源，都有权限设为*）',
  `nopermission_ids` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '*' COMMENT '页面没有权限的btn资源，逗号分隔。（选择资源表中当前resource_id的类型为button的子资源，都无权限设为all，不设默认*）',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色资源';

-- ----------------------------
-- Records of s_sysrole_resource
-- ----------------------------
BEGIN;
INSERT INTO `s_sysrole_resource` VALUES (198091330983297024, 629685823381438464, 560791000658739200, '*', '160791000658739202', '1', 'admin', '2020-04-09 10:01:25', '', NULL);
INSERT INTO `s_sysrole_resource` VALUES (198091330995879936, 629685823381438464, 560767076386471936, '*', '*', '1', 'admin', '2020-04-09 10:01:25', '', NULL);
INSERT INTO `s_sysrole_resource` VALUES (598091330966519808, 587921646388510720, 560767076386471936, '*', '*', '1', 'admin', '2020-04-09 10:01:25', '', NULL);
INSERT INTO `s_sysrole_resource` VALUES (598091330983297024, 587921646388510720, 560791000658739200, '*', '*', '1', 'admin', '2020-04-09 10:01:25', '', NULL);
INSERT INTO `s_sysrole_resource` VALUES (598091330995879936, 587921646388510720, 561196302973534208, '*', '*', '1', 'admin', '2020-04-09 10:01:25', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_sysuser
-- ----------------------------
DROP TABLE IF EXISTS `s_sysuser`;
CREATE TABLE `s_sysuser` (
  `rec_id` bigint NOT NULL COMMENT '主键ID',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户登录ID',
  `user_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密钥',
  `mobile_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
  `area_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属区域编码，存放区域表的area_code',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户邮箱',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '用户性别性别 1 男 0 女',
  `locked` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否锁定：0-锁定，1-正常',
  `last_online` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '登录状态：0-未登录，1-登录过',
  `is_admin` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否是管理员：0-否，1-是',
  `h_img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `user_level` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户等级。可以根据业务系统的特点进行分类，用以用户权益划分。',
  `organization_id` bigint DEFAULT NULL COMMENT '所属组织机构编码，存放组织机构表的rec_id',
  `person_sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '个性签名',
  `education` varchar(10) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户学历',
  `profession` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户职业',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户表';

-- ----------------------------
-- Records of s_sysuser
-- ----------------------------
BEGIN;
INSERT INTO `s_sysuser` VALUES (559056349858627584, 'admin', '超级管理员', '0473497177497d9db9de5e2cfda28746', 'kwNdUVv72K', '15659996982', '35', '274756462@qq.com', '0', '1', '2020-03-09 08:07:27', '1', '1', '', '1', 0, 'ha', '00', '', '1', 'admin', '2020-03-23 16:50:20', 'admin', '2020-03-09 21:08:06');
INSERT INTO `s_sysuser` VALUES (687965119522537472, 'user01', '用户01', '223e7e9eb770cbfb8c54f41f99c19dd1', 'MQcLNuBD3x', '18600139921', '35', '2343@qq.com', '1', '1', NULL, '1', '0', '720303751625179136.jpeg', NULL, NULL, '687965085234102272.jpeg,687965106725715968.jpg', '00', '', '1', 'admin', '2020-03-13 10:07:48', 'user01', '2020-06-10 15:50:03');
INSERT INTO `s_sysuser` VALUES (688065580208488448, 'user02', '用户02', '1bb5808ef2112cce083e5ef51db025dd', '7nm3EhhXIc', '18600139921', '3505', '2343@qq.com', '1', '1', NULL, '1', '0', '689213169272356864.jpeg', NULL, NULL, '688065572360945664.jpg', '00', '', '1', 'admin', '2020-03-13 16:47:01', 'user02', '2020-03-16 20:47:07');
INSERT INTO `s_sysuser` VALUES (689226129877237761, 'user03', '用户033', '0bccbe85972750bbc6fdf671b0478098', 'x9Dq0R1pDr', '18600139921', '3505', '2343@qq.com', '0', '1', NULL, '0', '0', '689226120272281600.jpeg', NULL, NULL, '', '00', '', '1', 'admin', '2020-03-16 21:38:34', NULL, '2020-06-10 15:47:40');
INSERT INTO `s_sysuser` VALUES (690966669203341312, 'abin', '用户05', '0c134f2739636608a3723c7fc7942f2a', 'CbfTi86p85', '15659109051', '350104', 'ttxx@qq.com', '1', '1', NULL, '0', '0', '690966219024498688.jpeg', NULL, NULL, '690966662735724544.jpg', '00', '', '1', 'admin', '2020-03-21 16:54:51', NULL, '2020-06-02 23:10:23');
COMMIT;

-- ----------------------------
-- Table structure for s_sysuser_auth
-- ----------------------------
DROP TABLE IF EXISTS `s_sysuser_auth`;
CREATE TABLE `s_sysuser_auth` (
  `rec_id` bigint(18) NOT NULL COMMENT '主键ID',
  `user_id` bigint(18) DEFAULT NULL COMMENT '用户ID',
  `auth_type` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权类型：role-按角色，org-按组织机构，job-按职务，group-按分组',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '记录状态',
  `organization_id` bigint(18) DEFAULT NULL COMMENT '组织机构ID',
  `job_id` bigint(18) DEFAULT NULL COMMENT '工作职务ID',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `role_id` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `group_id` bigint(18) DEFAULT NULL COMMENT '分组ID',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户授权表';

-- ----------------------------
-- Records of s_sysuser_auth
-- ----------------------------
BEGIN;
INSERT INTO `s_sysuser_auth` VALUES (687965119790972928, 687965119522537472, 'role', '1', NULL, NULL, '', '2020-03-13 10:07:48', '', '2020-03-16 21:37:28', 587921646388510720, NULL);
INSERT INTO `s_sysuser_auth` VALUES (688065580439175168, 688065580208488448, 'role', '1', NULL, NULL, '', '2020-03-13 16:47:01', '', '2020-03-16 20:47:07', 629685823381438464, NULL);
INSERT INTO `s_sysuser_auth` VALUES (689226130112118784, 689226129877237761, 'role', '1', NULL, NULL, '', '2020-03-16 21:38:34', '', '2020-03-16 21:39:12', 587921646388510720, NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_user_education
-- ----------------------------
DROP TABLE IF EXISTS `s_user_education`;
CREATE TABLE `s_user_education` (
  `rec_id` bigint NOT NULL COMMENT '主键ID',
  `edu_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学历编码',
  `edu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学历名称',
  `edu_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学历类型',
  `sort` int DEFAULT '0' COMMENT '显示排序',
  `rec_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '记录状态',
  `rec_person` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `rec_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rec_updateperson` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `rec_updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学历表';

-- ----------------------------
-- Records of s_user_education
-- ----------------------------
BEGIN;
INSERT INTO `s_user_education` VALUES (1, '01', '博士', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
INSERT INTO `s_user_education` VALUES (2, '02', '硕士', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
INSERT INTO `s_user_education` VALUES (3, '05', '本科', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
INSERT INTO `s_user_education` VALUES (4, '06', '专科', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
INSERT INTO `s_user_education` VALUES (5, '07', '高中', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
INSERT INTO `s_user_education` VALUES (6, '08', '初中', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
INSERT INTO `s_user_education` VALUES (7, '00', '未知', NULL, 0, '1', 'admin', '2020-06-10 22:14:49', 'admin', '2020-06-10 22:14:49');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
