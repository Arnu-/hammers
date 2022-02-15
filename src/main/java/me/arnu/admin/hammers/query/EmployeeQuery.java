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
 * 员工查询条件
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
public class EmployeeQuery extends BaseQuery {

    /**
     * 手机, 姓名共用。
     */
    private String mobile;

}
