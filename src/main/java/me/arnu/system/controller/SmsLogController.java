/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.controller;


import me.arnu.common.utils.JsonResult;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import me.arnu.system.query.SmsLogQuery;
import me.arnu.system.service.ISmsLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import me.arnu.common.common.BaseController;

/**
 * <p>
 * 短信日志 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Controller
@RequestMapping("/smslog")
public class SmsLogController extends BaseController {

    @Autowired
    private ISmsLogService smsLogService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:smslog:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(SmsLogQuery query) {
        return smsLogService.getList(query);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @RequiresPermissions("sys:smslog:delete")
    @Log(title = "短信日志", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return smsLogService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:smslog:dall")
    @Log(title = "短信日志", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return smsLogService.deleteByIds(ids);
    }

}
