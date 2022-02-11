/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.query;

import me.arnu.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 测试案例查询条件
 * </p>
 *
 * @author Arnu
 * @since 2021-01-29
 */
@Data
public class Test2Query extends BaseQuery {

    /**
     * 级别名称
     */
    private String name;

}
