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
 * 操作日志查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class OperLogQuery extends BaseQuery {

    /**
     * 模块标题
     */
    private String title;

    /**
     * 业务类型：0其它 1新增 2修改 3删除
     */
    private Integer businessType;

    /**
     * 操作类别：0其它 1后台用户 2手机端用户
     */
    private Integer operatorType;

    /**
     * 操作状态：1正常 2异常
     */
    private Integer status;

}
