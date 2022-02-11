/**
 * ${tableAnnotation}
 * @auth ${author}
 * @date ${date}
 */
layui.use(['func'], function () {

    //声明变量
    var func = layui.func
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            <#if hasPid = true>
              {field: 'id', width: 80, title: 'ID', align: 'center', sort: true}
            <#else>
              {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            </#if>
<#if model_column?exists>
    <#list model_column as model>
    <#if model.columnName != 'mark'>
        <#if (model.columnType = 'DATETIME' || model.columnType = 'DATE' || model.columnType = 'TIME' || model.columnType = 'YEAR' || model.columnType = 'TIMESTAMP')>
            , {field: '${model.changeColumnName?uncap_first}', width: 180, title: '${model.columnComment}', align: 'center'}
        <#elseif model.hasColumnCommentValue = true>
            <#if (model.changeColumnName?uncap_first = 'status' && model.columnSwitch == true)>
            , {field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl'}
            <#else>
            , {field: '${model.changeColumnName?uncap_first}', width: 100, title: '${model.columnCommentName}', align: 'center', templet(d) {
            <#assign column = "d.${model.changeColumnName?uncap_first}">
                var cls = "";
                <#if model.columnCommentValue?exists>
                <#list model.columnCommentValue?keys as key>
                <#if key = "1">
                if (${column} == 1) {
                    // ${model.columnCommentValue[key]}
                    cls = "layui-btn-normal";
                } <#elseif key = "2">else if (${column} == 2) {
                    // ${model.columnCommentValue[key]}
                    cls = "layui-btn-danger";
                } <#elseif key = "3">else if (${column} == 3) {
                    // ${model.columnCommentValue[key]}
                    cls = "layui-btn-warm";
                } <#elseif key = "4">else if (${column} == 4) {
                    // ${model.columnCommentValue[key]}
                    cls = "layui-btn-primary";
                } <#elseif key = "5">else if (${column} == 5) {
                    // ${model.columnCommentValue[key]}
                    cls = "layui-btn-disabled";
                }
                </#if>
                </#list>

				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.${model.changeColumnName?uncap_first}Name+'</span>';
                </#if>
            }}
            </#if>
        <#elseif model.columnImage == true>
            , {field: '${model.changeColumnName?uncap_first}', width: 100, title: '${model.columnComment}', align: 'center', templet: function (d) {
                var ${model.changeColumnName?uncap_first}Str = "";
                if (d.${model.changeColumnName?uncap_first}Url) {
                    ${model.changeColumnName?uncap_first}Str = '<a href="' + d.${model.changeColumnName?uncap_first}Url + '" target="_blank"><img src="' + d.${model.changeColumnName?uncap_first}Url + '" height="26" /></a>';
                }
                return ${model.changeColumnName?uncap_first}Str;
              }
            }
        <#elseif (model.changeColumnName?uncap_first = "createUser" || model.changeColumnName?uncap_first = "updateUser")>
            , {field: '${model.changeColumnName?uncap_first}Name', width: 100, title: '${model.columnComment}', align: 'center'}
        <#else>
            , {field: '${model.changeColumnName?uncap_first}', width: 100, title: '${model.columnComment}', align: 'center'}
        </#if>
    </#if>
    </#list>
</#if>
            <#if hasPid = true>
            , {width: 200, title: '功能操作', align: 'left', toolbar: '#toolBar'}
            <#else>
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
            </#if>
        ];

        //【渲染TABLE】
    <#if hasPid = true>
        func.treetable(cols, "tableList");
    <#else>
        func.tableIns(cols, "tableList");
    </#if>

        //【设置弹框】
    <#if (model_column?size > 11)>
        func.setWin("${tableAnnotation}");
    <#else>
        func.setWin("${tableAnnotation}", 500, 300);
    </#if>

<#if model_column?exists>
   <#list model_column as model>
        <#if model.changeColumnName?uncap_first = 'status'>
        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
        </#if>
    </#list>
</#if>
    }
});
