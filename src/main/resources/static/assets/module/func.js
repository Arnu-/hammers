!
function(t) {
    var e = {};
    function n(a) {
        if (e[a]) return e[a].exports;
        var i = e[a] = {
            i: a,
            l: !1,
            exports: {}
        };
        return t[a].call(i.exports, i, i.exports, n),
        i.l = !0,
        i.exports
    }
    n.m = t,
    n.c = e,
    n.d = function(t, e, a) {
        n.o(t, e) || Object.defineProperty(t, e, {
            enumerable: !0,
            get: a
        })
    },
    n.r = function(t) {
        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {
            value: "Module"
        }),
        Object.defineProperty(t, "__esModule", {
            value: !0
        })
    },
    n.t = function(t, e) {
        if (1 & e && (t = n(t)), 8 & e) return t;
        if (4 & e && "object" == typeof t && t && t.__esModule) return t;
        var a = Object.create(null);
        if (n.r(a), Object.defineProperty(a, "default", {
            enumerable: !0,
            value: t
        }), 2 & e && "string" != typeof t) for (var i in t) n.d(a, i,
        function(e) {
            return t[e]
        }.bind(null, i));
        return a
    },
    n.n = function(t) {
        var e = t && t.__esModule ?
        function() {
            return t.
        default
        }:
        function() {
            return t
        };
        return n.d(e, "a", e),
        e
    },
    n.o = function(t, e) {
        return Object.prototype.hasOwnProperty.call(t, e)
    },
    n.p = "",
    n(n.s = 2)
} ([, ,
function(t, e, n) {
    n(3)
},
function(t, e) {
    layui.define(["form", "layer", "table", "common", "treeTable"], (function(t) {
        "use strict";
        var e, n, a, i, o, r = layui.form,
        l = layui.table,
        c = layui.layer,
        u = layui.common,
        d = layui.treeTable,
        f = layui.$,
        s = 0,
        h = 0,
        m = !1,
        b = {
            tableIns: function(t, r, d = null, b = "", p = !1) {
                n = r,
                a = d,
                b && "" != b || (b = cUrl + "/list");
                var y = f("#param").val();
                if (y && (y = JSON.parse(y), f.isArray(y))) for (var v in y) b.indexOf("?") >= 0 ? b += "&" + y[v] : b += "?" + y[v];
                return e = l.render({
                    elem: "#" + n,
                    url: b,
                    method: "post",
                    cellMinWidth: 150,
                    page: {
                        layout: ["refresh", "prev", "page", "next", "skip", "count", "limit"],
                        curr: 1,
                        groups: 10,
                        first: "首页",
                        last: "尾页"
                    },
                    height: "full-100",
                    limit: 20,
                    limits: [20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 1e3],
                    even: !0,
                    cols: [t],
                    loading: !0,
                    done: function(t, e, n) {
                        if (o) {
                            var a = f(".layui-table-body").find("table").find("tbody");
                            a.children("tr").on("dblclick", (function() {
                                var e = a.find(".layui-table-hover").data("index"),
                                n = t.data[e];
                                u.edit(i, n.id, s, h)
                            }))
                        }
                    }
                }),
                l.on("toolbar(" + n + ")", (function(t) {
                    var e = l.checkStatus(t.config.id);
                    switch (t.event) {
                    case "getCheckData":
                        var n = e.data;
                        c.alert(JSON.stringify(n));
                        break;
                    case "getCheckLength":
                        n = e.data;
                        c.msg("选中了：" + n.length + " 个");
                        break;
                    case "isAll":
                        c.msg(e.isAll ? "全选": "未全选")
                    }
                })),
                l.on("tool(" + n + ")", (function(t) {
                    var e = t.data,
                    n = t.event;
                    "edit" === n ? u.edit(i, e.id, s, h, [], (function(t, e) {
                        2 == e && f(".layui-laypage-btn").click()
                    }), m) : "detail" === n ? u.detail(i, e.id, s, h, m) : "del" === n ? u.delete(e.id, (function(e, n) {
                        n && t.del()
                    })) : "cache" === n ? u.cache(e.id) : "copy" === n ? u.copy(i, e.id, s, h) : a && a(n, e)
                })),
                l.on("checkbox(" + n + ")", (function(t) {})),
                l.on("edit(" + n + ")", (function(t) {
                    var e = t.value,
                    n = t.data,
                    a = t.field,
                    i = {};
                    i.id = n.id,
                    i[a] = e;
                    var o = JSON.stringify(i),
                    r = JSON.parse(o),
                    l = cUrl + "/update";
                    u.ajaxPost(l, r, (function(t, e) {}), "更新中...")
                })),
                l.on("row(" + n + ")", (function(t) {
                    t.tr.addClass("layui-table-click").siblings().removeClass("layui-table-click");
                    t.data
                })),
                p && l.on("sort(" + n + ")", (function(t) {
                    l.reload(n, {
                        initSort: t,
                        where: {
                            field: t.field,
                            order: t.type
                        }
                    })
                })),
                this
            },
            treetable: function(t = [], e, a = !0, o = 0, r = "", l = null, m = "") {
                n = e,
                m || (m = cUrl + "/list");
                var b = d.render({
                    elem: "#" + e,
                    url: m,
                    method: "POST",
                    height: "full-50",
                    cellMinWidth: 80,
                    tree: {
                        iconIndex: 1,
                        idName: "id",
                        pidName: r || "pid",
                        isPidData: !0
                    },
                    cols: [t],
                    done: function(t, e, n) {
                        c.closeAll("loading")
                    },
                    style: "margin-top:0;"
                });
                d.on("tool(" + e + ")", (function(t) {
                    var e = t.data,
                    n = t.event,
                    a = e.id;
                    "add" === n ? u.edit(i, 0, s, h, ["pid=" + a], (function(t, e) {
                        2 == e && location.reload()
                    })) : "edit" === n ? u.edit(i, a, s, h, [], (function(t, e) {
                        2 == e && location.reload()
                    })) : "del" === n ? u.delete(a, (function(e, n) {
                        n && t.del()
                    })) : l && l(n, a, 0)
                })),
                f("#collapse").on("click", (function() {
                    return b.foldAll(),
                    !1
                })),
                f("#expand").on("click", (function() {
                    return b.expandAll(),
                    !1
                })),
                f("#refresh").on("click", (function() {
                    return b.refresh(),
                    !1
                })),
                f("#search").click((function() {
                    var t = f("#keywords").val();
                    return t ? b.filterData(t) : b.clearFilter(),
                    !1
                }))
            },
            setWin: function(t, e = 0, n = 0, a = !1) {
                return i = t,
                s = e,
                h = n,
                m = a,
                this
            },
            setDbclick: function(t) {
                return o = t || !0,
                this
            },
            searchForm: function(t, e) {
                r.on("submit(" + t + ")", (function(t) {
                    return u.searchForm(l, t, e),
                    !1
                }))
            },
            getCheckData: function(t) {
                return t || (t = n),
                l.checkStatus(t).data
            },
            initDate: function(t, e = null) {
                u.initDate(t, (function(t, n) {
                    e && e(t, n)
                }))
            },
            showWin: function(t, e, n = 0, a = 0, i = [], o = 2, r = [], l = null, c = !1) {
                u.showWin(t, e, n, a, i, o, r, (function(t, e) {
                    l && l(t, e)
                }), c)
            },
            ajaxPost: function(t, e, n = null, a = "处理中...") {
                u.ajaxPost(t, e, n, a)
            },
            ajaxGet: function(t, e, n = null, a = "处理中...") {
                u.ajaxGet(t, e, n, a)
            },
            formSwitch: function(t, e = "", n = null) {
                u.formSwitch(t, e, (function(t, e) {
                    n && n(t, e)
                }))
            },
            uploadFile: function(t, e = null, n = "", a = "xls|xlsx", i = 10240, o = {}) {
                u.uploadFile(t, (function(t, n) {
                    e && e(t, n)
                }), n, a, i, o)
            }
        };
        u.verify(),
        r.on("submit(submitForm)", (function(t) {
            return u.submitForm(t.field, null, (function(t, e) {
                console.log("保存成功回调")
            })),
            !1
        })),
        r.on("submit(searchForm)", (function(t) {
            return u.searchForm(l, t),
            !1
        })),
        f(".btnOption").click((function() {
            null != (a = f(this).attr("data-param")) && (console.log(a), a = JSON.parse(a), console.log(a));
            var t = b.getCheckData(n);
            switch (f(this).attr("lay-event")) {
            case "add":
                u.edit(i, 0, s, h, a, (function(t, e) {
                    2 == e && location.reload()
                }), m);
                break;
            case "batchDrop":
                (o = {
                    title: "批量删除"
                }).url = cUrl + "/batchDelete",
                o.data = t,
                o.confirm = !0,
                o.type = "GET",
                u.batchFunc(o, (function() {
                    e.reload()
                }));
                break;
            case "batchCache":
                (o = {
                    title: "批量重置缓存"
                }).url = cUrl + "/batchCache",
                o.data = t,
                o.confirm = !0,
                o.type = "GET",
                u.batchFunc(o, (function() {
                    e.reload()
                }));
                break;
            case "batchEnable":
                (o = {
                    title: "批量启用状态"
                }).url = cUrl + "/batchStatus",
                o.param = a,
                o.data = t,
                o.form = "submitForm",
                o.confirm = !0,
                o.show_tips = "处理中...",
                o.type = "POST",
                u.batchFunc(o, (function() {
                    e.reload()
                }));
                break;
            case "batchDisable":
                (o = {
                    title: "批量禁用状态"
                }).url = cUrl + "/batchStatus",
                o.param = a,
                o.data = t,
                o.confirm = !0,
                o.show_tips = "处理中...",
                o.type = "POST",
                u.batchFunc(o, (function() {
                    e.reload()
                }));
                break;
            case "export":
                var a = [],
                o = f(".layui-form-item [name]").serializeArray();
                f.each(o, (function() {
                    a.push(this.name + "=" + this.value)
                })),
                (o = {
                    title: "导出数据"
                }).url = cUrl + "/export",
                o.data = t,
                o.confirm = !0,
                o.type = "POST",
                o.show_tips = "数据准备中...",
                o.param = a,
                u.batchFunc(o, (function(t, e) {
                    window.location.href = "/common/download?fileName=" + encodeURI(t.data) + "&isDelete=" + !0
                }));
                break;
            case "import":
                u.uploadFile("import", (function(t, e) {}))
            }
        })),
        window.formClose = function() {
            var t = parent.layer.getFrameIndex(window.name);
            parent.layer.close(t)
        },
        t("func", b)
    }))
}]);