/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.controller;


import me.arnu.common.utils.ExcelUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import me.arnu.system.dto.LevelDto;
import me.arnu.system.entity.Level;
import me.arnu.system.query.LevelQuery;
import me.arnu.system.service.ILevelService;
import me.arnu.system.vo.LevelListVo;
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
 * 职级 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Controller
@RequestMapping("/level")
public class LevelController extends BaseController {

    @Autowired
    private ILevelService levelService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:level:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(LevelQuery query) {
        return levelService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:level:add")
    @Log(title = "职级", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody Level entity) {
        return levelService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:level:update")
    @Log(title = "职级", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody Level entity) {
        return levelService.edit(entity);
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
            info = levelService.info(id);
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
    @RequiresPermissions("sys:level:delete")
    @Log(title = "职级", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return levelService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:level:dall")
    @Log(title = "职级", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return levelService.deleteByIds(ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:level:status")
    @Log(title = "职级", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/setStatus")
    public JsonResult setStatus(@RequestBody Level entity) {
        return levelService.setStatus(entity);
    }

    /**
     * 批量设置状态
     *
     * @param levelDto 状态Dto
     * @return
     */
    @RequiresPermissions("sys:level:eall")
    @Log(title = "职级", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/batchStatus")
    public JsonResult batchStatus(@RequestBody LevelDto levelDto) {
        return levelService.batchStatus(levelDto);
    }

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:level:export")
    @Log(title = "职级", businessType = BusinessType.EXPORT)
    @ResponseBody
    @PostMapping("/export")
    public JsonResult export(@RequestBody LevelQuery levelQuery) {
        List<LevelListVo> levelListVoList = levelService.exportExcel(levelQuery);
        ExcelUtils<LevelListVo> excelUtils = new ExcelUtils<LevelListVo>(LevelListVo.class);
        return excelUtils.exportExcel(levelListVoList, "职级列表");
    }
}
