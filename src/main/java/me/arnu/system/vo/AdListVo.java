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
 * 广告列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class AdListVo {

    /**
     * 广告ID
     */
    private Integer id;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告位ID
     */
    private Integer sortId;

    /**
     * 广告位名称
     */
    private String sortName;

    /**
     * 广告图片
     */
    private String cover;

    /**
     * 广告图片
     */
    private String coverUrl;

    /**
     * 广告格式：1图片 2文字 3视频 4推荐
     */
    private Integer type;

    /**
     * 广告格式描述
     */
    private String typeName;

    /**
     * 广告描述
     */
    private String description;

    /**
     * 广告内容
     */
    private String content;

    /**
     * 广告链接
     */
    private String url;

    /**
     * 广告宽度
     */
    private Integer width;

    /**
     * 广告高度
     */
    private Integer height;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    /**
     * 广告点击次数
     */
    private Integer viewNum;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 排序
     */
    private Integer sort;

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