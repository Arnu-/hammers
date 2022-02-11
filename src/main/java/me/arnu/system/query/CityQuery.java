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
 * 城市查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class CityQuery extends BaseQuery {

    /**
     * 上级ID
     */
    private Integer pid;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 城市级别：1省份 2市区 3区县
     */
    private Integer level;

}
