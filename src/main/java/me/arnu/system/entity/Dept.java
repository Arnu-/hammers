package me.arnu.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级ID
     */
    private Integer pid;

    /**
     * 类型：1公司 2部门
     */
    private Integer type;

    /**
     * 是否有子级：1有 2无
     */
    private Integer hasChild;

    /**
     * 排序
     */
    private Integer sort;


}
