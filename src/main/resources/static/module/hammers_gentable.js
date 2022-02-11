/**
 * 代码生成
 * @auth Arnu
 * @date 2020-05-10
 */
layui.use(['func', 'common'], function () {

    //声明变量
    var func = layui.func,
        common = layui.common,
        $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            , {field: 'tableName', width: 180, title: '表名称', align: 'center'}
            , {field: 'tableComment', width: 120, title: '表描述', align: 'center'}
            , {field: 'tablePrefix', width: 100, title: '表前缀', align: 'center'}
            , {field: 'className', width: 150, title: '实体类名称', align: 'center'}
            , {field: 'packageName', width: 180, title: '生成包路径', align: 'center'}
            , {field: 'moduleName', width: 150, title: '生成模块名', align: 'center'}
            , {field: 'businessName', width: 100, title: '生成业务名', align: 'center'}
            , {field: 'functionName', width: 120, title: '生成功能名', align: 'center'}
            , {field: 'functionAuthor', width: 120, title: '生成功能作者', align: 'center'}
            , {field: 'options', width: 120, title: '其它生成选项', align: 'center'}
            , {field: 'note', width: 150, title: '备注', align: 'center'}
            , {fixed: 'right', width: 250, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList", function (layEvent, data) {
            // 代码生成
            console.log(data);
            if (layEvent === "generator") {
                layer.confirm('您确定要生成表【' + data.tableName + '】的模块吗？生成后将覆盖现有的模块全部文件！', {
                        icon: 3,
                        skin: 'layer-ext-moon',
                        btn: ['确认', '取消'] //按钮
                    }, function (index) {
                        func.ajaxPost("/gentable/batchGenCode", {"tables": data.tableName}, function (res, success) {
                            // 延迟1秒关闭
                            setTimeout(function () {
                                // 关闭窗体
                                layer.close(layer.index);
                            }, 1000);

                        });
                    }
                );
            }
        });

        //【设置弹框】
        func.setWin("代码生成", 720, 550);

        // 选择数据库表
        $(".btnImportTable").click(function () {
            func.showWin("选择数据表", "/gentable/importTable", 750, 550);
        });

        // 批量生成
        $(".btnGenerator").click(function () {
            // 选择数据
            var data = func.getCheckData();

            // 方法参数
            var item = {};
            item['title'] = "批量生成";
            item['url'] = cUrl + "/batchGenerator";
            item['data'] = data;
            item['form'] = "submitForm";
            item['confirm'] = true;
            item['show_tips'] = "生成中...";

            // 执行方法
            common.batchFunc(item, function () {
                location.reload();
            });
        });

    }
});
