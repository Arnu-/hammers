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
 * 系统角色查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class RoleQuery extends BaseQuery {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态：1正常 2禁用
     */
    private Integer status;

}
