/**
 * 请假类型
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
            , {field: 'employeeId', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            , {field: 'employeeName', width: 100, title: '姓名', align: 'center'}
            , {field: 'dept', width: 100, title: '部门', align: 'center'}
            , {field: 'level', width: 100, title: '级别', align: 'center'}
            , {field: 'workYear', width: 100, title: '工龄', align: 'center'}
            , {field: 'lastYearAnnualVacationBalance', width: 100, title: '去年结余年假', align: 'center'}
            , {field: 'actuallyAnnualVacationDays', width: 100, title: '今年年假额度', align: 'center'}
            , {field: 'lastYearRemainAnnualVacationDays', width: 100, title: '去年可用年假剩余', align: 'center'}
            , {field: 'thisYearRemainAnnualVacationDays', width: 100, title: '今年可用年假剩余', align: 'center'}
            , {field: 'firstAnnualVacationDayOffDays', width: 100, title: '3月31日前请的年假', align: 'center'}
            , {field: 'secondAnnualVacationDayOffDays', width: 100, title: '3月31日后请的年假', align: 'center'}
            , {field: 'annualVacationDayOffDays', width: 100, title: '总计请年假', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "summaryTableList");

        //【设置弹框】
        func.setWin("请假类型", 500, 300);

    }
});
