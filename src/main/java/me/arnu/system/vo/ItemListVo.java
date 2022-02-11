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
 * 站点列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class ItemListVo {

    /**
     * 站点ID
     */
    private Integer id;

    /**
     * 站点名称
     */
    private String name;

    /**
     * 站点类型:1普通站点 2其他
     */
    private Integer type;

    /**
     * 站点类型描述
     */
    private String typeName;

    /**
     * 站点地址
     */
    private String url;

    /**
     * 站点图片
     */
    private String image;

    /**
     * 站点图片
     */
    private String imageUrl;

    /**
     * 是否二级域名:1是 2否
     */
    private Integer isDomain;

    /**
     * 是否二级域名描述
     */
    private String isDomainName;

    /**
     * 状态：1在用 2停用
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 站点备注
     */
    private String note;

    /**
     * 显示顺序
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