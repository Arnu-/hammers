package me.arnu.admin.controller;


import me.arnu.admin.entity.MemberLevel;
import me.arnu.admin.query.MemberLevelQuery;
import me.arnu.admin.service.IMemberLevelService;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import me.arnu.common.utils.JsonResult;
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
 * 会员级别表 前端控制器
 * </p>
 *
 * @author Arnu
 * @since 2021-01-28
 */
@Controller
@RequestMapping("/memberlevel")
public class MemberLevelController extends BaseController {

    @Autowired
    private IMemberLevelService memberLevelService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:memberlevel:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(MemberLevelQuery query) {
        return memberLevelService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:memberlevel:add")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody MemberLevel entity) {
        return memberLevelService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:memberlevel:update")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody MemberLevel entity) {
        return memberLevelService.edit(entity);
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
            info = memberLevelService.info(id);
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
    @RequiresPermissions("sys:memberlevel:delete")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return memberLevelService.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID(多个使用逗号","分隔)
     * @return
     */
    @RequiresPermissions("sys:memberlevel:dall")
    @Log(title = "会员等级", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return memberLevelService.deleteByIds(ids);
    }

}
