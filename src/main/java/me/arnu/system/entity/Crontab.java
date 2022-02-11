/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_crontab")
public class Crontab extends BaseEntity {

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
     * 备注
     */
    private String note;

}