/**
 * 会员用户
 * @auth Arnu
 * @date 2020-05-04
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
            , {field: 'realname', width: 100, title: '真实姓名', align: 'center'}
            , {field: 'nickname', width: 120, title: '用户昵称', align: 'center'}
            , {field: 'gender', width: 80, title: '性别', align: 'center', templet(d) {
                var cls = "";
                if (d.gender == 1) {
                    // 男
                    cls = "layui-btn-normal";
                } else if (d.gender == 2) {
                    // 女
                    cls = "layui-btn-danger";
                } else if (d.gender == 3) {
                    // 未知
                    cls = "layui-btn-warm";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.genderName+'</span>';
            }}
            , {field: 'avatar', width: 90, title: '用户头像', align: 'center', templet: function (d) {
                var avatarStr = "";
                if (d.avatarUrl) {
                    avatarStr = '<a href="' + d.avatarUrl + '" target="_blank"><img src="' + d.avatarUrl + '" height="26" /></a>';
                }
                return avatarStr;
              }
            }
            , {field: 'qrcode', width: 80, title: '推广码', align: 'center', templet: function (d) {
                    var qrcodeStr = "";
                    if (d.qrcodeUrl) {
                        qrcodeStr = '<a href="' + d.qrcodeUrl + '" target="_blank"><img src="' + d.qrcodeUrl + '" height="26" /></a>';
                    }
                    return qrcodeStr;
                }
            }
            , {field: 'mobile', width: 130, title: '手机号', align: 'center'}
            , {field: 'cityArea', width: 200, title: '所在城市', align: 'center'}
            , {field: 'device', width: 100, title: '设备类型', align: 'center', templet(d) {
                var cls = "";
                if (d.device == 1) {
                    // 苹果
                    cls = "layui-btn-normal";
                } else if (d.device == 2) {
                    // 安卓
                    cls = "layui-btn-danger";
                } else if (d.device == 3) {
                    // WAP站
                    cls = "layui-btn-warm";
                } else if (d.device == 4) {
                    // PC站
                    cls = "layui-btn-primary";
                } else if (d.device == 5) {
                    // 微信小程序
                    cls = "layui-btn-disabled";
                }

				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.deviceName+'</span>';
            }}
            , {field: 'loginStatus', width: 100, title: '用户状态', align: 'center', templet(d) {
                var cls = "";
                if (d.loginStatus == 1) {
                    // 登录
                    cls = "layui-btn-normal";
                } else if (d.loginStatus == 2) {
                    // 登出
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.loginStatusName+'</span>';
            }}
            , {field: 'source', width: 100, title: '用户来源', align: 'center', templet(d) {
                var cls = "";
                if (d.source == 1) {
                    // 注册会员
                    cls = "layui-btn-normal";
                } else if (d.source == 2) {
                    // 马甲会员
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.sourceName+'</span>';
            }}
            , {field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl'}
            , {field: 'loginIp', width: 100, title: '最近登录IP', align: 'center'}
            , {field: 'loginRegion', width: 130, title: '上次登录地点', align: 'center'}
            , {field: 'loginTime', width: 180, title: '最近登录时间', align: 'center'}
            , {field: 'loginCount', width: 100, title: '登录总次数', align: 'center'}
            , {field: 'createTime', width: 180, title: '创建时间', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'left', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("会员用户");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    }
});
