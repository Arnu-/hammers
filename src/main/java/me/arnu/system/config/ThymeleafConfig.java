/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.config;

import me.arnu.system.constant.*;
import me.arnu.system.constant.*;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Thymeleaf模板配置
 */
@Configuration
public class ThymeleafConfig {
    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            /**
             * 职级状态
             */
            vars.put("LEVEL_STATUS_LIST", LevelConstant.LEVEL_STATUS_LIST);
            /**
             * 岗位状态
             */
            vars.put("POSITION_STATUS_LIST", PositionConstant.POSITION_STATUS_LIST);
            /**
             * 站点类型
             */
            vars.put("ITEM_TYPE_LIST", ItemConstant.ITEM_TYPE_LIST);
            /**
             * 栏目有无图片
             */
            vars.put("ITEMCATE_ISCOVER_LIST", ItemCateConstant.ITEMCATE_ISCOVER_LIST);
            /**
             * 栏目状态
             */
            vars.put("ITEMCATE_STATUS_LIST", ItemCateConstant.ITEMCATE_STATUS_LIST);
            /**
             * 站点域名是否是二级域名
             */
            vars.put("ITEM_ISDOMAIN_LIST", ItemConstant.ITEM_ISDOMAIN_LIST);
            /**
             * 站点状态
             */
            vars.put("ITEM_STATUS_LIST", ItemConstant.ITEM_STATUS_LIST);
            /**
             * 角色状态
             */
            vars.put("ROLE_STATUS_LIST", RoleConstant.ROLE_STATUS_LIST);

            /**
             * 广告位类型
             */
            vars.put("ADSORT_PLATFORM_LIST", AdSortConstant.ADSORT_PLATFORM_LIST);
            /**
             * 广告类型
             */
            vars.put("AD_TYPE_LIST", AdConstant.AD_TYPE_LIST);
            /**
             * 广告状态
             */
            vars.put("AD_STATUS_LIST", AdConstant.AD_STATUS_LIST);
            /**
             * 人员性别
             */
            vars.put("USER_GENDER_LIST", UserConstant.USER_GENDER_LIST);
            /**
             * 人员状态
             */
            vars.put("USER_STATUS_LIST", UserConstant.USER_STATUS_LIST);
            /**
             * 部门类型
             */
            vars.put("DEPT_TYPE_LIST", DeptConstant.DEPT_TYPE_LIST);
            /**
             * 部门是否有子级
             */
            vars.put("DEPT_HASCHILD_LIST", DeptConstant.DEPT_HASCHILD_LIST);
            /**
             * 城市级别
             */
            vars.put("CITY_LEVEL_LIST", CityConstant.CITY_LEVEL_LIST);
            /**
             * 配置状态
             */
            vars.put("CONFIG_STATUS_LIST", ConfigConstant.CONFIG_STATUS_LIST);
            /**
             * 配置类型
             */
            vars.put("CONFIG_TYPE_LIST", ConfigConstant.CONFIG_TYPE_LIST);
            /**
             * 定时任务状态
             */
            vars.put("CRONTAB_STATUS_LIST", CrontabConstant.CRONTAB_STATUS_LIST);
            /**
             * 字典状态
             */
            vars.put("DIC_STATUS_LIST", DicConstant.DIC_STATUS_LIST);
            /**
             * 布局类型
             */
            vars.put("LAYOUT_TYPE_LIST", LayoutConstant.LAYOUT_TYPE_LIST);
            /**
             * 友链类型
             */
            vars.put("LINK_TYPE_LIST", LinkConstant.LINK_TYPE_LIST);
            /**
             * 友链平台
             */
            vars.put("LINK_PLATFORM_LIST", LinkConstant.LINK_PLATFORM_LIST);
            /**
             * 友链形式
             */
            vars.put("LINK_FORM_LIST", LinkConstant.LINK_FORM_LIST);
            /**
             * 友链状态
             */
            vars.put("LINK_STATUS_LIST", LinkConstant.LINK_STATUS_LIST);
            /**
             * 登录日志状态
             */
            vars.put("LOGINLOG_STATUS_LIST", LoginLogConstant.LOGINLOG_STATUS_LIST);
            /**
             * 登录日志类型
             */
            vars.put("LOGINLOG_TYPE_LIST", LoginLogConstant.LOGINLOG_TYPE_LIST);
            /**
             * 消息模板类型
             */
            vars.put("MESSAGETEMPLATE_TYPE_LIST", MessageTemplateConstant.MESSAGETEMPLATE_TYPE_LIST);
            /**
             * 消息模板状态
             */
            vars.put("MESSAGETEMPLATE_STATUS_LIST", MessageTemplateConstant.MESSAGETEMPLATE_STATUS_LIST);
            /**
             * 消息类型
             */
            vars.put("MESSAGE_TYPE_LIST", MessageConstant.MESSAGE_TYPE_LIST);
            /**
             * 消息发送状态
             */
            vars.put("MESSAGE_SENDSTATUS_LIST", MessageConstant.MESSAGE_SENDSTATUS_LIST);
            /**
             * 通知公告来源
             */
            vars.put("NOTICE_SOURCE_LIST", NoticeConstant.NOTICE_SOURCE_LIST);
            /**
             * 通知公告是否已置顶
             */
            vars.put("NOTICE_ISTOP_LIST", NoticeConstant.NOTICE_ISTOP_LIST);
            /**
             * 通知公告是否已发布
             */
            vars.put("NOTICE_STATUS_LIST", NoticeConstant.NOTICE_STATUS_LIST);
            /**
             * 通知公告是否已推送
             */
            vars.put("NOTICE_ISSEND_LIST", NoticeConstant.NOTICE_ISSEND_LIST);
            /**
             * 操作日志业务类型
             */
            vars.put("OPERLOG_BUSINESSTYPE_LIST", OperLogConstant.OPERLOG_BUSINESSTYPE_LIST);
            /**
             * 操作日志操作类别
             */
            vars.put("OPERLOG_OPERATORTYPE_LIST", OperLogConstant.OPERLOG_OPERATORTYPE_LIST);
            /**
             * 操作日志状态
             */
            vars.put("OPERLOG_STATUS_LIST", OperLogConstant.OPERLOG_STATUS_LIST);
            /**
             * 短信发送类型
             */
            vars.put("SMSLOG_TYPE_LIST", SmsLogConstant.SMSLOG_TYPE_LIST);
            /**
             * 短信发送状态
             */
            vars.put("SMSLOG_STATUS_LIST", SmsLogConstant.SMSLOG_STATUS_LIST);
            /**
             * 菜单类型
             */
            vars.put("MENU_TYPE_LIST", MenuConstant.MENU_TYPE_LIST);
            /**
             * 菜单显示状态
             */
            vars.put("MENU_STATUS_LIST", MenuConstant.MENU_STATUS_LIST);
            /**
             * 是否公共菜单
             */
            vars.put("MENU_ISPUBLIC_LIST", MenuConstant.MENU_ISPUBLIC_LIST);
            viewResolver.setStaticVariables(vars);
        }
    }
}
