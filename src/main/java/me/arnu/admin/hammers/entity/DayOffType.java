/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.entity;

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
 * 请假类型
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_day_off_type")
public class DayOffType extends BaseEntity {

    /**
     * 默认年假类型
     */
    public static final Integer ANNUAL_VACATION = 1;

    /**
     * 默认事假类型
     */
    public static final Integer PERSONAL_LEAVE = 2;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 备注
     */
    private String note;

}