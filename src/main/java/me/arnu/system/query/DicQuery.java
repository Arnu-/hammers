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
 * 字典查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class DicQuery extends BaseQuery {

    /**
     * 字典名称
     */
    private String title;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

}
