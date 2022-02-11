/**
 * 代码生成列
 * @auth Arnu
 * @date 2020-05-10
 */
layui.use(['func'], function () {

    //声明变量
    var func = layui.func
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
              {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            , {field: 'tableId', width: 100, title: '归属表编号', align: 'center'}
            , {field: 'columnName', width: 100, title: '列名称', align: 'center'}
            , {field: 'columnComment', width: 100, title: '列描述', align: 'center'}
            , {field: 'columnType', width: 100, title: '列类型', align: 'center'}
            , {field: 'javaType', width: 100, title: 'JAVA类型', align: 'center'}
            , {field: 'javaField', width: 100, title: 'JAVA字段名', align: 'center'}
            , {field: 'isPk', width: 100, title: '是否主键', align: 'center', templet(d) {
                var cls = "";
                if (d.isPk == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isPk == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isPkName+'</span>';
            }}
            , {field: 'isIncrement', width: 100, title: '是否自增', align: 'center', templet(d) {
                var cls = "";
                if (d.isIncrement == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isIncrement == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isIncrementName+'</span>';
            }}
            , {field: 'isRequired', width: 100, title: '是否必填', align: 'center', templet(d) {
                var cls = "";
                if (d.isRequired == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isRequired == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isRequiredName+'</span>';
            }}
            , {field: 'isInsert', width: 100, title: '是否为插入字段', align: 'center', templet(d) {
                var cls = "";
                if (d.isInsert == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isInsert == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isInsertName+'</span>';
            }}
            , {field: 'isEdit', width: 100, title: '是否编辑字段', align: 'center', templet(d) {
                var cls = "";
                if (d.isEdit == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isEdit == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isEditName+'</span>';
            }}
            , {field: 'isList', width: 100, title: '是否列表字段', align: 'center', templet(d) {
                var cls = "";
                if (d.isList == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isList == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isListName+'</span>';
            }}
            , {field: 'isQuery', width: 100, title: '是否查询字段', align: 'center', templet(d) {
                var cls = "";
                if (d.isQuery == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isQuery == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isQueryName+'</span>';
            }}
            , {field: 'queryType', width: 100, title: '查询方式（等于、不等于、大于、小于、范围）', align: 'center'}
            , {field: 'htmlType', width: 100, title: '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）', align: 'center'}
            , {field: 'dictType', width: 100, title: '字典类型', align: 'center'}
            , {field: 'sort', width: 100, title: '排序', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '创建时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("代码生成列");

    }
});
