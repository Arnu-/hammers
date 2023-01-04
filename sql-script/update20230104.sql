DROP TABLE IF EXISTS `ums_employee_v3`;
CREATE TABLE if not exists `ums_employee_v3`
(
    `id`              int(11)      NOT NULL AUTO_INCREMENT,
    `employee_id`     varchar(255) NOT NULL COMMENT '员工号',
    `realname`        varchar(255) DEFAULT NULL COMMENT '真实姓名',
    `nickname`        varchar(255) DEFAULT NULL COMMENT '昵称',
    `gender`          int(11)      DEFAULT NULL COMMENT '性别',
    `avatar`          varchar(255) DEFAULT NULL COMMENT '头像',
    `mobile`          varchar(255) DEFAULT NULL COMMENT '手机',
    `email`           varchar(255) DEFAULT NULL COMMENT '邮箱',
    `birthday`        datetime     DEFAULT NULL COMMENT '生日',
    `dept_id`         int(11)      DEFAULT NULL COMMENT '部门',
    `level_id`        int(11)      DEFAULT NULL COMMENT '级别',
    `position_id`     int(11)      DEFAULT NULL COMMENT '职位',
    `province_id`     int(11)      DEFAULT NULL COMMENT '省',
    `city_id`         int(11)      DEFAULT NULL COMMENT '市',
    `district_id`     int(11)      DEFAULT NULL COMMENT '区/县',
    `address`         varchar(255) DEFAULT NULL COMMENT '地址',
    `education`       varchar(255) DEFAULT NULL comment '学历',
    `graduation_date` datetime     DEFAULT NULL COMMENT '毕业日期',
    `enrollment_date` datetime     DEFAULT NULL COMMENT '入职日期',
    `formal_date`     datetime     DEFAULT NULL COMMENT '转正日期',
    `leave_date`      datetime     DEFAULT NULL COMMENT '离职日期',
    `work_year`       float        DEFAULT '0.1' COMMENT '工龄',
    `status`          int(11)      DEFAULT NULL COMMENT '状态',
    `note`            varchar(255) DEFAULT NULL COMMENT '备注',
    `create_user`     int(11)      DEFAULT '0' COMMENT '创建人',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_user`     int(11)      DEFAULT '0' COMMENT '更新人',
    `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',
    `mark`            int(11)      DEFAULT '1' COMMENT '有效标志',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

insert into ums_employee_v3 (`id`, `employee_id`, `realname`, `nickname`, `gender`, `avatar`,
                             `mobile`, `email`, `birthday`, `dept_id`, `level_id`, `position_id`,
                             `province_id`, `city_id`, `district_id`, `address`, `enrollment_date`,
                             `formal_date`, `leave_date`, `work_year`, `status`, `note`, `create_user`,
                             `create_time`, `update_user`, `update_time`, `mark`)
select `id`,
       `employee_id`,
       `realname`,
       `nickname`,
       `gender`,
       `avatar`,
       `mobile`,
       `email`,
       `birthday`,
       `dept_id`,
       `level_id`,
       `position_id`,
       `province_id`,
       `city_id`,
       `district_id`,
       `address`,
       `enrollment_date`,
       `formal_date`,
       `leave_date`,
       `work_year`,
       `status`,
       `note`,
       `create_user`,
       `create_time`,
       `update_user`,
       `update_time`,
       `mark`
from ums_employee;

DROP table if exists ums_employee_v2;

RENAME TABLE ums_employee TO ums_employee_v2;

RENAME TABLE ums_employee_v3 TO ums_employee;