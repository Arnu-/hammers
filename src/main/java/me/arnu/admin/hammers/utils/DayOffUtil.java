/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/3/22                                
*/
package me.arnu.admin.hammers.utils;

import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;

import java.util.Calendar;
import java.util.Date;

/**
 * 请假相关的帮助类
 */
public class DayOffUtil {
    /**
     * 计算年假剩余状况
     *
     * @param vo                    取出来的基础数据
     * @param firstDayOff           上部分请假（即上年结余年假有效期间的请假）
     * @param secondDayOff          下部分请假（即上年结余年假已经失效期间的请假）
     * @param now                   现在日期（即计算假期结余的日期）
     * @param lastAnnualVExpiryDate （开始日期）即上年年假失效日期
     */
    public static void calcAnnualVacation(EmployeeDayOffSummaryVo vo, double firstDayOff, double secondDayOff, Date now,
            Date lastAnnualVExpiryDate) {
        // 5.1 取出对应人的请假状态
        // 5.2 根据请假前、后半年设定来计算
        // 5.3 去年残留还有就先扣去年，去年没有就开始扣今年。
        double lastYearRemain = vo.getLastYearAnnualVacationBalance();


        vo.setWorkYear(calcWorkYearToThisYearEnd(vo.getEnrollmentDate()));
        if (vo.getWorkYear() < 1) {
            vo.setActuallyAnnualVacationDays(vo.getActuallyAnnualVacationDays() * vo.getWorkYear());
        }

        double thisYearRemain = vo.getActuallyAnnualVacationDays();
        if (lastYearRemain >= firstDayOff) {
            lastYearRemain = lastYearRemain - firstDayOff;
            thisYearRemain = thisYearRemain - secondDayOff;
        } else {
            thisYearRemain = thisYearRemain + lastYearRemain - firstDayOff - secondDayOff;
            lastYearRemain = 0d;
        }
        vo.setLastYearRemainAnnualVacationDays(lastYearRemain);
        vo.setThisYearRemainAnnualVacationDays(thisYearRemain);
        vo.setFirstAnnualVacationDayOffDays(firstDayOff);
        vo.setSecondAnnualVacationDayOffDays(secondDayOff);
        // 用于处理显示上年有效年假剩余的数字，过期了就统统显示为0
        if (now.getTime() >= lastAnnualVExpiryDate.getTime()) {
            vo.setLastYearRemainAnnualVacationDays(0d);
            vo.setAllAnnualVacationDays(vo.getActuallyAnnualVacationDays());
        } else {
            vo.setLastYearRemainAnnualVacationDays(lastYearRemain);
            vo.setAllAnnualVacationDays(vo.getActuallyAnnualVacationDays()
                    + vo.getLastYearAnnualVacationBalance());
        }
        vo.setAnnualVacationDayOffDays(firstDayOff + secondDayOff);
    }

    /**
     * 计算从入职到今年年底的工龄
     * @param enrollmentDate 入职日期
     * @return 工龄
     */
    public static float calcWorkYearToThisYearEnd(Date enrollmentDate) {
        Calendar c = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        e.setTime(enrollmentDate);
        float workYear = c.get(Calendar.YEAR) - e.get(Calendar.YEAR) + (365f - e.get(Calendar.DAY_OF_YEAR)) / 365f;
        return workYear;
    }

}
