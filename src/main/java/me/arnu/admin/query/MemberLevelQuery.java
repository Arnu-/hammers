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
 * 会员等级查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class MemberLevelQuery extends BaseQuery {

    /**
     * 级别名称
     */
    private String name;

}
