/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 短信日志
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_sms_log")
public class SmsLog extends BaseEntity {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 发送类型：1用户注册 2修改密码 3找回密码 4换绑手机号验证 5换绑手机号 6钱包提现 7设置支付密码 8系统通知
     */
    private Integer type;

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

}