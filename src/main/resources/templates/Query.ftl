/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package ${packageName}.query;

import me.arnu.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * ${tableAnnotation}查询条件
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
public class ${entityName}Query extends BaseQuery {

<#if model_column?exists>
    <#list model_column as model>
    <#if model.columnName = 'name'>
    /**
     * ${model.columnComment}
     */
    private String ${model.changeColumnName?uncap_first};

    </#if>
    <#if model.columnName = 'title'>
    /**
     * ${model.columnComment}
     */
    private String ${model.changeColumnName?uncap_first};

    </#if>
    <#if model.columnName = 'mobile'>
    /**
     * ${model.columnComment}
     */
    private String ${model.changeColumnName?uncap_first};

    </#if>
    <#if model.hasColumnCommentValue = true>
    /**
     * ${model.columnComment}
     */
    <#if model.columnNumberValue = true>
    private Integer ${model.changeColumnName?uncap_first};
    <#else>
    private String ${model.changeColumnName?uncap_first};
    </#if>

    </#if>
    </#list>
</#if>
}
