/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.query;

import me.arnu.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 系统配置查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class ConfigQuery extends BaseQuery {

    /**
     * 配置标题
     */
    private String title;

    /**
     * 配置类型：hidden=隐藏 readonly=只读文本 number=数字 text=单行文本 textarea=多行文本 array=数组 password=密码 radio=单选框 checkbox=复选框 select=下拉框 icon=字体图标 date=日期 datetime=时间 image=单张图片 images=多张图片 file=单个文件 files=多个文件 ueditor=富文本编辑器 json=JSON
     */
    private String type;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 分组ID
     */
    private Integer groupId;

}
