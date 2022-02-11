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
 * 通知公告查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class NoticeQuery extends BaseQuery {

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知来源：1云平台
     */
    private Integer source;

    /**
     * 是否置顶：1已置顶 2未置顶
     */
    private Integer isTop;

    /**
     * 发布状态：1草稿箱 2立即发布 3定时发布
     */
    private Integer status;

    /**
     * 推送状态：1已推送 2未推送
     */
    private Integer isSend;

}
