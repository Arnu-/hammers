/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 登录日志列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class LoginLogListVo {

    /**
     * 登录日志ID
     */
    private Integer id;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date loginTime;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录地区
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态：1成功 2失败
     */
    private Integer status;

    /**
     * 登录状态描述
     */
    private String statusName;

    /**
     * 类型：1登录系统 2退出系统
     */
    private Integer type;

    /**
     * 类型描述
     */
    private String typeName;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加人名称
     */
    private String createUserName;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

}