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
 * 操作日志 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
public class OperLogConstant {

    /**
     * 业务类型
     */
    public static Map<Integer, String> OPERLOG_BUSINESSTYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "新增");
            put(2, "修改");
            put(3, "删除");
            put(10, "状态");
            put(11, "批量删除");
        }
    };
    /**
     * 操作类别
     */
    public static Map<Integer, String> OPERLOG_OPERATORTYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "后台用户");
            put(2, "WAP端用户");
            put(3, "移动端APP");
            put(4, "微信小程序");
        }
    };
    /**
     * 操作状态
     */
    public static Map<Integer, String> OPERLOG_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "正常");
            put(2, "异常");
        }
    };
}