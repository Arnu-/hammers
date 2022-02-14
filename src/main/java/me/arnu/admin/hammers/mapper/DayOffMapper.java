/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/13                                
*/
package me.arnu.admin.hammers.mapper;

import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于计算请假相关的mapper
 */
public interface DayOffMapper {
    List<EmployeeDayOffSummaryVo> selectEmpAnnualVacationInfo(DayOffTypeQuery dayOffQuery);

    List<Map<String, Object>> selectEmpDayOffList(Date startDate, Date endDate);
}
