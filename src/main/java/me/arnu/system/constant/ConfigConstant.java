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
 * 系统配置 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public class ConfigConstant {

    /**
     * 配置类型
     */
    public static Map<String, String> CONFIG_TYPE_LIST = new HashMap<String, String>() {
        {
            put("text", "单行文本");
            put("textarea", "多行文本");
            put("ueditor", "富文本编辑器");
            put("date", "日期");
            put("datetime", "时间");
            put("number", "数字");
            put("select", "下拉框");
            put("radio", "单选框");
            put("checkbox", "复选框");
            put("image", "单张图片");
            put("images", "多张图片");
            put("password", "密码");
            put("icon", "字体图标");
            put("file", "单个文件");
            put("files", "多个文件");
            put("hidden", "隐藏");
            put("readonly", "只读文本");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> CONFIG_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "正常");
            put(2, "停用");
        }
    };
}