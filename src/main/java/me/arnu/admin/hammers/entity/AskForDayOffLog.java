/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * <p>
 * 请假记录
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_ask_for_day_off_log")
public class AskForDayOffLog extends BaseEntity {

    /**
     * 常量，上午
     */
    @TableField(exist = false)
    public static final String MORNING = "上午";

    /**
     * 常量，下午
     */
    @TableField(exist = false)
    public static final String AFTERNOON = "下午";

    /**
     * 员工
     */
    private String employeeId;

    /**
     * 请假类型
     */
    private Integer dayOffTypeId;

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /**
     * 开始半天，选择上午、下午
     */
    private String startHalfDay;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 结束半天，选择上午、下午
     */
    private String endHalfDay;

    /**
     * 请假天数
     */
    private Float days;

    /**
     * 备注
     */
    private String note;

}