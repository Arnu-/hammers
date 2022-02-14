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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import me.arnu.common.common.BaseController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

    // 单个文件最大上传大小(10M)
    private long fileMaxSize = 1024 * 1024 * 10;
    // 最大文件大小(100M)
    private long maxSize = 1024 * 1024 * 100;

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    @RequiresPermissions("sys:level:import")
    @Log(title = "职级", businessType = BusinessType.IMPORT)
    @ResponseBody
    @PostMapping("/import")
    public JsonResult importLevel(HttpServletRequest request) {
        // List<LevelListVo> levelListVoList = levelService.exportExcel(levelQuery);
        // ExcelUtils<LevelListVo> excelUtils = new ExcelUtils<LevelListVo>(LevelListVo.class);
        // List<LevelListVo> list = excelUtils.importExcel(is);
        // return excelUtils.exportExcel(levelListVoList, "职级列表");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        // 第二步：解析request
        if (isMultipart) {
            // 设置环境:创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 阀值,超过这个值才会写到临时目录,否则在内存中
            factory.setSizeThreshold(1024 * 1024 * 10);
            // 设置上传文件的临时目录
            factory.setRepository(new File(""));
            // 核心操作类:创建一个文件上传解析器。
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置文件名称编码(解决上传"文件名"的中文乱码)
            upload.setHeaderEncoding("UTF-8");
            // 限制单个文件上传大小
            upload.setFileSizeMax(fileMaxSize);
            // 限制总上传文件大小
            upload.setSizeMax(maxSize);
            // 使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 第3步：处理uploaded items
            if (items != null && items.size() > 0) {
                ExcelUtils<LevelListVo> excelUtils = new ExcelUtils<LevelListVo>(LevelListVo.class);
                Iterator<FileItem> iter = items.iterator();
                // 文件域对象
                List<LevelListVo> list = new ArrayList<>();
                // 表单字段
                Map<String, String> fields = new HashMap<String, String>();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    // 处理所有表单元素和文件域表单元素
                    if (item.isFormField()) {
                        // 如果fileitem中封装的是普通输入项的数据（输出名、值）
                        String name = item.getFieldName();// 普通输入项数据的名
                        String value = item.getString();
                        fields.put(name, value);
                    } else {
                        // 如果fileitem中封装的是上传文件，得到上传的文件名称
                        // 文件域表单元素
                        try {
                            list = excelUtils.importExcel(item.getInputStream());
                            // list.add(item);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                return JsonResult.success("上传成功", list);
            }
        }
        return JsonResult.error("无文件");
    }
}
