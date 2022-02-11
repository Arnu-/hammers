/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.query;

import me.arnu.common.common.BaseQuery;
import lombok.Data;

/**
 * <p>
 * 短信日志查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class SmsLogQuery extends BaseQuery {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 发送类型：1用户注册 2修改密码 3找回密码 4换绑手机号验证 5换绑手机号 6钱包提现 7设置支付密码 8系统通知
     */
    private Integer type;

    /**
     * 状态：1成功 2失败 3待处理
     */
    private Integer status;

}
