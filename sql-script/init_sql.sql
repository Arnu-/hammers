CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `param` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT '0',
  `type` int(11) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `is_public` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ;


INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (1,'系统管理','layui-icon-login-wechat','#','',0,1,'',1,1,'',1,1,1),
	 (2,'权限管理','layui-icon-set','#','',1,2,'',1,2,'',1,1,1),
	 (3,'用户管理','layui-icon-username','/user/index','',2,3,'sys:user:index',1,2,'',1,1,1),
	 (4,'列表','layui-icon-home','/user/list','',3,4,'sys:user:list',1,2,'',1,1,1),
	 (5,'添加','layui-icon-home','/user/add','',3,4,'sys:user:add',1,2,'',2,1,1),
	 (6,'修改','layui-icon-home','/user/update','',3,4,'sys:user:update',1,2,'',3,1,1),
	 (7,'删除','layui-icon-home','/user/delete','',3,4,'sys:user:delete',1,2,'',4,1,1),
	 (8,'状态','layui-icon-home','/user/setStatus','',3,4,'sys:user:status',1,2,'',5,1,1),
	 (9,'批量删除','','/user/batchDelete','',3,4,'sys:user:dall',1,2,'',6,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (10,'角色管理','layui-icon-group','/role/index','',2,3,'',1,2,'',2,1,1),
	 (11,'列表','layui-icon-home','/role/list','',10,4,'sys:role:list',1,2,'',1,1,1),
	 (12,'添加','layui-icon-home','/role/add','',10,4,'sys:role:add',1,2,'',5,1,1),
	 (13,'修改','layui-icon-home','/role/update','',10,4,'sys:role:update',1,2,'',10,1,1),
	 (14,'删除','layui-icon-home','/role/delete','',10,4,'sys:role:delete',1,2,'',15,1,1),
	 (15,'状态','','/role/setStatus','',10,4,'sys:role:status',1,2,'',20,1,1),
	 (16,'批量删除','','/role/batchDelete','',10,4,'sys:role:dall',1,2,'',25,1,1),
	 (17,'权限分配','layui-icon-home','/role/permission','',10,4,'sys:role:permission',1,2,'',30,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (18,'职级管理','layui-icon-auz','/level/index','',2,3,'sys:level:index',2,2,'',3,1,1),
	 (19,'列表','','/level/list','',18,4,'sys:level:list',1,2,'',1,1,1),
	 (20,'添加','','/level/add','',18,4,'sys:level:add',1,2,'',5,1,1),
	 (21,'修改','','/level/update','',18,4,'sys:level:update',1,2,'',10,1,1),
	 (22,'删除','','/level/delete','',18,4,'sys:level:delete',1,2,'',15,1,1),
	 (23,'状态','layui-icon-home','/level/setStatus','',18,4,'sys:level:status',1,2,'',20,1,1),
	 (24,'批量删除','','/level/batchDelete','',18,4,'sys:level:dall',1,2,'',25,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (25,'岗位管理','layui-icon-home','/position/index','',2,3,'sys:position:index',2,2,'',4,1,1),
	 (26,'列表','','/position/list','',25,4,'sys:position:list',1,2,'',1,1,1),
	 (27,'添加','','/position/add','',25,4,'sys:position:add',1,2,'',5,1,1),
	 (28,'修改','','/position/update','',25,4,'sys:position:update',1,2,'',10,1,1),
	 (29,'删除','','/position/delete','',25,4,'sys:position:delete',1,2,'',15,1,1),
	 (30,'状态','','/position/setStatus','',25,4,'sys:position:status',1,2,'',20,1,1),
	 (31,'批量删除','','/position/batchDelete','',25,4,'sys:position:dall',1,2,'',25,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (32,'部门管理','layui-icon-home','/dept/index','',2,3,'sys:dept:index',2,2,'',5,1,1),
	 (33,'列表','','/dept/list','',32,4,'sys:dept:list',1,2,'',1,1,1),
	 (34,'添加','','/dept/add','',32,4,'sys:dept:add',1,2,'',5,1,1),
	 (35,'修改','','/dept/update','',32,4,'sys:dept:update',1,2,'',10,1,1),
	 (36,'删除','','/dept/delete','',32,4,'sys:dept:delete',1,2,'',15,1,1),
	 (37,'批量删除','','/dept/batchDelete','',32,4,'sys:dept:dall',1,2,'',25,1,1),
	 (38,'全部展开','','/dept/expand','',32,4,'sys:dept:expand',1,2,'',30,1,1),
	 (39,'全部折叠','','/dept/collapse','',32,4,'sys:dept:collapse',1,2,'',35,1,1),
	 (40,'添加子级','','/dept/addz','',32,4,'sys:dept:addz',1,2,'',40,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (41,'菜单管理','layui-icon-home','/menu/index','',2,3,'sys:menu:index',1,2,'',6,1,1),
	 (42,'列表','','/menu/list','',41,4,'sys:menu:list',1,2,'',1,1,1),
	 (43,'添加','','/menu/add','',41,4,'sys:menu:add',1,2,'',5,1,1),
	 (44,'修改','','/menu/update','',41,4,'sys:menu:update',1,2,'',10,1,1),
	 (45,'删除','','/menu/delete','',41,4,'sys:menu:delete',1,2,'',15,1,1),
	 (46,'状态','','/menu/setStatus','',41,4,'sys:menu:status',1,2,'',20,1,1),
	 (47,'批量删除','','/menu/batchDelete','',41,4,'sys:menu:dall',1,2,'',25,1,1),
	 (48,'全部展开','','/menu/expand','',41,4,'sys:menu:expand',1,2,'',30,1,1),
	 (49,'全部折叠','','/menu/collapse','',41,4,'sys:menu:collapse',1,2,'',35,1,1),
	 (50,'添加子级','','/menu/addz','',41,4,'sys:menu:addz',1,2,'',40,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (51,'操作日志','layui-icon-home','/operlog/index','',2,3,'sys:operlog:index',1,2,'',7,1,1),
	 (52,'列表','','/operlog/list','',51,4,'sys:operlog:list',1,2,'',1,1,1),
	 (53,'删除','','/operlog/delete','',51,4,'sys:operlog:delete',1,2,'',15,1,1),
	 (54,'批量删除','','/operlog/batchDelete','',51,4,'sys:operlog:dall',1,2,'',25,1,1),
	 (55,'登录日志','layui-icon-home','/loginlog/index','',2,3,'sys:loginlog:index',1,2,'',8,1,1),
	 (56,'列表','','/loginlog/list','',55,4,'sys:loginlog:list',1,2,'',1,1,1),
	 (57,'删除','','/loginlog/delete','',55,4,'sys:loginlog:delete',1,2,'',15,1,1),
	 (58,'批量删除','','/loginlog/batchDelete','',55,4,'sys:loginlog:dall',1,2,'',25,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (59,'字典管理','layui-icon-app','#','',1,2,'',2,2,'',2,1,1),
	 (60,'字典类型','layui-icon-home','/dictype/index','',59,3,'sys:dictype:index',1,2,'',1,1,1),
	 (61,'列表','','/dictype/list','',60,4,'sys:dictype:list',1,2,'',1,1,1),
	 (62,'添加','','/dictype/add','',60,4,'sys:dictype:add',1,2,'',5,1,1),
	 (63,'修改','','/dictype/update','',60,4,'sys:dictype:update',1,2,'',10,1,1),
	 (64,'删除','','/dictype/delete','',60,4,'sys:dictype:delete',1,2,'',15,1,1),
	 (65,'批量删除','','/dictype/batchDelete','',60,4,'sys:dictype:dall',1,2,'',25,1,1),
	 (66,'字典管理','layui-icon-home','/dic/index','',59,3,'sys:dic:index',1,2,'',5,1,1),
	 (67,'列表','','/dic/list','',66,4,'sys:dic:list',1,2,'',1,1,1),
	 (68,'添加','','/dic/add','',66,4,'sys:dic:add',1,2,'',5,1,1),
	 (69,'修改','','/dic/update','',66,4,'sys:dic:update',1,2,'',10,1,1),
	 (70,'删除','','/dic/delete','',66,4,'sys:dic:delete',1,2,'',15,1,1),
	 (71,'状态','','/dic/setStatus','',66,4,'sys:dic:status',1,2,'',20,1,1),
	 (72,'批量删除','','/dic/batchDelete','',66,4,'sys:dic:dall',1,2,'',25,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (73,'应用中心','layui-icon-theme','#','',1,2,'',2,2,'',3,1,1),
	 (74,'行政区划','layui-icon-home','/city/index','',73,3,'sys:index:index',1,2,'',5,1,1),
	 (75,'列表','','/city/list','',74,4,'sys:city:list',1,2,'',1,1,1),
	 (76,'添加','','/city/add','',74,4,'sys:city:add',1,2,'',5,1,1),
	 (77,'修改','','/city/update','',74,4,'sys:city:update',1,2,'',10,1,1),
	 (78,'删除','','/city/delete','',74,4,'sys:city:delete',1,2,'',15,1,1),
	 (79,'批量删除','','/city/batchDelete','',74,4,'sys:city:dall',1,2,'',25,1,1),
	 (80,'全部展开','','/city/expand','',74,4,'sys:city:expand',1,2,'',30,1,1),
	 (81,'全部折叠','','/city/collapse','',74,4,'sys:city:collapse',1,2,'',35,1,1),
	 (82,'添加子级','','/city/addz','',74,4,'sys:city:addz',1,2,'',40,1,1),
	 (83,'定时任务','layui-icon-home','/crontab/index','',73,3,'sys:crontab:index',1,2,'',10,1,1),
	 (84,'列表','','/crontab/list','',83,4,'sys:crontab:list',1,2,'',1,1,1),
	 (85,'添加','','/crontab/add','',83,4,'sys:crontab:add',1,2,'',5,1,1),
	 (86,'修改','','/crontab/update','',83,4,'sys:crontab:update',1,2,'',10,1,1),
	 (87,'删除','','/crontab/delete','',83,4,'sys:crontab:delete',1,2,'',15,1,1),
	 (88,'状态','','/crontab/setStatus','',83,4,'sys:crontab:status',1,2,'',20,1,1),
	 (89,'批量删除','','/crontab/batchDelete','',83,4,'sys:crontab:dall',1,2,'',25,1,1);
INSERT INTO sys_menu (id,name,icon,url,param,pid,`type`,permission,status,is_public,note,sort,create_user,mark) VALUES
	 (90,'系统工具','layui-icon-set','#','',1,2,'',2,2,'',4,1,1),
	 (91,'代码生成','layui-icon-home','/gentable/index','',90,3,'sys:gentable:index',1,2,'',1,1,1),
	 (92,'列表','','/gentable/list','',91,4,'sys:gentable:list',1,2,'',1,1,1),
	 (93,'导入数据表','layui-icon-home','/gentable/importTable','',91,4,'sys:gentable:importTable',1,2,'',5,1,1),
	 (94,'修改','','/gentable/update','',91,4,'sys:gentable:update',1,2,'',10,1,1),
	 (95,'删除','','/gentable/delete','',91,4,'sys:gentable:delete',1,2,'',15,1,1),
	 (96,'生成代码','layui-icon-home','/gentable/generator','',91,4,'sys:gentable:generator',1,2,'',20,1,1),
	 (97,'批量删除','','/gentable/batchDelete','',91,4,'sys:gentable:dall',1,2,'',25,1,1),
	 (98,'导出数据','','/level/export','',18,4,'sys:level:export',1,2,'',45,0,1),
	 (99,'导入数据','','/level/import','',18,4,'sys:level:import',1,2,'',50,0,1);

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `realname` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `position_id` int(11) DEFAULT NULL,
  `province_id` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `district_id` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `login_num` int(11) DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

INSERT INTO sys_user (id,realname,nickname,gender,avatar,username,password,status,create_user,mark) VALUES
	 (1,'系统默认管理员','超级管理员',1,'/images/user/20210129/20210129134048270.jpg','admin','ddd056e3116ef3c8972a11c62a770a31',1,1,1);

CREATE TABLE `sys_role` (
  `name` varchar(255) DEFAULT NULL,
  `rules` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

-- 插入默认角色
INSERT INTO sys_role (name,rules,status,sort,create_user,mark) VALUES
	 ('超级管理员','',1,1,1,1),
	 ('管理员','',1,5,1,1);

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_login_log` (
  `title` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `login_location` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ;
CREATE TABLE `sys_oper_log` (
  `title` varchar(255) DEFAULT NULL,
  `business_type` int(11) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `request_method` varchar(255) DEFAULT NULL,
  `operator_type` int(11) DEFAULT NULL,
  `oper_name` varchar(255) DEFAULT NULL,
  `oper_url` varchar(255) DEFAULT NULL,
  `oper_ip` varchar(255) DEFAULT NULL,
  `oper_location` varchar(255) DEFAULT NULL,
  `oper_param` varchar(2000) DEFAULT NULL,
  `json_result` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `error_msg` text,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_gen_table` (
  `table_name` varchar(255) DEFAULT NULL,
  `table_prefix` varchar(255) DEFAULT NULL,
  `table_comment` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `package_name` varchar(255) DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `business_name` varchar(255) DEFAULT NULL,
  `function_name` varchar(255) DEFAULT NULL,
  `function_author` varchar(255) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `sys_gen_table_column` (
  `table_id` int(11) DEFAULT NULL,
  `column_name` varchar(255) DEFAULT NULL,
  `column_comment` varchar(255) DEFAULT NULL,
  `column_type` varchar(255) DEFAULT NULL,
  `java_type` varchar(255) DEFAULT NULL,
  `java_field` varchar(255) DEFAULT NULL,
  `is_pk` int(11) DEFAULT NULL,
  `is_increment` int(11) DEFAULT NULL,
  `is_required` int(11) DEFAULT NULL,
  `is_insert` int(11) DEFAULT NULL,
  `is_edit` int(11) DEFAULT NULL,
  `is_list` int(11) DEFAULT NULL,
  `is_query` int(11) DEFAULT NULL,
  `query_type` varchar(255) DEFAULT NULL,
  `html_type` varchar(255) DEFAULT NULL,
  `dict_type` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_city` (
  `name` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT '0',
  `citycode` varchar(255) DEFAULT NULL,
  `p_adcode` varchar(255) DEFAULT NULL,
  `adcode` varchar(255) DEFAULT NULL,
  `lng` int(11) DEFAULT NULL,
  `lat` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_dept` (
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT '0',
  `type` int(11) DEFAULT NULL,
  `has_child` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_level` (
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_position` (
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_user` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `mark` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
);

CREATE TABLE `ums_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门',
  `level_id` int(11) DEFAULT NULL COMMENT '级别',
  `position_id` int(11) DEFAULT NULL COMMENT '职位',
  `province_id` int(11) DEFAULT NULL COMMENT '省',
  `city_id` int(11) DEFAULT NULL COMMENT '市',
  `district_id` int(11) DEFAULT NULL COMMENT '区/县',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `city_name` varchar(255) DEFAULT NULL COMMENT '城市名',
  `enrollment_date` datetime DEFAULT NULL COMMENT '入职日期',
  `formal_date` datetime DEFAULT NULL COMMENT '转正日期',
  `leave_date` datetime DEFAULT NULL COMMENT '离职日期',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` int(11) DEFAULT '1' COMMENT '有效标志',
  PRIMARY KEY (`id`)
) comment='员工';


CREATE TABLE `ums_level_annual_vacation_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_id` int(11) not null comment '级别id',
  `days` int (11) not null default 5 comment '年假基数',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` int(11) DEFAULT '1' COMMENT '有效标志',
  PRIMARY KEY (`id`)
) comment='级别年假基数';

CREATE TABLE `ums_employee_special_annual_vacation_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) not null comment '员工',
  `days` int (11) not null default 5 comment '年假基数',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` int(11) DEFAULT '1' COMMENT '有效标志',
  PRIMARY KEY (`id`)
) comment = '员工年假特殊设置';

CREATE TABLE `ums_day_off_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT null comment '类型名称',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` int(11) DEFAULT '1' COMMENT '有效标志',
  PRIMARY KEY (`id`)
) comment='请假类型';

CREATE TABLE `ums_ask_for_day_off_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT null comment '员工',
  `day_off_type_id` int(11) NOT null comment '请假类型',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `days` float default 0 comment '请假天数',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` int(11) DEFAULT '1' COMMENT '有效标志',
  PRIMARY KEY (`id`)
) comment='请假记录';

