/**
 * 职级
 * @auth Arnu
 * @date 2020-04-20
 */
layui.use(['func', 'common', 'upload', 'table'], function () {

    //声明变量
    var func = layui.func
        , common = layui.common
        , upload = layui.upload
        , table = layui.table
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            { type: 'checkbox', fixed: 'left' }
            , { field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left' }
            , { field: 'name', width: 200, title: '职级名称', align: 'center' }
            , { field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl' }
            , { field: 'sort', width: 100, title: '显示顺序', align: 'center' }
            , { field: 'createUserName', width: 100, title: '添加人', align: 'center' }
            , { field: 'createTime', width: 180, title: '创建时间', align: 'center' }
            , { field: 'updateUserName', width: 100, title: '更新人', align: 'center' }
            , { field: 'updateTime', width: 180, title: '更新时间', align: 'center' }
            , { fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar' }
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");
        
        // var tableIns = table.render( {elem:"#tableList", cols: [cols] });
        // table.reload( {id:"tableList", cols:cols});

        //【设置弹框】
        func.setWin("职级", 500, 300, true);

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
        // importLevel
        // u.uploadFile("import", (function(t, e) {}))
        // common.uploadFile("importLevel", function (t, e) {
        //     tableIns.reload(cols, t.data);
        // })


        // var uploadInst = upload.render({
        //     elem: '#importEmp' //绑定元素
        //     , url: 'importEmp' //上传接口
        //     , accept: 'file' //普通文件
        //     , done: function (res) {
        //         //【TABLE列数组】
        //         //                        var cols = [
        //         //                            {type: 'checkbox', fixed: 'left'}
        //         //                            , {field: 'tableName', width: 200, title: '表名称', align: 'left'}
        //         //                            , {field: 'tableComment', width: 150, title: '表描述', align: 'center'}
        //         //                            , {field: 'createTime', width: 180, title: '创建时间', align: 'center'}
        //         //                        ];
        //         table.reload("#importTableList", res.data);
        //         // res.data
        //         //上传完毕回调
        //     }
        //     , error: function () {
        //         //请求异常回调

        //     }
        // });


        // var n = "#importLevel", i = "import", o = "xls|xlsx", l = 10240, u = {}
        // e = function (t, e) {
        //     // table.reload('tableList' ,{id: 'tableList', data:t.data});
        //     tableIns.reload({ data: t.data });
        // }
        //     , t = layer;
        // upload.render({
        //     elem: n,
        //     url: i,
        //     exts: o,
        //     accept: "file",
        //     size: l,
        //     method: "post",
        //     data: u,
        //     before: function (n) {
        //         t.msg("上传并处理中。。。", {
        //             icon: 16,
        //             shade: .01,
        //             time: 0
        //         })
        //     },
        //     done: function (n) {
        //         t.closeAll();
        //         e && e(n, !0);
        //     },
        //     error: function () {
        //         return t.msg("数据请求异常")
        //     }
        // })

        /*
        function (n) {
                return t.closeAll(),
                    0 == n.code ? t.alert(n.msg, {
                        title: "上传反馈",
                        skin: "layui-layer-molv",
                        closeBtn: 1,
                        anim: 0,
                        btn: ["确定", "取消"],
                        icon: 6,
                        yes: function () {
                            e && e(n, !0)
                        },
                        btn2: function () { }
                    }) : t.msg(n.msg, {
                        icon: 5
                    }),
                    !1
            },
            */
    }
});
