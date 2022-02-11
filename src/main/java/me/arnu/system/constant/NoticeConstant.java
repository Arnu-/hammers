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
 * 通知公告 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
public class NoticeConstant {

    /**
     * 通知来源
     */
    public static Map<Integer, String> NOTICE_SOURCE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "云平台");
        }
    };
    /**
     * 是否置顶
     */
    public static Map<Integer, String> NOTICE_ISTOP_LIST = new HashMap<Integer, String>() {
        {
            put(1, "已置顶");
            put(2, "未置顶");
        }
    };
    /**
     * 发布状态
     */
    public static Map<Integer, String> NOTICE_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "草稿箱");
            put(2, "立即发布");
            put(3, "定时发布");
        }
    };
    /**
     * 推送状态
     */
    public static Map<Integer, String> NOTICE_ISSEND_LIST = new HashMap<Integer, String>() {
        {
            put(1, "已推送");
            put(2, "未推送");
        }
    };
}