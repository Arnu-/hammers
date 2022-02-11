/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.controller;


import me.arnu.common.common.BaseController;
import me.arnu.common.service.IUploadService;
import me.arnu.common.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 角色菜单关联表 前端控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-02-26
 */
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {

    @Autowired
    private IUploadService uploadService;

    /**
     * 上传图片
     *
     * @param request 网络请求
     * @param name    目录名
     * @return
     */
    @PostMapping("/uploadImage")
    public JsonResult uploadImage(HttpServletRequest request, String name) {
        return uploadService.uploadImage(request, name);
    }

    /**
     * 上传文件
     *
     * @param request
     * @param name    目录名
     * @return
     */
    @PostMapping("/uploadFile")
    public JsonResult uploadFile(HttpServletRequest request, String name) {
        return uploadService.uploadFile(request, name);
    }

    /**
     * 上传图片
     *
     * @param request 网络请求
     * @param name    目录名
     * @return
     */
    @PostMapping("/kindeditorImage")
    public String kindeditorImage(HttpServletRequest request, String name) {
        return uploadService.kindeditorImage(request, name);
    }

}
