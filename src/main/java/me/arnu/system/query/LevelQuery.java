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
 * 职级查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class LevelQuery extends BaseQuery {

    /**
     * 职级名称
     */
    private String name;

    /**
     * 状态：1正常 2停用
     */
    private Integer status;

    /**
     * 选中数据ID集合
     */
    private String ids;

}
