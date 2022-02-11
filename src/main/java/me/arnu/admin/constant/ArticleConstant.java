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
 * 文章管理 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-08-11
 */
public class ArticleConstant {

    /**
     * 是否置顶
     */
    public static Map<Integer, String> ARTICLE_ISTOP_LIST = new HashMap<Integer, String>() {
        {
            put(1, "已置顶");
            put(2, "未置顶");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> ARTICLE_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "已审核");
            put(2, "待审核");
            put(3, "审核失败");
        }
    };
}