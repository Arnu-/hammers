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
 * 短信日志列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class SmsLogListVo {

    /**
     * 短信日志ID
     */
    private Integer id;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 发送类型：1用户注册 2修改密码 3找回密码 4换绑手机号验证 5换绑手机号 6钱包提现 7设置支付密码 8系统通知
     */
    private Integer type;

    /**
     * 发送类型描述
     */
    private String typeName;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 项目签名
     */
    private String sign;

    /**
     * 
     */
    private String templateCode;

    /**
     * 参数
     */
    private String params;

    /**
     * 阿里云返回的
     */
    private String bizId;

    /**
     * 阿里云返回的code
     */
    private String code;

    /**
     * 阿里云返回的
     */
    private String message;

    /**
     * 状态：1成功 2失败 3待处理
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

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