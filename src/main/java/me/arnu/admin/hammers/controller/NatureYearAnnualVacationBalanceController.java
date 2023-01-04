/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.controller;


import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import me.arnu.admin.hammers.vo.EmployeeListVo;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import me.arnu.admin.hammers.entity.NatureYearAnnualVacationBalance;
import me.arnu.admin.hammers.query.NatureYearAnnualVacationBalanceQuery;
import me.arnu.admin.hammers.service.INatureYearAnnualVacationBalanceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import me.arnu.common.common.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自然年结余天数 控制器
 * </p>
 *
 * @author Arnu
 * @since 2022-02-13
 */
@Controller
@RequestMapping("/natureyearannualvacationbalance")
public class NatureYearAnnualVacationBalanceController extends BaseController {

    @Autowired
    private INatureYearAnnualVacationBalanceService natureYearAnnualVacationBalanceService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:natureyearannualvacationbalance:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(NatureYearAnnualVacationBalanceQuery query) {
        return natureYearAnnualVacationBalanceService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:natureyearannualvacationbalance:add")
    @Log(title = "自然年结余天数", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody NatureYearAnnualVacationBalance entity) {
        return natureYearAnnualVacationBalanceService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:natureyearannualvacationbalance:update")
    @Log(title = "自然年结余天数", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody NatureYearAnnualVacationBalance entity) {
        return natureYearAnnualVacationBalanceService.edit(entity);
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
            info = natureYearAnnualVacationBalanceService.info(id);
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
    @RequiresPermissions("sys:natureyearannualvacationbalance:delete")
    @Log(title = "自然年结余天数", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return natureYearAnnualVacationBalanceService.deleteById(id);
    }

    /**
     * 打开自动计算年假结余界面
     *
     * @return
     */
    @GetMapping("/autoBalanceCalc")
    public String autoBalanceCalc() {
        return this.render();
    }


	/**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @RequiresPermissions("sys:natureyearannualvacationbalance:dall")
    @Log(title = "自然年结余天数", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return natureYearAnnualVacationBalanceService.deleteByIds(ids);
    }

    /**
     * 批量生成年假结余
     * @param list 数据
     * @param overwrite
     * @return
     */
    @RequiresPermissions("sys:natureyearannualvacationbalance:add")
    @Log(title = "自然年结余天数", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/addBatch")
    public JsonResult addBatch(@RequestBody List<EmployeeDayOffSummaryVo> list,int year,  Boolean overwrite) {
        overwrite = overwrite != null && overwrite;
        // return JsonResult.success();
        return natureYearAnnualVacationBalanceService.addBatch(list,year, overwrite);
    }
}
