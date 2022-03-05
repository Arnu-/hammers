/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.controller;


import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.query.AskForDayOffLogQuery;
import me.arnu.admin.hammers.service.IAskForDayOffLogService;
import me.arnu.admin.hammers.vo.AskForDayOffLogListVo;
import me.arnu.common.annotation.Log;
import me.arnu.common.common.BaseController;
import me.arnu.common.enums.BusinessType;
import me.arnu.common.utils.ExcelUtils;
import me.arnu.common.utils.JsonResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * <p>
 * 请假记录 控制器
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Controller
@RequestMapping("/askfordayofflog")
public class AskForDayOffLogController extends BaseController {

    @Autowired
    private IAskForDayOffLogService askForDayOffLogService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @RequiresPermissions("sys:askfordayofflog:list")
    @ResponseBody
    @PostMapping("/list")
    public JsonResult list(AskForDayOffLogQuery query) {
        return askForDayOffLogService.getList(query);
    }

    /**
     * 添加记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:askfordayofflog:add")
    @Log(title = "请假记录", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody AskForDayOffLog entity) {
        return askForDayOffLogService.edit(entity);
    }

    /**
     * 修改记录
     *
     * @param entity 实体对象
     * @return
     */
    @RequiresPermissions("sys:askfordayofflog:update")
    @Log(title = "请假记录", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(@RequestBody AskForDayOffLog entity) {
        return askForDayOffLogService.edit(entity);
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
            info = askForDayOffLogService.info(id);
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
    @RequiresPermissions("sys:askfordayofflog:delete")
    @Log(title = "请假记录", businessType = BusinessType.DELETE)
    @ResponseBody
    @GetMapping("/delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return askForDayOffLogService.deleteById(id);
    }
	
	/**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @RequiresPermissions("sys:askfordayofflog:dall")
    @Log(title = "请假记录", businessType = BusinessType.BATCH_DELETE)
    @ResponseBody
    @GetMapping("/batchDelete/{ids}")
    public JsonResult batchDelete(@PathVariable("ids") String ids) {
        return askForDayOffLogService.deleteByIds(ids);
    }


    /**
     * 导入数据表，打开导入页面
     *
     * @return
     */
    @GetMapping("/import")
    public String importEmp() {
        return this.render();
    }

    /**
     * 获取导入数据的模板
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/importTemplateExcel")
    public JsonResult importTemplateExcel() {
        ExcelUtils<AskForDayOffLogListVo> excelUtils = new ExcelUtils<AskForDayOffLogListVo>(AskForDayOffLogListVo.class);
        JsonResult result = excelUtils.importTemplateExcel("请假表导入模板");
        return result;
    }

    // 单个文件最大上传大小(10M)
    private long fileMaxSize = 1024 * 1024 * 10;
    // 最大文件大小(100M)
    private long maxSize = 1024 * 1024 * 100;

    /**
     * 导入数据
     *
     * @return
     */
    @RequiresPermissions("sys:askfordayofflog:import")
    @Log(title = "请假", businessType = BusinessType.IMPORT)
    @ResponseBody
    @PostMapping("/importEmp")
    public JsonResult importEmp(HttpServletRequest request) {
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
                ExcelUtils<AskForDayOffLogListVo> excelUtils = new ExcelUtils<AskForDayOffLogListVo>(AskForDayOffLogListVo.class);
                Iterator<FileItem> iter = items.iterator();
                // 文件域对象
                List<AskForDayOffLogListVo> list = new ArrayList<>();
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
                return JsonResult.success("上传成功", list, list.size());
            }
        }
        return JsonResult.error("无文件");
    }

    /**
     * 批量添加，用于导入功能
     * @param list 数据
     * @return 成功或失败
     */
    @RequiresPermissions("sys:askfordayofflog:add")
    @Log(title = "员工", businessType = BusinessType.INSERT)
    @ResponseBody
    @PostMapping("/addBatch")
    public JsonResult addBatch(@RequestBody List<AskForDayOffLogListVo> list
            , Boolean autoCreateType) {
        autoCreateType = autoCreateType != null && autoCreateType;
        return askForDayOffLogService.addBatch(list
                , autoCreateType);
    }

}
