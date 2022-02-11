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
 * 广告位描述列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-01
 */
@Data
public class AdSortListVo {

    /**
     * 广告位描述ID
     */
    private Integer id;

    /**
     * 广告位名称
     */
    private String name;

    /**
     * 广告位描述
     */
    private String note;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 站点名称
     */
    private String itemName;

    /**
     * 栏目ID
     */
    private Integer cateId;

    /**
     * 栏目名称
     */
    private String cateName;

    /**
     * 广告页面位置
     */
    private Integer locId;

    /**
     * 站点类型：1PC网站 2WAP手机站 3小程序 4APP移动端
     */
    private Integer platform;

    /**
     * 站点类型描述
     */
    private String platformName;

    /**
     * 广告位排序
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