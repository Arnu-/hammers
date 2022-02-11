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
 * 栏目列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Data
public class ItemCateListVo {

    /**
     * 栏目ID
     */
    private Integer id;

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Integer pid;

    /**
     * 站点ID
     */
    private Integer itemId;

    /**
     * 站点名称
     */
    private String itemName;

    /**
     * 拼音(全)
     */
    private String pinyin;

    /**
     * 拼音(简)
     */
    private String code;

    /**
     * 有无封面:1有 2无
     */
    private Integer isCover;

    /**
     * 有无封面描述
     */
    private String isCoverName;

    /**
     * 封面
     */
    private String cover;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 状态：1启用 2停用
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 备注
     */
    private String note;

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

    /**
     * 是否有子级
     */
    private boolean haveChild;

}