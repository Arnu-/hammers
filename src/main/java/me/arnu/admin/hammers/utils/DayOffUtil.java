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
     * 已过期。该方法计算不严谨。
     */
    @Deprecated
    public static void calcAnnualVacation(EmployeeDayOffSummaryVo vo, double firstDayOff, double secondDayOff, Date now,
                                          Date lastAnnualVExpiryDate) {
        // 5.1 取出对应人的请假状态
        // 5.2 根据请假前、后半年设定来计算
        // 5.3 去年残留还有就先扣去年，去年没有就开始扣今年。
        double lastYearRemain = vo.getLastYearAnnualVacationBalance();


        vo.setWorkYear(calcWorkYearToThisYearEnd(vo.getEnrollmentDate()));
        if (vo.getWorkYear() < 1) {
            vo.setActualAnnualVacationDays(vo.getActualAnnualVacationDays() * vo.getWorkYear());
        }

        double thisYearRemain = vo.getActualAnnualVacationDays();
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
            vo.setAllAnnualVacationDays(vo.getActualAnnualVacationDays());
        } else {
            vo.setLastYearRemainAnnualVacationDays(lastYearRemain);
            vo.setAllAnnualVacationDays(vo.getActualAnnualVacationDays()
                    + vo.getLastYearAnnualVacationBalance());
        }
        vo.setAnnualVacationDayOffDays(firstDayOff + secondDayOff);
    }

    /**
     * 计算从入职到今年年底的工龄
     *
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

    /**
     * 计算年假剩余状况
     *
     * @param vo                    取出来的基础数据
     * @param firstDayOff           上部分请假（即上年结余年假有效期间的请假）最小单位是0.5，
     * @param secondDayOff          下部分请假（即上年结余年假已经失效期间的请假）最小单位是0.5
     * @param now                   现在日期（即计算假期结余的日期）最小单位是0.5
     * @param lastAnnualVExpiryDate （开始日期）即上年年假失效日期
     */
    public static void calcAnnualVacationV2(EmployeeDayOffSummaryVo vo, double firstDayOff, double secondDayOff, Date now,
                                            Date lastAnnualVExpiryDate) {
        // 5.1 取出对应人的请假状态
        // 5.2 根据请假前、后半年设定来计算
        // 5.3 去年残留还有就先扣去年，去年没有就开始扣今年。
        double lastYearRemain = vo.getLastYearAnnualVacationBalance();

        double thisYearRemain = thisYearAnnualVacationDays(vo, now);
        vo.setActualAnnualVacationDays(thisYearRemain);
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
            vo.setAllAnnualVacationDays(vo.getActualAnnualVacationDays());
        } else {
            vo.setLastYearRemainAnnualVacationDays(lastYearRemain);
            vo.setAllAnnualVacationDays(vo.getActualAnnualVacationDays()
                    + vo.getLastYearAnnualVacationBalance());
        }
        vo.setAnnualVacationDayOffDays(firstDayOff + secondDayOff);
    }

    /**
     * 计算一个员工的当年年假
     * 有效时间不满一年的年假计算规则（入职当年、毕业刚满1年的）
     * 起算日期到12月31日的天数，除以365，得到当年的年假比例。再根据所属级别的对应基数，相乘，取整（仅整数部分）得到当年年假。
     * 起算日期以日志日期或毕业满一年日期，二者取其晚者
     * 入职、毕业第二自然年其，年假比例为 100%
     * <p>
     * 年假比例为 r，
     * 年假为 V，
     * 基数为 b，
     * 起算日期为 ds，
     * 结算日期为 de
     * <p>
     * r = (de - ds)/365
     * V = round(r * b)
     * <p>
     * V = round((de - ds) / 365 * b)
     * <p>
     * 1. 未转正
     * V = 0
     * 2. 未毕业满一年
     * V = 0
     * 3. 毕业满一年且已转正 起算日期
     * 3.1 入职日期与毕业满一年日期取其晚者为 ds （起算日期）
     * 3.2 入职日期与毕业日期均不在计算当年 ds=1月1日
     * 4. 满足计算年的，结算日期
     * 4.1 发生离职，结算日期 de = 离职日期
     * 4.2 正常，结算日期 de = 12月31日
     * 5. 其他情况，有特殊设定的，年假基数按设定取。
     *
     * @param vo       员工基础信息
     * @param calcDate 某一年
     * @return 返回年假数，只能是整数
     */
    public static int thisYearAnnualVacationDays(EmployeeDayOffSummaryVo vo, Date calcDate) {
        // 1. 未转正
        if (vo.getFormalDate() == null || vo.getFormalDate().after(calcDate)) {
            return 0;
        }

        Date ds;
        Date de;
        Calendar cs = Calendar.getInstance();
        if (vo.getGraduationDate() != null) {
            // 如果毕业日期不为空，则计算毕业日期。
            cs = Calendar.getInstance();
            cs.setTime(vo.getGraduationDate());
            // 加1年
            cs.add(Calendar.YEAR, 1);
            ds = cs.getTime();
            if (vo.getEnrollmentDate().after(ds)) {
                ds = vo.getEnrollmentDate();
            }
        } else {
            // 毕业日期为空，就用入职日期。因为未转正的已经pass了。
            ds = vo.getEnrollmentDate();
        }
        cs.setTime(ds);
        Calendar ce = Calendar.getInstance();
        ce.setTime(calcDate);
        ce.set(ce.get(Calendar.YEAR), Calendar.DECEMBER, 31, 0, 0, 0);
        // 取出全年天数
        int wholeYearDays = ce.get(Calendar.DAY_OF_YEAR);
        // ce.add(Calendar.DATE, 1);
        de = ce.getTime();
        // 离职了, 离职日期在当年之内
        if (vo.getLeaveDate() != null && vo.getLeaveDate().before(de)) {
            de = vo.getLeaveDate();
            ce.setTime(de);
        }
        // 计算日期比入职日期超过一年（自然年）了，起算时间就是1月1日了
        if (ce.get(Calendar.YEAR) > cs.get(Calendar.YEAR)) {
            cs.set(ce.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
            ds = cs.getTime();
        }
        if (de.before(ds)) {
            throw new RuntimeException("计算年假出错，起算时间晚于结算时间。" + vo.getEmployeeId() + ":" + vo.getEmployeeName());
        }
        /*if (isWholeYear(cs, ce)) {
            return (int) vo.getActualAnnualVacationDays();
        }*/
        // 计算有效在职时间比例 因为天是从1开始的，所以减了还要加1
        double r = (double) (ce.get(Calendar.DAY_OF_YEAR) - cs.get(Calendar.DAY_OF_YEAR) + 1) / wholeYearDays;

        return (int) Math.round(r * vo.getActualAnnualVacationDays());
    }

    // 好像用不到了。
    /*private static boolean isWholeYear(Calendar cs, Calendar ce) {
        return cs.get(Calendar.YEAR) == ce.get(Calendar.YEAR)
                && cs.get(Calendar.MONTH) == Calendar.JANUARY
                && cs.get(Calendar.DATE) == 1
                && ce.get(Calendar.MONTH) == Calendar.DECEMBER
                && ce.get(Calendar.DATE) == 31;
    }*/
}
