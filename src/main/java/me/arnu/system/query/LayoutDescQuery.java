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
 * 布局描述查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class LayoutDescQuery extends BaseQuery {

    /**
     * 推荐位名称
     */
    private String name;

}
