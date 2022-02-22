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
     * @param startDate
     * @param endDate
     * @return
     */
    List<AskForDayOffLog> selectEmpAnnualDayOffList(Date startDate, Date endDate);

    /**
     * 获取请的其他假
     * @param startDate
     * @param endDate
     * @return
     */
    List<AskForDayOffLog> selectEmpOtherDayOffList(Date startDate, Date endDate);

}
