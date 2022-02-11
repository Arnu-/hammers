/**
 * 文章管理
 * @auth Arnu
 * @date 2020-08-11
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
            , {field: 'title', width: 250, title: '文章标题', align: 'center'}
            , {field: 'cover', width: 100, title: '文章封面', align: 'center', templet: function (d) {
                var coverStr = "";
                if (d.coverUrl) {
                    coverStr = '<a href="' + d.coverUrl + '" target="_blank"><img src="' + d.coverUrl + '" height="26" /></a>';
                }
                return coverStr;
              }
            }
            , {field: 'cateName', width: 200, title: '文章分类', align: 'center'}
            , {field: 'viewNum', width: 100, title: '浏览次数', align: 'center'}
            , {field: 'isTop', width: 100, title: '是否置顶', align: 'center', templet(d) {
                var cls = "";
                if (d.isTop == 1) {
                    // 已置顶
                    cls = "layui-btn-normal";
                } else if (d.isTop == 2) {
                    // 未置顶
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isTopName+'</span>';
            }}
            , {field: 'status', width: 100, title: '状态', align: 'center', templet(d) {
                var cls = "";
                if (d.status == 1) {
                    // 已审核
                    cls = "layui-btn-normal";
                } else if (d.status == 2) {
                    // 待审核
                    cls = "layui-btn-danger";
                } else if (d.status == 3) {
                    // 审核失败
                    cls = "layui-btn-warm";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.statusName+'</span>';
            }}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("文章管理");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    }
});
