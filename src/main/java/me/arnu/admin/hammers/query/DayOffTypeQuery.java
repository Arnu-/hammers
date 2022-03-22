/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.query;

import me.arnu.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 请假类型查询条件
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
public class DayOffTypeQuery extends BaseQuery {

    /**
     * 类型名称
     */
    private String name;

    /**
     * 员工号
     */
    private String empId;
}
