/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.query;

import me.arnu.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 代码生成查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
@Data
public class GenTableQuery extends BaseQuery {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 表前缀
     */
    private String tablePrefix;
}
