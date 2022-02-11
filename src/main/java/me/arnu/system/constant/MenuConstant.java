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
 * 菜单 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-07
 */
public class MenuConstant {

    /**
     * 类型
     */
    public static Map<Integer, String> MENU_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "模块");
            put(2, "导航");
            put(3, "菜单");
            put(4, "节点");
        }
    };
    /**
     * 是否显示
     */
    public static Map<Integer, String> MENU_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "显示");
            put(2, "不显示");
        }
    };
    /**
     * 是否公共
     */
    public static Map<Integer, String> MENU_ISPUBLIC_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
}