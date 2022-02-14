/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/12                                
*/
package me.arnu.admin.hammers.vo;

import lombok.Data;

import java.util.Map;

/**
 * 员工请假统计信息
 * @author Arnu
 */
@Data
public class EmployeeDayOffSummaryVo {

    private int id;
    /**
     * 员工id
     */
    private String employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工部门
     */
    private String dept;

    /**
     * 员工级别
     */
    private String level;

    /**
     * 工龄
     */
    private double workYear;

//    /**
//     * 级别对应年假天数
//     */
//    private double levelAnnualVacationDays;
//
//    /**
//     * 特殊年假天数
//     */
//    private double employeeSpecialAnnualVacationDays;

    /**
     * 实际本年年假天数
     */
    private double actuallyAnnualVacationDays;

    /**
     * 上年剩余年假天数，即到达新年1月后，上一年年假还剩下几天
     * 因为可能会有跨年调整级别的问题，以及其他可能导致今年与上年年假天数不一致的情况。
     * 所以跨年后需要缓存起来上年剩余年假天数。
     */
    private double lastYearAnnualVacationBalance;
    
    /**
     * 截至目前可用年假天数  上年年假剩余+本年年假合计减去已请年假天数
     * 计算原则：如果当前在重叠计算年假周期，那么两个部分的年假都算在一起，优先使用上一年结余
     *
     *
     */
    private double allAnnualVacationDays;

//    /**
//     * 本年剩余年假天数，即总数减请假数
//     */
//    private double thisYearRemainAnnualVacationDays;
//
    /**
     * 到达新一年后，截至结算周期结束前，上一周期剩余年假天数
     * 这里已经减去了新年的请假天数
     */
    private double lastYearRemainAnnualVacationDays;

    /**
     * 当年剩余年假天数
     * 这里已经减去了当年的请假天数
     */
    private double thisYearRemainAnnualVacationDays;
//
//    /**
//     * 进入自然年新年后，依然可以使用上已计算周期年假的请假天数
//     */
//    private double lastPeriodAnnualVacationDayOffDays;

    /**
     * 可用上年年假的请假天数
     */
    private double firstAnnualVacationDayOffDays;

    /**
     * 可用上年年假的请假天数
     */
    private double secondAnnualVacationDayOffDays;

    /**
     * 当年年假请假总数
     */
    private double annualVacationDayOffDays;

    /**
     * 其他各种假计自然年的。
     */
    private Map<String, Float> dayOffMap;
}
