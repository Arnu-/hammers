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
            , { field: 'genderStr', width: 100, title: '性别', align: 'center' }
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
            , { field: 'dept', width: 100, title: '部门', align: 'center' }
            , { field: 'level', width: 100, title: '级别', align: 'center' }
            , { field: 'position', width: 100, title: '职位', align: 'center' }
            , { field: 'province', width: 100, title: '省', align: 'center' }
            , { field: 'cityName', width: 100, title: '市', align: 'center' }
            , { field: 'district', width: 100, title: '区/县', align: 'center' }
            , { field: 'address', width: 100, title: '地址', align: 'center' }
            , { field: 'enrollmentDate', width: 180, title: '入职日期', align: 'center' }
            , { field: 'formalDate', width: 180, title: '转正日期', align: 'center' }
            , { field: 'leaveDate', width: 180, title: '离职日期', align: 'center' }
            , { field: 'workYear', width: 100, title: '工龄', align: 'center' }
            , { field: 'status', width: 100, title: '状态', align: 'center' }
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
        func.setWin("员工");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
        $(".btnImportEmp").click(function () {
            func.showWin("导入员工", "import");
        });

    } else if (A == 'import') {
    //【TABLE列数组】
            var cols = [
                 { field: 'employeeId', width: 80, title: '员工号', align: 'center', edit: 'text' }
                , { field: 'realname', width: 100, title: '真实姓名', align: 'center' , edit: 'text'}
                , { field: 'nickname', width: 100, title: '昵称', align: 'center' , edit: 'text'}
                , { field: 'genderStr', width: 60, title: '性别', align: 'center' , edit: 'text'}
                , { field: 'mobile', width: 120, title: '手机', align: 'center' , edit: 'text'}
                , { field: 'email', width: 180, title: '邮箱', align: 'center' , edit: 'text'}
                , { field: 'birthday', width: 120, title: '生日', align: 'center' , edit: 'text'}
                , { field: 'dept', width: 180, title: '部门', align: 'center' , edit: 'text'}
                , { field: 'level', width: 80, title: '级别', align: 'center' , edit: 'text'}
                , { field: 'position', width: 80, title: '职位', align: 'center' , edit: 'text'}
                , { field: 'enrollmentDate', width: 120, title: '入职日期', align: 'center' , edit: 'text'}
                , { field: 'formalDate', width: 120, title: '转正日期', align: 'center' , edit: 'text'}
                , { field: 'leaveDate', width: 120, title: '离职日期', align: 'center' , edit: 'text'}
                , { field: 'workYear', width: 80, title: '工龄', align: 'center' , edit: 'text'}
                , { field: 'note', width: 100, title: '备注', align: 'center' , edit: 'text'}
                , { fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#importToolBar' }
            ];
        table.render({elem:"#importTableList",
        cols:[cols],
        height: "full-100",
        page: false,
        data:[]})
        //执行实例
        var uploadInst = upload.render({
            elem: '#importEmp' //绑定元素
            , url: 'importEmp' //上传接口
            , accept: 'file' //普通文件
            , done: function (res) {
                table.reload("importTableList", {data:res.data, limit: res.count});
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
            var url = function(u, ps){
                if(Array.isArray(ps) && ps.length > 0){
                    var p = ps[0];
                    u=u+"?" + p;
                    for(let i = 1; i < ps.length; i++){
                        u = u+"&" + ps[i];
                    }
                    return u;
                }else{
                    return u;
                }
            }("addBatch", a);
            func.ajaxPost(url, result, function (res, success) {
                console.log(res.msg);
                table.reload("importTableList", {data:res.data});
                layer.open({
                        type: 1
                        ,offset: 'auto'
                        ,id: 'importMsg' //防止重复弹出
                        ,content: '<div style="padding: 20px 100px;">'+ res.msg +'</div>'
                        ,btn: '知道了'
                        ,btnAlign: 'c' //按钮居中
                        ,shade: 0 //不显示遮罩
                      });

                // layer.closeAll("iframe");
                // 刷新父页面
                // parent.location.reload();
            }, "处理中。。。");
        });
    }
});
