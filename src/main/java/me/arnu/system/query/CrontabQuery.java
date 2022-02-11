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
 * 定时任务查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class CrontabQuery extends BaseQuery {

    /**
     * 任务标题
     */
    private String title;

    /**
     * 状态：1正常 2暂停
     */
    private Integer status;

}
