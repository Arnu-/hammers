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
 * 部门查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class DeptQuery extends BaseQuery {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 类型：1公司 2部门
     */
    private Integer type;

    /**
     * 是否有子级：1是 2否
     */
    private Integer hasChild;

}
