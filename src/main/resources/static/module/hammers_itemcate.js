/**
 * 栏目
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
              {field: 'id', width: 80, title: 'ID', align: 'center', sort: true}
            , {field: 'name', width: 200, title: '栏目名称', align: 'left'}
            , {field: 'itemName', width: 200, title: '所属站点', align: 'center'}
            , {field: 'cover', width: 100, title: '封面', align: 'center', templet: function (d) {
                    var coverStr = "";
                    if (d.coverUrl) {
                        coverStr = '<a href="' + d.coverUrl + '" target="_blank"><img src="' + d.coverUrl + '" height="26" /></a>';
                    }
                    return coverStr;
                }
            }
            , {field: 'pinyin', width: 150, title: '拼音(全)', align: 'center'}
            , {field: 'code', width: 100, title: '拼音(简)', align: 'center'}
            , {field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl'}
            , {field: 'sort', width: 100, title: '排序', align: 'center'}
            , {field: 'note', width: 200, title: '备注', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {width: 220, title: '功能操作', align: 'left', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.treetable(cols, "tableList");

        //【设置弹框】
        func.setWin("栏目");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    }
});
