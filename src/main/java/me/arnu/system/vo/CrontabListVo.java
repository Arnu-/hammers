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
 * 定时任务列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class CrontabListVo {

    /**
     * 定时任务ID
     */
    private Integer id;

    /**
     * 任务标题
     */
    private String title;

    /**
     * crontab格式
     */
    private String cron;

    /**
     * 延迟秒数(单位 毫秒)
     */
    private Integer delay;

    /**
     * 间隔秒数(单位 毫秒)
     */
    private Integer fixed;

    /**
     * 已执行次数
     */
    private Integer execute;

    /**
     * 状态：1正常 2暂停
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