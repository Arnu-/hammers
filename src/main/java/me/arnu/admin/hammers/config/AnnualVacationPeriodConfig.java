/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/13                                
*/
package me.arnu.admin.hammers.config;

import java.util.Calendar;

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

}
