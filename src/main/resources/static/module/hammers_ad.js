/**
 * 广告
 * @auth Arnu
 * @date 2020-05-03
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
            , {field: 'title', width: 200, title: '广告标题', align: 'center'}
            , {field: 'sortName', width: 200, title: '广告位', align: 'center'}
            , {field: 'description', width: 200, title: '广告描述', align: 'center'}
            , {field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl'}
            , {field: 'cover', width: 100, title: '广告图片', align: 'center', templet: function (d) {
                var coverStr = "";
                if (d.coverUrl) {
                    coverStr = '<a href="' + d.coverUrl + '" target="_blank"><img src="' + d.coverUrl + '" height="26" /></a>';
                }
                return coverStr;
              }
            }
            , {field: 'type', width: 100, title: '广告格式', align: 'center', templet(d) {
                var cls = "";
                if (d.type == 1) {
                    // 图片
                    cls = "layui-btn-normal";
                } else if (d.type == 2) {
                    // 文字
                    cls = "layui-btn-danger";
                } else if (d.type == 3) {
                    // 视频
                    cls = "layui-btn-warm";
                } else if (d.type == 4) {
                    // 推荐
                    cls = "layui-btn-primary";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.typeName+'</span>';
            }}
            , {field: 'width', width: 100, title: '广告宽度', align: 'center'}
            , {field: 'height', width: 100, title: '广告高度', align: 'center'}
            , {field: 'startTime', width: 180, title: '开始时间', align: 'center'}
            , {field: 'endTime', width: 180, title: '结束时间', align: 'center'}
            , {field: 'viewNum', width: 100, title: '广告点击次数', align: 'center'}
            , {field: 'sort', width: 100, title: '排序', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("广告");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    }
});
