package me.arnu.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 登录信息默认配置
 */
// 让Spring启动的时候扫描到该类，并添加到Spring容器中
@Configuration
// 设置前缀
@ConfigurationProperties(prefix = "spring.login")
public class LoginConfig {
    /**
     * 默认用户名，会显示在登录页 默认空
     */
    private String defaultUser;

    /**
     * 默认密码，会显示在登录页 默认空
     */
    private String defaultPwd;

    /**
     * 默认验证码，会显示在登录页 默认空
     */
    private String defaultCaptcha;

    /**
     * 默认是否勾选记住我 默认false
     */
    private Boolean defaultRemember;
    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser == null ? "" : defaultUser;
    }

    public String getDefaultUser() {
        return defaultUser == null ? "" : defaultUser;
    }
    public String getDefaultCaptcha() {
        return defaultCaptcha == null ? "" : defaultCaptcha;
    }

    public void setDefaultCaptcha(String defaultCaptcha) {
        this.defaultCaptcha = defaultCaptcha == null ? "" : defaultCaptcha;
    }

    public String getDefaultPwd() {
        return defaultPwd == null ? "" : defaultPwd;
    }

    public void setDefaultPwd(String defaultPwd) {
        this.defaultPwd = defaultPwd == null ? "" : defaultPwd;
    }

    public Boolean getDefaultRemember() {
        return defaultRemember == null ? false : defaultRemember;
    }

    public void setDefaultRemember(Boolean defaultRemember) {
        this.defaultRemember = defaultRemember == null ? false : defaultRemember;
    }

}
