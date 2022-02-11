/**
 * 系统配置
 * @auth Arnu
 * @date 2020-05-03
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
            , {field: 'title', width: 100, title: '配置标题', align: 'center'}
            , {field: 'tag', width: 100, title: '配置标签符', align: 'center'}
            , {field: 'value', width: 100, title: '配置值', align: 'center'}
            , {field: 'options', width: 100, title: '配置项', align: 'center'}
            , {field: 'groupId', width: 100, title: '配置分组ID', align: 'center'}
            , {field: 'type', width: 100, title: '配置类型', align: 'center', templet(d) {
                var cls = "";
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.typeName+'</span>';
            }}
            , {field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl'}
            , {field: 'sort', width: 100, title: '排序', align: 'center'}
            , {field: 'note', width: 100, title: '配置说明', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        var url = "/config/list?groupId=" + $("#groupId").val();
        func.tableIns(cols, "tableList", null, url);

        //【设置弹框】
        func.setWin("系统配置");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    }
});
