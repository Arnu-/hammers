/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 布局
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_layout")
public class Layout extends BaseEntity {

    /**
     * 页面编号
     */
    private Integer itemId;

    /**
     * 页面的位置
     */
    private Integer locId;

    /**
     * 类型：1新闻资讯 2其他
     */
    private Integer type;

    /**
     * 对应的类型编号
     */
    private Integer typeId;

    /**
     * 图片路径
     */
    private String image;

    /**
     * 显示顺序
     */
    private Integer sort;

}