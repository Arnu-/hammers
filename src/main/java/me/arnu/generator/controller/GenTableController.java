/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.controller;


import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.ConvertUtil;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import me.arnu.common.utils.StringUtils;
import me.arnu.generator.dto.GeneratorTableDto;
import me.arnu.generator.dto.ImportTableDto;
import me.arnu.generator.entity.GenTable;
import me.arnu.generator.query.GenTableQuery;
import me.arnu.generator.service.IGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import me.arnu.common.common.BaseController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
@Controller
@RequestMapping("/gentable")
public class GenTableController extends BaseController {

    @Autowired
    private IGenTableService genTableService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
//    @RequiresPermissions("sys:gentable:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(GenTableQuery query) {
        return genTableService.getList(query);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
//    @RequiresPermissions("sys:gentable:update")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody GenTable entity) {
        return genTableService.edit(entity);
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
        // 根据业务ID获取数据表信息
        GenTable table = genTableService.selectGenTableById(id);
        model.addAttribute("info", table);
        return this.render();
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
//    @RequiresPermissions("sys:gentable:delete")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return genTableService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
//    @RequiresPermissions("sys:gentable:batchDelete")
    @Log(title = "代码生成", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return genTableService.deleteByIds(ids);
    }


    /**
     * 导入数据表
     *
     * @return
     */
    @GetMapping("/importTable")
    public String importTable() {
        return this.render();
    }

    /**
     * 获取数据库表
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/genDbTableList")
    public JsonResult genDbTableList(GenTableQuery query) {
        return genTableService.genDbTableList(query);
    }

    /**
     * 导入数据表
     *
     * @param importTableDto 数据表名(多个逗号","分割)
     * @return
     */
    @Log(title = "导入数据表", businessType = BusinessType.IMPORT)
    @ResponseBody
    @PostMapping("/importTableSave")
    public JsonResult importTableSave(@RequestBody ImportTableDto importTableDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        String[] tableNames = ConvertUtil.toStrArray(importTableDto.getTables());
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return JsonResult.success();
    }

    /**
     * 代码生成
     *
     * @param generatorTableDto 数据表名(多个逗号","分割)
     * @return
     * @throws IOException
     */
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @ResponseBody
    @PostMapping("/batchGenCode")
    public JsonResult batchGenCode(@RequestBody GeneratorTableDto generatorTableDto) throws IOException {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        String[] tableNames = ConvertUtil.toStrArray(generatorTableDto.getTables());
        return genTableService.generatorCode(tableNames);
    }

    /**
     * 批量生成
     *
     * @param map 记录ID
     * @return
     */
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @ResponseBody
    @PostMapping("/batchGenerator")
    public JsonResult batchGenerator(@RequestBody Map<String, String> map) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        String ids = map.get("ids");
        if (StringUtils.isEmpty(ids)) {
            return JsonResult.error("数据表ID不能为空");
        }
        String[] tableIds = ids.split(",");
        List<String> stringList = new ArrayList<>();
        for (String tableId : tableIds) {
            GenTable genTable = genTableService.getById(Integer.valueOf(tableId));
            if (genTable == null) {
                continue;
            }
            stringList.add(genTable.getTableName());
        }
        String[] tableNames = stringList.toArray(new String[stringList.size()]);
        return genTableService.generatorCode(tableNames);
    }

}
