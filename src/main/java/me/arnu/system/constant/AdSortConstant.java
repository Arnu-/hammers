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
 * 广告位描述 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-01
 */
public class AdSortConstant {

    /**
     * 站点类型
     */
    public static Map<Integer, String> ADSORT_PLATFORM_LIST = new HashMap<Integer, String>() {
        {
            put(1, "PC网站");
            put(2, "WAP手机站");
            put(3, "微信小程序");
            put(4, "APP移动端");
        }
    };
}