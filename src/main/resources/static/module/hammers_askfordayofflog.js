/**
 * 请假记录
 * @auth Arnu
 * @date 2022-02-12
 */
layui.use(['func', 'table', 'upload', 'form'], function () {

    //声明变量
    var func = layui.func
        , table = layui.table
        , upload = layui.upload
        , form = layui.form
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            { type: 'checkbox', fixed: 'left' }
            , { field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left' }
            , { field: 'employeeId', width: 100, title: '员工号', align: 'center' }
            , { field: 'realName', width: 100, title: '姓名', align: 'center' }
            , { field: 'dayOffType', width: 100, title: '请假类型', align: 'center' }
            , { field: 'startDate', width: 180, title: '开始日期', align: 'center' }
            , { field: 'startHalfDay', width: 100, title: '开始于', align: 'center' }
            , { field: 'endDate', width: 180, title: '结束日期', align: 'center' }
            , { field: 'endHalfDay', width: 100, title: '结束于', align: 'center' }
            , { field: 'days', width: 100, title: '请假天数', align: 'center' }
            , { field: 'note', width: 100, title: '备注', align: 'center' }
            , { field: 'createUserName', width: 100, title: '创建人', align: 'center' }
            , { field: 'createTime', width: 180, title: '创建时间', align: 'center' }
            , { field: 'updateUserName', width: 100, title: '更新人', align: 'center' }
            , { field: 'updateTime', width: 180, title: '更新时间', align: 'center' }
            , { fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar' }
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("请假记录", 680, 500);
        $(".btnImportAsk").click(function () {
            func.showWin("导入请假", "import");
        });
    } else if (A == 'import') {
        //【TABLE列数组】
        var cols = [
            , { field: 'employeeId', width: 100, title: '员工号', align: 'center', edit: 'text' }
            , { field: 'realName', width: 100, title: '姓名', align: 'center', edit: 'text' }
            , { field: 'dayOffType', width: 100, title: '请假类型', align: 'center', edit: 'text' }
            , { field: 'startDate', width: 180, title: '开始日期', align: 'center', edit: 'date', dateType: 'date' }
            , { field: 'startHalfDay', width: 100, title: '开始于', align: 'center', edit: 'text' }
            , { field: 'endDate', width: 180, title: '结束日期', align: 'center', edit: 'date', dateType: 'date' }
            , { field: 'endHalfDay', width: 100, title: '结束于', align: 'center', edit: 'text' }
            , { field: 'days', width: 100, title: '请假天数', align: 'center', edit: 'text' }
            , { field: 'note', width: 100, title: '备注', align: 'center', edit: 'text' }
        ];
        table.render({
            elem: "#importTableList",
            cols: [cols],
            height: "full-100",
            page: false,
            data: []
        })
        //执行实例
        var uploadInst = upload.render({
            elem: '#importEmp' //绑定元素
            , url: 'importEmp' //上传接口
            , accept: 'file' //普通文件
            , done: function (res) {
                table.reload("importTableList", { data: res.data, limit: res.count });
            }
            , error: function () {
            }
        });
        $(".btnImportTemplateExcel").click(function () {
            func.ajaxGet("importTemplateExcel", {}, function (res, success) {
                window.location.href = "/common/download?fileName=" + encodeURI(res.data) + "&isDelete=" + !0
                console.log("处理成功");
                // layer.closeAll("iframe");
                // 刷新父页面
                // parent.location.reload();
            }, "处理中。。。");
        });

        // 确定按钮事件
        $(".btnConfirm").click(function () {
            var result = table.cache["importTableList"];
            if (result.length == 0) {
                layer.msg("请至少上传一条数据", { icon: 5 });
                return false;
            }
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
                console.log(res.msg);
                let showMsg = res.msg;
                let layerOpt = {
                    type: 1
                    , offset: 'auto'
                    , id: 'importMsg' //防止重复弹出
                    , btn: '知道了'
                    , btnAlign: 'c' //按钮居中
                    , shade: 0 //不显示遮罩
                };
                // ,content: '<div style="padding: 20px 100px;">'+ showMsg +'</div>'
                /*

                  ,yse: function(){
                    layer.closeAll("iframe");
                    parent.location.reload();
                  }
                */
                if (Array.isArray(res.data) && res.data.length > 0) {
                    showMsg = showMsg + "请通过界面备注检查未写入数据问题原因";
                } else {
                    layerOpt['yes'] = function () {
                        layer.closeAll("iframe");
                        parent.location.reload();
                    }
                }
                layerOpt['content'] = '<div style="padding: 20px 100px;">' + showMsg + '</div>';
                table.reload("importTableList", { data: res.data });
                layer.open(layerOpt);

                // layer.closeAll("iframe");
                // 刷新父页面
                // parent.location.reload();
            }, "处理中。。。");
        });
    } else if (A == "edit") {
        $("#annual").click(function (r) {
            var employeeId = $("#employeeId").val();
            if (employeeId && employeeId.length > 0) {
                var url = "remainAnnual";
                let d = new Date();
                let year = d.getYear() + 1900;
                var p = {
                    page: 1,
                    limit: 20,
                    name: employeeId,
                    year: year,
                    empId: employeeId
                }
                func.ajaxPost(url, p, function (res, success) {
                    $("#remainAnnual").val(res.msg);
                    console.log(res.msg);
                }, "处理中。。。");
            }
        })
    }
});
