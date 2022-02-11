<html layout:decorator="public/layout" xmlns:miguo="http://www.w3.org/1999/html">
<div layout:fragment="content">

    <!-- 表格工具栏 -->
    <form class="layui-form toolbar">
        <div class="layui-form-item">
<#if model_column?exists>
    <#list model_column as model>
        <#if model.columnName = 'name'>
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="name" placeholder="请输入${model.columnComment}" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
        <#if model.columnName = 'title'>
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="title" placeholder="请输入${model.columnComment}" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
        <#if model.columnName = 'mobile'>
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="mobile" placeholder="请输入手机号码" autocomplete="off" class="layui-input">
                </div>
            </div>
        </#if>
        <#if model.hasColumnCommentValue = true>
            <!-- ${model.columnCommentName}下拉单选 -->
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <widget:singleSelect name="${model.changeColumnName?uncap_first}|0|${model.columnCommentName}|name|id" data="${model.columnValue}" value="0"/>
                </div>
            </div>
        </#if>
    </#list>
</#if>
            <div class="layui-inline">
                <div class="layui-input-inline" style="width: auto;">
                    <widget:btnQuery name="查询"/>
                    <widget:btnAdd name="添加${tableAnnotation}"/>
                    <widget:btnDAll name="批量删除"/>
                </div>
            </div>
        </div>
    </form>

    <!-- 数据表格 -->
    <table class="layui-hide" id="tableList" lay-filter="tableList"></table>

    <!-- 表格操作列 -->
    <script type="text/html" id="toolBar">
        <widget:btnEdit name="编辑"/>
        <widget:btnDel name="删除"/>
        <#if hasPid = true>
        {{#  if(d.pid == 0){ }}
        <widget:btnAddZ name="添加"/>
        {{#  } }}
        </#if>
    </script>

<#if model_column?exists>
    <#list model_column as model>
    <#if (model.changeColumnName?uncap_first = 'status' && model.columnSwitch == true)>
    <!-- 状态 -->
    <script type="text/html" id="statusTpl">
        <input type="checkbox" name="status" value="{{ d.id }}" lay-skin="switch" lay-text="${model.columnSwitchValue}" lay-filter="status" {{ d.status == 1 ? 'checked' : '' }} >
    </script>
    </#if>
    </#list>
</#if>
</div>
</html>