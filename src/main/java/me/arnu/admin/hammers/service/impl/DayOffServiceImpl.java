/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.admin.hammers.config.AnnualVacationPeriodConfig;
import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.mapper.DayOffMapper;
import me.arnu.admin.hammers.mapper.DayOffTypeMapper;
import me.arnu.admin.hammers.mapper.EmployeeMapper;
import me.arnu.admin.hammers.query.AskForDayOffLogQuery;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.service.IDayOffService;
import me.arnu.admin.hammers.service.IDayOffTypeService;
import me.arnu.admin.hammers.vo.DayOffTypeListVo;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 请假类型 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class DayOffServiceImpl implements IDayOffService {

    @Autowired
    private DayOffMapper dayOffMapper;


    /**
     * 这个地方获取请假的一系列相关信息，输出每个员工的
     */
    @Override
    public JsonResult getList(DayOffTypeQuery query) {
        DayOffTypeQuery dayOffQuery = (DayOffTypeQuery) query;

        // 1. 取出员工信息
        // 2. 取出员工年假设定
        // 3. 取出上年剩余年假
        List<EmployeeDayOffSummaryVo> empSummaryList = dayOffMapper.selectEmpAnnualVacationInfo(dayOffQuery);

        // 4. 取出今年已请年假，两部分，1、重叠期，2、独立期
        Date now = new Date();
        // 暂时先认为以自然年作为开始。就只用取出结束时间
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date startDate = c.getTime();

        c.set(Calendar.MONTH, AnnualVacationPeriodConfig.endMonth);
        c.set(Calendar.DATE, AnnualVacationPeriodConfig.endDate);

        // 为了方便计算，这里要加一天
        c.add(Calendar.DATE, 1);
        Date endDate = c.getTime();
        List<Map<String, Object>> empDayOffFirstHalfList = dayOffMapper.selectEmpDayOffList(startDate, endDate);
        List<Map<String, Object>> empDayOffSecondHalfList = null;

        startDate = endDate;

        if (month > AnnualVacationPeriodConfig.endMonth ||
                (month == AnnualVacationPeriodConfig.endMonth
                        && date > AnnualVacationPeriodConfig.endDate)) {
            // 取出分割后日期
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            c.set(Calendar.DATE, 31);
            // 为了方便计算，这里要加一天
            c.add(Calendar.DATE, 1);

            endDate = c.getTime();
            empDayOffSecondHalfList = dayOffMapper.selectEmpDayOffList(startDate, endDate);
        }
//        Map<Integer, EmployeeDayOffSummaryVo> summaryVoMap = new HashMap<>();
//        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
//            summaryVoMap.put(vo.getEmployeeId(), vo);
//        }

        Map<String, Double> firstDayOffMap = new HashMap<>();
        Map<String, Double> secondDayOffMap = null;

        for (Map<String, Object> map : empDayOffFirstHalfList) {
            firstDayOffMap.put((String) map.get("employee_id"), (double) map.get("dayoff"));
        }

        if (empDayOffSecondHalfList != null) {
            secondDayOffMap = new HashMap<>();
            for (Map<String, Object> map : empDayOffSecondHalfList) {
                secondDayOffMap.put((String) map.get("employee_id"), (double) map.get("dayoff"));
            }
        }

        // 5. 计算出实际剩余年假
        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
            // 5.1 取出对应人的请假状态
            // 5.2 根据请假前、后半年设定来计算
            // 5.3 去年残留还有就先扣去年，去年没有就开始扣今年。
            String empId = vo.getEmployeeId();
            double firstDayOff = firstDayOffMap.getOrDefault(empId, 0d);
            double secondDayOff = secondDayOffMap == null ? 0d :
                    secondDayOffMap.getOrDefault(empId, 0d);
            double lastYearRemain = vo.getLastYearAnnualVacationBalance();
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
            if (now.getTime() >= startDate.getTime()) {
                vo.setLastYearRemainAnnualVacationDays(0d);
                vo.setAllAnnualVacationDays(vo.getActuallyAnnualVacationDays());
            } else {
                vo.setLastYearRemainAnnualVacationDays(lastYearRemain);
                vo.setAllAnnualVacationDays(vo.getActuallyAnnualVacationDays()
                        + vo.getLastYearAnnualVacationBalance());
            }
            vo.setAnnualVacationDayOffDays(firstDayOff + secondDayOff);
        }
        return JsonResult.success("ok", empSummaryList);
    }

    @Override
    public JsonResult edit(DayOffType entity) {
        return null;
    }

    @Override
    public Map<String, Object> info(Integer id) {
        return null;
    }
}