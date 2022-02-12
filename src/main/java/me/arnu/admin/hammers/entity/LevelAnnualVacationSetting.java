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
 * 级别年假基数
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_level_annual_vacation_setting")
public class LevelAnnualVacationSetting extends BaseEntity {

    /**
     * 级别id
     */
    private Integer levelId;

    /**
     * 年假基数
     */
    private Integer days;

    /**
     * 备注
     */
    private String note;

}