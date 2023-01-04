/**
 * 自然年结余天数
 * @auth Arnu
 * @date 2022-02-13
 */
layui.use(['func', 'form', 'table', 'layer'], function () {


    //声明变量
    var func = layui.func
        , form = layui.form
        , table = layui.table
        , layer = layui.layer
        , $ = layui.$;

    form.verify({
        inputFloat: [
            /^[0-9]+(\.?[0-9]+)?$/
            , '请输入数字'
        ]
    })
    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            , {field: 'employeeId', width: 100, title: '员工ID', align: 'center'}
            , {field: 'realName', width: 100, title: '员工', align: 'center'}
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

        // 【打开自动计算对话框】
        $(".btnAutoCalc").click(function () {
            func.showWin("自动计算年假结余", "autoBalanceCalc");
        });
    } else if (A === 'autoBalanceCalc') {
        let now = new Date();
        form.val('toolbar', {"name": "", "year": now.getFullYear() - 1});
        let cols = [
            {"field": "employeeId", "width": 80, "title": "员工号", "align": "center", "fixed": "left"},
            {"field": "employeeName", "width": 100, "title": "姓名", "align": "center", "fixed": "left"},
            {"field": "dept", "width": 120, "title": "部门", "align": "center", "fixed": ""},
            {"field": "level", "width": 120, "title": "级别", "align": "center", "fixed": ""},
            {
                "field": "thisYearRemainAnnualVacationDays",
                "width": 100,
                "title": "今年剩余",
                "align": "center",
                "fixed": "",
                "edit": "text"
            },
            {
                "field": "lastYearAnnualVacationBalance",
                "width": 100,
                "title": "去年结余",
                "align": "center",
                "fixed": ""
            },
            {"field": "actualAnnualVacationDays", "width": 100, "title": "今年额度", "align": "center", "fixed": ""},
            {
                "field": "lastYearRemainAnnualVacationDays",
                "width": 100,
                "title": "去年剩余",
                "align": "center",
                "fixed": ""
            },
            {
                "field": "firstAnnualVacationDayOffDays",
                "width": 100,
                "title": "3月31日前",
                "align": "center",
                "fixed": ""
            },
            {
                "field": "secondAnnualVacationDayOffDays",
                "width": 100,
                "title": "3月31日后",
                "align": "center",
                "fixed": ""
            },
            {"field": "annualVacationDayOffDays", "width": 100, "title": "总计", "align": "center", "fixed": ""},
            {fixed: 'right', width: 80, title: '操作', align: 'center', toolbar: '#importToolBar'}
        ];
        // 生成表格
        table.render({
            elem: "#tableList",
            cols: [cols],
            height: "full-100",
            page: false,
            data: []
        })
        // 获取数据
        form.on('submit(searchForm)', function (elem) {
            // console.log(elem);
            let p = {"name": elem.field.name, "year": elem.field.year}
            table.reload("tableList", {
                url: "/dayoff/list",
                method: 'post',
                page: {
                    curr: 1
                },
                where: p
            })
            return false;
        })
        // 确定按钮事件
        $(".btnConfirm").click(function () {
            var result = table.cache["tableList"];
            if (result.length == 0) {
                layer.msg("请至少上传一条数据", {icon: 5});
                return false;
            }
            // 下列代码是用来获取表格工具栏的form内容
            var a = [],
                o = $(".layui-form-item [name]").serializeArray();
            $.each(o, (function () {
                a.push(this.name + "=" + this.value)
            }))
            var url = function (u, ps) {
                if (Array.isArray(ps) && ps.length > 0) {
                    var p = ps[0];
                    u = u + "?" + p;
                    for (let i = 1; i < ps.length; i++) {
                        u = u + "&" + ps[i];
                    }
                    return u;
                } else {
                    return u;
                }
            }("addBatch", a);
            func.ajaxPost(url, result, function (res, success) {
                // console.log(res.msg);
                let showMsg = res.msg;
                let layerOpt = {
                    type: 1
                    , offset: 'auto'
                    , id: 'importMsg' //防止重复弹出
                    , btn: '知道了'
                    , btnAlign: 'c' //按钮居中
                    , shade: 0 //不显示遮罩
                };
                if (Array.isArray(res.data) && res.data.length > 0) {
                    showMsg = showMsg + "请通过界面备注检查未写入数据问题原因";
                } else {
                    layerOpt['yes'] = function () {
                        // 关闭当前弹窗 todo:一会儿放开
                        // layer.closeAll("iframe");
                        parent.location.reload();
                    }
                }
                layerOpt['content'] = '<div style="padding: 20px 100px;">' + showMsg + '</div>';

                table.render({
                    elem: "#tableList",
                    cols: [cols],
                    height: "full-100",
                    page: false,
                    data: res.data,
                    limit: res.count
                })
                // table.reload("tableList", {data: res.data, limit: res.count});
                layer.open(layerOpt);
            }, "处理中。。。");
        });
    }
});
