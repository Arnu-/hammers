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
 * 广告 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public class AdConstant {

    /**
     * 广告格式
     */
    public static Map<Integer, String> AD_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "图片");
            put(2, "文字");
            put(3, "视频");
            put(4, "推荐");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> AD_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "在用");
            put(2, "停用");
        }
    };
}