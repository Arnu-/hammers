/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里短信配置类
 */
// 让Spring启动的时候扫描到该类，并添加到Spring容器中
@Configuration
// 设置前缀
@ConfigurationProperties(prefix = "spring.alisms")
@Data
public class AliSmsConfig {

    /**
     * KEY
     */
    private String accessKeyId;

    /**
     * 密钥
     */
    private String accessKeySecret;

    /**
     * 区域ID
     */
    private String regionId;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板ID
     */
    private String templateCode;

}
