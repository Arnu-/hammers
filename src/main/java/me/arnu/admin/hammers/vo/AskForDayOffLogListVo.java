/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.arnu.common.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 请假记录列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
public class AskForDayOffLogListVo {

    /**
     * 请假记录ID
     */
    private Integer id;

    /**
     * 员工号
     */
    @Excel(name = "员工号")
    private String employeeId;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String realName;

    /**
     * 请假类型
     */
    private Integer dayOffTypeId;

    @Excel(name = "请假类型")
    private String dayOffType;

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @Excel(name = "开始日期")
    private Date startDate;

    /**
     * 开始半天，选择上午、下午
     */
    @Excel(name = "开始上下午")
    private String startHalfDay;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    @Excel(name = "结束时间")
    private Date endDate;

    /**
     * 结束半天，选择上午、下午
     */
    @Excel(name = "结束上下午")
    private String endHalfDay;

    /**
     * 请假天数
     */
    @Excel(name = "请假天数")
    private Float days;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
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