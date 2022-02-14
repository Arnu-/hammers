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
 * 自然年结余天数
 * </p>
 *
 * @author Arnu
 * @since 2022-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_nature_year_annual_vacation_balance")
public class NatureYearAnnualVacationBalance extends BaseEntity {

    /**
     * 员工
     */
    private Integer employeeId;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 剩余天数
     */
    private Float days;

    /**
     * 备注
     */
    private String note;

}