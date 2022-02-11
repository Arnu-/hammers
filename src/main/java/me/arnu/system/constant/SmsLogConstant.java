/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 短信日志 模块常量
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
public class SmsLogConstant {

    /**
     * 发送类型
     */
    public static Map<Integer, String> SMSLOG_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "用户注册");
            put(2, "修改密码");
            put(3, "找回密码");
            put(4, "换绑手机号验证");
            put(5, "换绑手机号");
            put(6, "钱包提现");
            put(7, "设置支付密码");
            put(8, "系统通知");
        }
    };
    /**
     * 状态
     */
    public static Map<Integer, String> SMSLOG_STATUS_LIST = new HashMap<Integer, String>() {
        {
            put(1, "成功");
            put(2, "失败");
            put(3, "待处理");
        }
    };
}