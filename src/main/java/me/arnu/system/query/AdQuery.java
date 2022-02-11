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
 * 广告查询条件
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class AdQuery extends BaseQuery {

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告格式：1图片 2文字 3视频 4推荐
     */
    private Integer type;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

}
