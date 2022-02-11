/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员用户 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
public class MemberConstant {

    /**
     * 性别
     */
    public static Map<Integer, String> MEMBER_GENDER_LIST = new HashMap<Integer, String>() {
        {
            put(1, "男");
            put(2, "女");
            put(3, "未知");
        }
    };
    /**
     * 设备类型
     */
    public static Map<Integer, String> MEMBER_DEVICE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "苹果");
            put(2, "安卓");
            put(3, "WAP站");
            put(4, "PC站");
            put(5, "微信小程序");
            put(6, "后台添加");
        }
    };
    /**
     * 用户状态
     */
    public static Map<Integer, String> MEMBER_LOGINSTATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "登录");
            put(2, "登出");
        }
    };
    /**
     * 用户来源
     */
    public static Map<Integer, String> MEMBER_SOURCE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "注册会员");
            put(2, "马甲会员");
        }
    };
    /**
     * 是否启用
     */
    public static Map<Integer, String> MEMBER_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "启用");
            put(2, "停用");
        }
    };
}