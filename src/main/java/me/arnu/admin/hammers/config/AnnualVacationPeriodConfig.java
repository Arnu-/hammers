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

    public static Date AnnualVEnd(){
        Calendar c = Calendar.getInstance();
        // 停用上年年假的日期
        c.set(Calendar.MONTH, AnnualVacationPeriodConfig.endMonth);
        c.set(Calendar.DATE, AnnualVacationPeriodConfig.endDate);

        // 无论是计算结束还是计算开始，都要它+1天
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }


    public static Date getFirstDateOfYear() {
        // 暂时先认为以自然年作为开始。就只用取出结束时间
        Calendar c = Calendar.getInstance();

        // 如下代码的作用是将日期时间改为：1月1日0时0分0秒0毫秒
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 实际上获取的时明年的元旦
     * @return
     */
    public static Date getEndDateOfYear() {
        // 暂时先认为以自然年作为开始。就只用取出结束时间
        Calendar c = Calendar.getInstance();

        // 如下代码的作用是将日期时间改为：1月1日0时0分0秒0毫秒
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.YEAR, 1);
        return c.getTime();
    }

}
