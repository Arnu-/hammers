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
 * 站点 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
public class ItemConstant {

    /**
     * 站点类型
     */
    public static Map<Integer, String> ITEM_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "普通站点");
            put(2, "其他");
        }
    };
    /**
     * 是否二级域名
     */
    public static Map<Integer, String> ITEM_ISDOMAIN_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> ITEM_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "在用");
            put(2, "停用");
        }
    };
}