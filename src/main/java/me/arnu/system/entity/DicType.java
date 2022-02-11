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
 * 字典类型
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dic_type")
public class DicType extends BaseEntity {

    /**
     * 字典名称
     */
    private String name;

    /**
     * 标识符
     */
    private String tag;

    /**
     * 显示顺序
     */
    private Integer sort;

}