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

/**
 * 系统参数配置类
 */
@Configuration
@Data
public class SystemConfig {

    /**
     * 系统名称
     */
    public static String fullName;

    /**
     * 系统简称
     */
    public static String nickName;

    /**
     * 系统版本号
     */
    public static String version;

    /**
     * 系统名称
     *
     * @param name 名称
     */
    @Value("${system.fullName}")
    public void setFullName(String name) {
        fullName = name;
    }

    /**
     * 系统简称
     *
     * @param name 名称
     */
    @Value("${system.nickName}")
    public void setNickName(String name) {
        nickName = name;
    }

    /**
     * 系统版本号
     *
     * @param ver 版本号
     */
    @Value("${system.version}")
    public void setVersion(String ver) {
        version = ver;
    }

}
