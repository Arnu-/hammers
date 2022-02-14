/**
 * 员工
 * @auth Arnu
 * @date 2022-02-12
 */
layui.use(['func', 'table', 'upload'], function () {

    //声明变量
    var func = layui.func
        , table = layui.table
        , upload = layui.upload
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            { type: 'checkbox', fixed: 'left' }
            , { field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left' }
            , { field: 'employeeId', width: 100, title: '员工号', align: 'center' }
            , { field: 'realname', width: 100, title: '真实姓名', align: 'center' }
            , { field: 'nickname', width: 100, title: '昵称', align: 'center' }
            , { field: 'gender', width: 100, title: '性别', align: 'center' }
            , {
                field: 'avatar', width: 100, title: '头像', align: 'center', templet: function (d) {
                    var avatarStr = "";
                    if (d.avatarUrl) {
                        avatarStr = '<a href="' + d.avatarUrl + '" target="_blank"><img src="' + d.avatarUrl + '" height="26" /></a>';
                    }
                    return avatarStr;
                }
            }
            , { field: 'mobile', width: 100, title: '手机', align: 'center' }
            , { field: 'email', width: 100, title: '邮箱', align: 'center' }
            , { field: 'birthday', width: 180, title: '生日', align: 'center' }
            , { field: 'deptId', width: 100, title: '部门', align: 'center' }
            , { field: 'levelId', width: 100, title: '级别', align: 'center' }
            , { field: 'positionId', width: 100, title: '职位', align: 'center' }
            , { field: 'provinceId', width: 100, title: '省', align: 'center' }
            , { field: 'cityId', width: 100, title: '市', align: 'center' }
            , { field: 'districtId', width: 100, title: '区/县', align: 'center' }
            , { field: 'address', width: 100, title: '地址', align: 'center' }
            , { field: 'cityName', width: 100, title: '城市名', align: 'center' }
            , { field: 'enrollmentDate', width: 180, title: '入职日期', align: 'center' }
            , { field: 'formalDate', width: 180, title: '转正日期', align: 'center' }
            , { field: 'leaveDate', width: 180, title: '离职日期', align: 'center' }
            , { field: 'workYear', width: 100, title: '工龄', align: 'center' }
            , { field: 'status', width: 100, title: '状态', align: 'center' }
            , { field: 'note', width: 100, title: '备注', align: 'center' }
            , { field: 'sort', width: 100, title: '排序', align: 'center' }
            , { field: 'createUserName', width: 100, title: '创建人', align: 'center' }
            , { field: 'createTime', width: 180, title: '创建时间', align: 'center' }
            , { field: 'updateUserName', width: 100, title: '更新人', align: 'center' }
            , { field: 'updateTime', width: 180, title: '更新时间', align: 'center' }
            , { fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar' }
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("员工");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
        $(".btnImportEmp").click(function () {
            func.showWin("导入员工", "import", 750, 550);
        });

    } else if (A == 'import') {
        //执行实例
        var uploadInst = upload.render({
            elem: '#importEmp' //绑定元素
            , url: 'importEmp' //上传接口
            , accept: 'file' //普通文件
            , done: function (res) {
                //【TABLE列数组】
                //                        var cols = [
                //                            {type: 'checkbox', fixed: 'left'}
                //                            , {field: 'tableName', width: 200, title: '表名称', align: 'left'}
                //                            , {field: 'tableComment', width: 150, title: '表描述', align: 'center'}
                //                            , {field: 'createTime', width: 180, title: '创建时间', align: 'center'}
                //                        ];
                table.reload("#importTableList", res.data);
                // res.data
                //上传完毕回调
            }
            , error: function () {
                //请求异常回调

            }
        });
        $(".btnImportTemplateExcel").click(function () {
            // var a = [],
            //     o = $(".layui-form-item [name]").serializeArray();
            // $.each(o, (function () {
            //     a.push(this.name + "=" + this.value)
            // })),
            //     (o = {
            //         title: "导出数据"
            //     }).url = "importTemplateExcel",
            //     o.data = {},
            //     o.confirm = !0,
            //     o.type = "GET",
            //     o.show_tips = "数据准备中...",
            //     o.param = a,
            //     layui.common.batchFunc(o, (function (t, e) {
            //         window.location.href = "/common/download?fileName=" + encodeURI(t.data) + "&isDelete=" + !0
            //     }));

            // // var result = func.getCheckData("tableList2"); 获取选中的行
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
            var result = func.getCheckData("tableList2");
            if (result.length == 0) {
                layer.msg("请至少选择一条记录", { icon: 5 });
                return false;
            }
            var arr = [];
            result.forEach(function (item, index) {
                arr.push(item.tableName);
            });
            var data = { "tables": arr.join(",") };
            func.ajaxPost("/gentable/importTableSave", data, function (res, success) {
                console.log("处理成功");
                layer.closeAll("iframe");
                // 刷新父页面
                parent.location.reload();
            }, "处理中。。。");
        });
    }
});
