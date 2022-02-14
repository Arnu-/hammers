/**
 * 自然年结余天数
 * @auth Arnu
 * @date 2022-02-13
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
            , {field: 'employeeId', width: 100, title: '员工', align: 'center'}
            , {field: 'year', width: 100, title: '年份', align: 'center'}
            , {field: 'days', width: 100, title: '剩余天数', align: 'center'}
            , {field: 'note', width: 100, title: '备注', align: 'center'}
            , {field: 'createUserName', width: 100, title: '创建人', align: 'center'}
            , {field: 'createTime', width: 180, title: '创建时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("自然年结余天数", 500, 300);

    }
});
