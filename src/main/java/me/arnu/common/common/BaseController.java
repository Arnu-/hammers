/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.common;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基类控制器
 */
public class BaseController {

    /**
     * 构造函数
     */
    public BaseController() {

    }

    /**
     * 列表页
     *
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        return this.render();
    }

    /**
     * 获取记录详情
     *
     * @param id    记录ID
     * @param model 模型
     * @return
     */
    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        return this.render();
    }

    /**
     * 渲染模板
     *
     * @return
     */
    public String render() {
        return this.render("");
    }

    /**
     * 渲染模板
     *
     * @param tpl 模板路径
     * @return
     */
    public String render(String tpl) {
        if (StringUtils.isEmpty(tpl)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String url = request.getRequestURI();
            return url.substring(1);
        } else {
            return tpl;
        }
    }

}
