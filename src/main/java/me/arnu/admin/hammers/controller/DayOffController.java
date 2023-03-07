/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.controller;


import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.service.IDayOffService;
import me.arnu.common.annotation.Log;
import me.arnu.common.common.BaseController;
import me.arnu.common.enums.BusinessType;
import me.arnu.common.utils.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 请假类型 控制器
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Controller
@RequestMapping("/dayoff")
public class DayOffController extends BaseController {

    @Autowired
    private IDayOffService dayOffService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:dayoff:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(DayOffTypeQuery query) {
        return dayOffService.getList(query);
    }

    @ResponseBody
    @PostMapping("/annualDetail")
    public JsonResult annualDetail(DayOffTypeQuery query) {
        return dayOffService.annualDetail(query);
    }

    @ResponseBody
    @PostMapping("/annualDetailJ")
    public JsonResult annualDetailJ(@RequestBody DayOffTypeQuery query) {
        return dayOffService.annualDetail(query);
    }


    /**
     * 打开显示年假计算页面
     *
     * @return
     */
    @GetMapping("/annualDetail")
    public String annualDetail() {
        return this.render();
    }

    @ResponseBody
    @GetMapping("/fields")
    public JsonResult getFields(){
        return dayOffService.getFields();
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:dayoff:add")
    @Log(title = "请假类型", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody DayOffType entity) {
        return dayOffService.edit(entity);
    }

    /**
     * 获取记录详情
     *
     * @param id    记录ID
     * @param model 模型
     * @return
     */
    @Override
    public String edit(Integer id, Model model) {
        Map<String, Object> info = new HashMap<>();
        if (id != null && id > 0) {
            info = dayOffService.info(id);
        }
        model.addAttribute("info", info);
        return super.edit(id, model);
    }
}
