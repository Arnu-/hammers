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
import me.arnu.admin.hammers.entity.LevelAnnualVacationSetting;
import me.arnu.admin.hammers.query.LevelAnnualVacationSettingQuery;
import me.arnu.admin.hammers.service.ILevelAnnualVacationSettingService;
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
 * 级别年假基数 控制器
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Controller
@RequestMapping("/levelannualvacationsetting")
public class LevelAnnualVacationSettingController extends BaseController {

    @Autowired
    private ILevelAnnualVacationSettingService levelAnnualVacationSettingService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:levelannualvacationsetting:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(LevelAnnualVacationSettingQuery query) {
        return levelAnnualVacationSettingService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:levelannualvacationsetting:add")
    @Log(title = "级别年假基数", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody LevelAnnualVacationSetting entity) {
        return levelAnnualVacationSettingService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:levelannualvacationsetting:update")
    @Log(title = "级别年假基数", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody LevelAnnualVacationSetting entity) {
        return levelAnnualVacationSettingService.edit(entity);
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
            info = levelAnnualVacationSettingService.info(id);
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
    @RequiresPermissions("sys:levelannualvacationsetting:delete")
    @Log(title = "级别年假基数", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return levelAnnualVacationSettingService.deleteById(id);
    }
	
	/**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @RequiresPermissions("sys:levelannualvacationsetting:dall")
    @Log(title = "级别年假基数", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return levelAnnualVacationSettingService.deleteByIds(ids);
    }

}
