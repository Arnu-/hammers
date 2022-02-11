/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package ${packageName}.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * ${tableAnnotation} 模块常量
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${entityName}Constant {

<#if model_column?exists>
<#list model_column as model>
    <#if model.hasColumnCommentValue = true>
    /**
     * ${model.columnCommentName}
     */
    <#if model.columnNumberValue = true>
    public static Map<Integer, String> ${entityName?upper_case}_${model.changeColumnName?upper_case}_LIST = new HashMap<Integer, String>() {
        {
    <#if model.columnCommentValue?exists>
        <#list model.columnCommentValue?keys as key>
            put(${key}, "${model.columnCommentValue[key]}");
        </#list>
    </#if>
        }
    };
    <#else>
    public static Map<String, String> ${entityName?upper_case}_${model.changeColumnName?upper_case}_LIST = new HashMap<String, String>() {
    {
    <#if model.columnCommentValue?exists>
        <#list model.columnCommentValue?keys as key>
            put("${key}", "${model.columnCommentValue[key]}");
        </#list>
    </#if>
    }
    };
    </#if>
    </#if>
</#list>
</#if>
}