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
import me.arnu.system.query.MessageQuery;
import me.arnu.system.service.IMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import me.arnu.common.common.BaseController;

/**
 * <p>
 * 系统消息 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private IMessageService messageService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:message:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(MessageQuery query) {
        return messageService.getList(query);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @RequiresPermissions("sys:message:delete")
    @Log(title = "系统消息", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return messageService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:message:dall")
    @Log(title = "系统消息", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return messageService.deleteByIds(ids);
    }

}
