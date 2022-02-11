/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CommonConfig {

    /**
     * 图片域名
     */
    public static String imageURL;

    /**
     * 是否演示环境：true是,false否
     */
    public static boolean appDebug;

    /**
     * 图片域名赋值
     *
     * @param url 域名地址
     */
    @Value("${server.IMAGE_URL}")
    public void setImageURL(String url) {
        imageURL = url;
    }

    /**
     * 是否演示模式
     *
     * @param debug
     */
    @Value("${spring.app_debug}")
    public void setAppDebug(boolean debug) {
        appDebug = debug;
    }

}
