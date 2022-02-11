/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.config;

import me.arnu.admin.constant.*;
import me.arnu.admin.constant.MemberConstant;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Thymeleaf模板配置
 */
@Configuration
public class ThymeleafAConfig {

    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();

            /**
             * 会员性别
             */
            vars.put("MEMBER_GENDER_LIST", MemberConstant.MEMBER_GENDER_LIST);
            /**
             * 会员设备类型
             */
            vars.put("MEMBER_DEVICE_LIST", MemberConstant.MEMBER_DEVICE_LIST);
            /**
             * 会员登陆状态
             */
            vars.put("MEMBER_LOGINSTATUS_LIST", MemberConstant.MEMBER_LOGINSTATUS_LIST);
            /**
             * 会员注册来源
             */
            vars.put("MEMBER_SOURCE_LIST", MemberConstant.MEMBER_SOURCE_LIST);
            /**
             * 会员状态
             */
            vars.put("MEMBER_STATUS_LIST", MemberConstant.MEMBER_STATUS_LIST);
            viewResolver.setStaticVariables(vars);
        }
    }
}
