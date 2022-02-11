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
 * 登录日志查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class LoginLogQuery extends BaseQuery {

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录状态：1成功 2失败
     */
    private Integer status;

    /**
     * 类型：1登录系统 2退出系统
     */
    private Integer type;

}
