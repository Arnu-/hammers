/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 代码生成列 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
public class GenTableColumnConstant {

    /**
     * 是否主键
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISPK_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 是否自增
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISINCREMENT_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 是否必填
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISREQUIRED_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 是否为插入字段
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISINSERT_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 是否编辑字段
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISEDIT_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 是否列表字段
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISLIST_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
    /**
     * 是否查询字段
     */
    public static Map<Integer, String> GENTABLECOLUMN_ISQUERY_LIST = new HashMap<Integer, String>() {
        {
            put(1, "是");
            put(2, "否");
        }
    };
}