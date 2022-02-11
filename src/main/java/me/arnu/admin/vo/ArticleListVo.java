/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 文章管理列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-08-11
 */
@Data
public class ArticleListVo {

    /**
     * 文章管理ID
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 首张图片编号
     */
    private String cover;

    /**
     * 首张图片编号
     */
    private String coverUrl;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 栏目分类名称
     */
    private String cateName;

    /**
     * 浏览次数
     */
    private Integer viewNum;

    /**
     * 是否置顶：1已置顶 2未置顶
     */
    private Integer isTop;

    /**
     * 是否置顶描述
     */
    private String isTopName;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}