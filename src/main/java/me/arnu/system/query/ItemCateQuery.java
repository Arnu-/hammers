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
 * 栏目查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class ItemCateQuery extends BaseQuery {

    /**
     * 上级ID
     */
    private Integer pid;

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 有无封面:1有 2无
     */
    private Integer isCover;

    /**
     * 状态：1启用 2停用
     */
    private Integer status;

}
