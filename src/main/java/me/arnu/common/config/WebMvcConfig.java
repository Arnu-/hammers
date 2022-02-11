/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 设置虚拟路径，访问绝对路径下资源
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 静态资源地址
     */
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    /**
     * 文件上传目录
     */
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    /**
     * 后台首页地址
     */
    @Value("${shiro.user.indexUrl}")
    private String indexUrl;

    /**
     * 默认首页的设置
     * 备注：当输入域名是可以自动跳转到默认指定的网页
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:" + indexUrl);
    }

    /**
     * 注册静态文件的自定义映射路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 定义到新文件夹
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // 定义到指定目录
        registry.addResourceHandler(staticAccessPath)
                .addResourceLocations("file:" + uploadFolder);
    }
}
