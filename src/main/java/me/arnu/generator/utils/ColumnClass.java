/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据表列
 */
@Data
public class ColumnClass {

    /**
     * 数据库字段名称
     **/
    private String columnName;

    /**
     * 数据库字段类型
     **/
    private String columnType;

    /**
     * 数据库字段首字母小写且去掉下划线字符串
     **/
    private String changeColumnName;

    /**
     * 数据库字段注释
     **/
    private String columnComment;

    /**
     * 是否有注解值
     */
    private boolean hasColumnCommentValue;

    /**
     * 是否选择开关
     */
    private boolean columnSwitch;

    /**
     * 数据库字段注释(仅包含名称)
     **/
    private String columnCommentName;

    /**
     * 数据库字段注解值
     */
    private Map<String, String> columnCommentValue = new HashMap<>();

    /**
     * 字段值(如：1淘宝 2京东 3拼多多,需转换成：1=淘宝,2=京东,3=拼多多)
     */
    private String columnValue;


    /**
     * 字段配置至是否是数字（特殊情况下，值不一定是数字，如：hidden=隐藏）
     */
    private boolean columnNumberValue;

    /**
     * 字段值开关(如：淘宝|京东)
     */
    private String columnSwitchValue;

    /**
     * 字段默认值
     */
    private String columnDefaultValue;

    /**
     * 是否是图片字段
     */
    private boolean columnImage;

    /**
     * 是否是多行文本
     */
    private boolean columnTextArea;

}
