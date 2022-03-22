/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/13                                
*/
package me.arnu.admin.hammers.mapper;

import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import org.apache.ibatis.annotations.MapKey;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于计算请假相关的mapper
 */
public interface DayOffMapper {
    List<EmployeeDayOffSummaryVo> selectEmpAnnualVacationInfo(DayOffTypeQuery dayOffQuery);

    /**
     * 获取请的年假
     * 接收一个数组，可以筛选一部分人的假期状况
     * 时间范围是一个左闭右开区间 [startDate, endDate )
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param empIds 接收一个数组，可以筛选一部分人的假期状况
     * @return 人员的年假请假状况
     */
    List<AskForDayOffLog> selectEmpAnnualDayOffList(Date startDate, Date endDate, List<String> empIds);

    /**
     * 获取请的其他假
     * 接收一个数组，可以筛选一部分人的假期状况
     * 时间范围是一个左闭右开区间 [startDate, endDate )
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param empIds 接收一个数组，可以筛选一部分人的假期状况
     * @return 人员的普通请假情况
     */
    List<AskForDayOffLog> selectEmpOtherDayOffList(Date startDate, Date endDate, List<String> empIds);

}
