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
 * 系统消息查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class MessageQuery extends BaseQuery {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 发送方式：1系统 2短信 3邮件 4微信 5推送
     */
    private Integer type;

    /**
     * 发送状态：1已发送 2未发送
     */
    private Integer sendStatus;

}
