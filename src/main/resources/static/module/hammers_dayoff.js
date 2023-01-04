/**
 * 请假类型
 * @auth Arnu
 * @date 2022-02-12
 */
layui.use(['func','form'], function () {

    //声明变量
    var func = layui.func,
        form = layui.form,
        $ = layui.$;

    if (A == 'index') {
        func.ajaxGet("fields", {}, function (res, success) {
            var cols = res.data;
            func.tableIns(cols, "tableList");
            //【设置弹框】
            func.setWin("请假统计", 500, 300);
            let now = new Date();
            form.val('toolbar', {"name":"","year":now.getFullYear()});
            },
             "处理中。。。");

        $(".btnAnnualCalc").click(function () {
            func.showWin("年假明细", "annualDetail", 600,550);
        });
    }else if (A == 'annualDetail'){
        let cols = [
            {field: 'field', width: 160, title: '字段', align: 'center'}
            , {field: 'value', width: 160, title: '取值', align: 'center'}
            , {field: 'describe', width: 260, title: '说明', align: 'center'}
        ];
        func.tableIns(cols, "tableList", null , "annualDetail");
    }
});
