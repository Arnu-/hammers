/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 友链 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public class LinkConstant {

    /**
     * 类型
     */
    public static Map<Integer, String> LINK_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "友情链接");
            put(2, "合作伙伴");
        }
    };
    /**
     * 平台
     */
    public static Map<Integer, String> LINK_PLATFORM_LIST = new HashMap<Integer, String>() {
        {
            put(1, "PC站");
            put(2, "WAP站");
            put(3, "微信小程序");
            put(4, "APP应用");
        }
    };
    /**
     * 友链形式
     */
    public static Map<Integer, String> LINK_FORM_LIST = new HashMap<Integer, String>() {
        {
            put(1, "文字链接");
            put(2, "图片链接");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> LINK_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "在用");
            put(2, "停用");
        }
    };
}