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
 * 配置分组查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class ConfigGroupQuery extends BaseQuery {

    /**
     * 分组名称
     */
    private String name;

}
