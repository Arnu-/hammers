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
 * 消息模板列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class MessageTemplateListVo {

    /**
     * 消息模板ID
     */
    private Integer id;

    /**
     * 模板CODE
     */
    private String code;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 模板类型：1系统模板 2短信模板 3邮件模板 4微信模板 5推送模板
     */
    private Integer type;

    /**
     * 模板类型描述
     */
    private String typeName;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 状态：1在用 2停用
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