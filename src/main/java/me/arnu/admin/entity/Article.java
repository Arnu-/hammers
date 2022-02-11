/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 文章管理
 * </p>
 *
 * @author Arnu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cms_article")
public class Article extends BaseEntity {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 首张图片编号
     */
    private String cover;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 浏览次数
     */
    private Integer viewNum;

    /**
     * 是否置顶：1已置顶 2未置顶
     */
    private Integer isTop;

    /**
     * 文章导读
     */
    private String guide;

    /**
     * 图集
     */
    private String imgs;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 状态：1已审核 2待审核 3审核失败
     */
    private Integer status;

}