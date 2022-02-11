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
 * 布局查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class LayoutQuery extends BaseQuery {

    /**
     * 类型：1新闻资讯 2其他
     */
    private Integer type;

}
