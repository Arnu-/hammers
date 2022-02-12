/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.controller;


import me.arnu.common.utils.JsonResult;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import me.arnu.admin.hammers.entity.EmployeeSpecialAnnualVacationSetting;
import me.arnu.admin.hammers.query.EmployeeSpecialAnnualVacationSettingQuery;
import me.arnu.admin.hammers.service.IEmployeeSpecialAnnualVacationSettingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import me.arnu.common.common.BaseController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 员工年假特殊设置 控制器
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Controller
@RequestMapping("/employeespecialannualvacationsetting")
public class EmployeeSpecialAnnualVacationSettingController extends BaseController {

    @Autowired
    private IEmployeeSpecialAnnualVacationSettingService employeeSpecialAnnualVacationSettingService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:employeespecialannualvacationsetting:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(EmployeeSpecialAnnualVacationSettingQuery query) {
        return employeeSpecialAnnualVacationSettingService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:employeespecialannualvacationsetting:add")
    @Log(title = "员工年假特殊设置", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody EmployeeSpecialAnnualVacationSetting entity) {
        return employeeSpecialAnnualVacationSettingService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:employeespecialannualvacationsetting:update")
    @Log(title = "员工年假特殊设置", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody EmployeeSpecialAnnualVacationSetting entity) {
        return employeeSpecialAnnualVacationSettingService.edit(entity);
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
            info = employeeSpecialAnnualVacationSettingService.info(id);
        }
        model.addAttribute("info", info);
        return super.edit(id, model);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @RequiresPermissions("sys:employeespecialannualvacationsetting:delete")
    @Log(title = "员工年假特殊设置", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return employeeSpecialAnnualVacationSettingService.deleteById(id);
    }
	
	/**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @RequiresPermissions("sys:employeespecialannualvacationsetting:dall")
    @Log(title = "员工年假特殊设置", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return employeeSpecialAnnualVacationSettingService.deleteByIds(ids);
    }

}
