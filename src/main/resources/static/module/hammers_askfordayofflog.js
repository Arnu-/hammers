/**
 * 请假记录
 * @auth Arnu
 * @date 2022-02-12
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
            , {field: 'employeeId', width: 100, title: '员工号', align: 'center'}
            , {field: 'realName', width: 100, title: '姓名', align: 'center'}
            , {field: 'dayOffType', width: 100, title: '请假类型', align: 'center'}
            , {field: 'startDate', width: 180, title: '开始日期', align: 'center'}
            , {field: 'startHalfDay', width: 100, title: '开始于', align: 'center'}
            , {field: 'endDate', width: 180, title: '结束日期', align: 'center'}
            , {field: 'endHalfDay', width: 100, title: '结束于', align: 'center'}
            , {field: 'days', width: 100, title: '请假天数', align: 'center'}
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
        func.setWin("请假记录", 680, 500);

    }
});
