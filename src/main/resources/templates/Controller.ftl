/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package ${packageName}.controller;


import me.arnu.common.utils.JsonResult;
import me.arnu.common.annotation.Log;
import me.arnu.common.enums.BusinessType;
import ${packageName}.entity.${entityName};
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.I${entityName}Service;
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
 * ${tableAnnotation} 控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Controller
@RequestMapping("/${entityName?lower_case}")
public class ${entityName}Controller extends BaseController {

    @Autowired
    private I${entityName}Service ${entityName?uncap_first}Service;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:${entityName?lower_case}:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(${entityName}Query query) {
        return ${entityName?uncap_first}Service.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:${entityName?lower_case}:add")
    @Log(title = "${tableAnnotation}", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody ${entityName} entity) {
        return ${entityName?uncap_first}Service.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:${entityName?lower_case}:update")
    @Log(title = "${tableAnnotation}", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody ${entityName} entity) {
        return ${entityName?uncap_first}Service.edit(entity);
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
            info = ${entityName?uncap_first}Service.info(id);
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
    @RequiresPermissions("sys:${entityName?lower_case}:delete")
    @Log(title = "${tableAnnotation}", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return ${entityName?uncap_first}Service.deleteById(id);
    }
	
	/**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @RequiresPermissions("sys:${entityName?lower_case}:dall")
    @Log(title = "${tableAnnotation}", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return ${entityName?uncap_first}Service.deleteByIds(ids);
    }

<#if model_column?exists>
    <#list model_column as model>
    <#if model.changeColumnName?uncap_first = 'status'>
    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:${entityName?lower_case}:status")
    @Log(title = "${tableAnnotation}", businessType = BusinessType.STATUS)
    @ResponseBody
    @PostMapping("/setStatus")
    public JsonResult setStatus(@RequestBody ${entityName} entity) {
        return ${entityName?uncap_first}Service.setStatus(entity);
    }
    </#if>
    </#list>
</#if>
}
