/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/13                                
*/
package me.arnu.admin.hammers.config;

import java.util.Calendar;
import java.util.Date;

/**
 * 年假配置
 */
public class AnnualVacationPeriodConfig {
    /**
     * 年假生效开始日期
     */
    public static int startMonth = Calendar.JANUARY;

    public static int startDate = 1;

    /**
     * 年假生效结束日期
     */
    public static int endMonth = Calendar.MARCH;
    public static int endDate = 31;

    /**
     * 上年的年假最后有效期
     *
     * @return 上年的年假最后有效期
     */
    public static Date AnnualVEnd(int year) {
        Calendar c = Calendar.getInstance();
        // 停用上年年假的日期
        c.set(year, AnnualVacationPeriodConfig.endMonth, AnnualVacationPeriodConfig.endDate, 0, 0, 0);

        // 无论是计算结束还是计算开始，都要它+1天
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }


    /**
     * 取出年的开始日期
     *
     * @return 指定年的元旦
     */
    public static Date getFirstDateOfYear(int year) {
        // 暂时先认为以自然年作为开始。就只用取出结束时间
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        return c.getTime();
    }

    /**
     * 实际上获取的时明年的元旦
     *
     * @return 指定年的第二年的元旦
     */
    public static Date getEndDateOfYear(int year) {
        // 暂时先认为以自然年作为开始。就只用取出结束时间
        Calendar c = Calendar.getInstance();
        c.set(year + 1, Calendar.JANUARY, 1, 0, 0, 0);
        return c.getTime();
    }

}
