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
import me.arnu.system.entity.ItemCate;
import me.arnu.system.query.ItemCateQuery;
import me.arnu.system.service.IItemCateService;
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
 * 栏目 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Controller
@RequestMapping("/itemcate")
public class ItemCateController extends BaseController {

    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:itemcate:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(ItemCateQuery query) {
        return itemCateService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:itemcate:add")
    @Log(title = "栏目", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody ItemCate entity) {
        return itemCateService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:itemcate:update")
    @Log(title = "栏目", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody ItemCate entity) {
        return itemCateService.edit(entity);
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
            info = itemCateService.info(id);
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
    @RequiresPermissions("sys:itemcate:delete")
    @Log(title = "栏目", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return itemCateService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:itemcate:dall")
    @Log(title = "栏目", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return itemCateService.deleteByIds(ids);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:itemcate:status")
    @Log(title = "栏目", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/setStatus")
    public JsonResult setStatus(@RequestBody ItemCate entity) {
        return itemCateService.setStatus(entity);
    }

    /**
     * 根据站点ID获取栏目列表
     *
     * @param itemId 站点ID
     * @return
     */
    @ResponseBody
    @PostMapping("/getItemCateListByItemId")
    public JsonResult getItemCateListByItemId(Integer itemId) {
        List<Map<String, Object>> cateList = itemCateService.getItemCateListByItemId(itemId, 0);
        return JsonResult.success("操作成功", cateList);
    }
}
