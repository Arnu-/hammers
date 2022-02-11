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
 * 布局描述
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_layout_desc")
public class LayoutDesc extends BaseEntity {

    /**
     * 推荐位名称
     */
    private String name;

    /**
     * 页面编号
     */
    private Integer itemId;

    /**
     * 位置编号
     */
    private Integer locId;

    /**
     * 页面位置描述
     */
    private String locDesc;

    /**
     * 排序
     */
    private Integer sort;

}