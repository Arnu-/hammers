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
 * 城市 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public class CityConstant {

    /**
     * 城市级别
     */
    public static Map<Integer, String> CITY_LEVEL_LIST = new HashMap<Integer, String>() {
        {
            put(1, "省份");
            put(2, "市区");
            put(3, "区县");
        }
    };
}