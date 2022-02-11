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
 * 用户查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class UserQuery extends BaseQuery {

    /**
     * 姓名/手机号
     */
    private String keywords;

    /**
     * 性别:1男 2女 3保密
     */
    private Integer gender;

    /**
     * 状态：1正常 2禁用
     */
    private Integer status;

}
