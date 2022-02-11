/**
 * 通知公告
 * @auth Arnu
 * @date 2020-05-04
 */
layui.use(['func'], function () {

    //声明变量
    var func = layui.func
        , form = layui.form
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
              {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            , {field: 'title', width: 350, title: '通知标题', align: 'center'}
            , {field: 'source', width: 100, title: '通知来源', align: 'center', templet(d) {
                var cls = "";
                if (d.source == 1) {
                    // 云平台
                    cls = "layui-btn-normal";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.sourceName+'</span>';
            }}
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
            , {field: 'viewNum', width: 100, title: '阅读量', align: 'center'}
            , {field: 'status', width: 100, title: '发布状态', align: 'center', templet(d) {
                var cls = "";
                if (d.status == 1) {
                    // 草稿箱
                    cls = "layui-btn-normal";
                } else if (d.status == 2) {
                    // 立即发布
                    cls = "layui-btn-danger";
                } else if (d.status == 3) {
                    // 定时发布
                    cls = "layui-btn-warm";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.statusName+'</span>';
            }}
            , {field: 'publishTime', width: 180, title: '发布时间', align: 'center'}
            , {field: 'isSend', width: 100, title: '推送状态', align: 'center', templet(d) {
                var cls = "";
                if (d.isSend == 1) {
                    // 已推送
                    cls = "layui-btn-normal";
                } else if (d.isSend == 2) {
                    // 未推送
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isSendName+'</span>';
            }}
            , {field: 'sendTime', width: 180, title: '推送时间', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("通知公告");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    } else {
        //监听推荐类型
        var status = $("#status").val();
        console.log("已选择："+status);
        if (status == 3) {
            // 显示
            $(".publishTime").removeClass("layui-hide");
        } else {
            // 隐藏
            $(".publishTime").addClass("layui-hide");
        }
        form.on('select(status)', function (data) {
            status = data.value;
            console.log(status);
            if (status == 3) {
                // 显示
                $(".publishTime").removeClass("layui-hide");
            } else {
                // 隐藏
                $(".publishTime").addClass("layui-hide");
            }
        });
    }
});
