/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.arnu.admin.hammers.config.AnnualVacationPeriodConfig;
import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.mapper.DayOffMapper;
import me.arnu.admin.hammers.mapper.DayOffTypeMapper;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.service.IDayOffService;
import me.arnu.admin.hammers.utils.DayOffUtil;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import me.arnu.admin.hammers.vo.LayuiTableColumnVo;
import me.arnu.common.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private DayOffTypeMapper dayOffTypeMapper;

    private static final List<String> fields = Arrays.asList(
            "id", "employeeId", "employeeName",
            "dept", "level", "workYear",
            "actuallyAnnualVacationDays",
            "lastYearAnnualVacationBalance",
            "allAnnualVacationDays",
            "lastYearRemainAnnualVacationDays",
            "thisYearRemainAnnualVacationDays",
            "firstAnnualVacationDayOffDays",
            "secondAnnualVacationDayOffDays",
            "annualVacationDayOffDays");

    /**
     * 这个地方获取请假的一系列相关信息，输出每个员工的
     */
    @Override
    public JsonResult getList(DayOffTypeQuery query) {

        // 1. 取出员工信息
        // 2. 取出员工年假设定
        // 3. 取出上年剩余年假
        List<EmployeeDayOffSummaryVo> empSummaryList = dayOffMapper.selectEmpAnnualVacationInfo(query);

        // 4. 取出今年已请年假，两部分，1、重叠期，2、独立期
        // 暂时先认为以自然年作为开始。就只用取出结束时间

        Map<String, Double> firstDayOffMap = getFirstHalfYearDayOff();
        Map<String, Double> secondDayOffMap = null;

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        // 如果日期已经超过了上年年假有效期，那就要计算后续部分
        if (c.get(Calendar.MONTH) > AnnualVacationPeriodConfig.endMonth ||
                (c.get(Calendar.MONTH) == AnnualVacationPeriodConfig.endMonth
                        && c.get(Calendar.DATE) > AnnualVacationPeriodConfig.endDate)) {

            secondDayOffMap = getSecondHalfYearDayOff();
        }

        List<DayOffType> list = dayOffTypeMapper.selectList(new QueryWrapper<DayOffType>() {{
            ne("id", 1);
            eq("mark", 1);
        }});
        Map<Integer, Map<String, Double>> typeDayOffMap = new HashMap<>();
        for (DayOffType type : list) {
            typeDayOffMap.put(type.getId(), new HashMap<>());
        }
        Date yearFirstDay = AnnualVacationPeriodConfig.getFirstDateOfYear();
        Date yearEndDay = AnnualVacationPeriodConfig.getEndDateOfYear();
        List<AskForDayOffLog> otherTypeDayOffList = dayOffMapper.selectEmpOtherDayOffList(yearFirstDay, yearEndDay, null);

        for (AskForDayOffLog map : otherTypeDayOffList) {
            String empId = map.getEmployeeId();
            Integer typeId = map.getDayOffTypeId();
            Float dayoffs = map.getDays();

            if (!typeDayOffMap.containsKey(typeId)) {
                typeDayOffMap.put(typeId, new HashMap<>());
            }
            Map<String, Double> dmap = typeDayOffMap.get(typeId);
            if (!dmap.containsKey(empId)) {
                dmap.put(empId, (double) dayoffs);
            }
        }
//        Map<Integer, EmployeeDayOffSummaryVo> summaryVoMap = new HashMap<>();
//        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
//            summaryVoMap.put(vo.getEmployeeId(), vo);
//        }


        List<JSONObject> jsonList = new ArrayList<>();
        Date endOfAnnualVExpire = AnnualVacationPeriodConfig.AnnualVEnd();

        // 5. 计算出实际剩余年假
        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
            // 5.1 取出对应人的请假状态
            // 5.2 根据请假前、后半年设定来计算
            // 5.3 去年残留还有就先扣去年，去年没有就开始扣今年。
            String empId = vo.getEmployeeId();
            double firstDayOff = firstDayOffMap.getOrDefault(empId, 0d);
            double secondDayOff = secondDayOffMap == null ? 0d :
                    secondDayOffMap.getOrDefault(empId, 0d);
            DayOffUtil.calcAnnualVacation(vo, firstDayOff, secondDayOff, now, endOfAnnualVExpire);
            JSONObject jobj = (JSONObject) JSONObject.toJSON(vo);
            // 放不同类型的请假数据
            for (Map.Entry<Integer, Map<String, Double>> dayOffMapE : typeDayOffMap.entrySet()) {
                int k = dayOffMapE.getKey();
                String type = "type_" + k;
                jobj.put(type, dayOffMapE.getValue().getOrDefault(vo.getEmployeeId(), 0d));
            }
            jsonList.add(jobj);
        }
        return JsonResult.success("ok", jsonList);
    }

    public Map<String, Double> getSecondHalfYearDayOff() {
        // 取出分割后日期
        Date startDate = AnnualVacationPeriodConfig.AnnualVEnd();
        Date endDate = AnnualVacationPeriodConfig.getEndDateOfYear();

        List<AskForDayOffLog> empDayOffSecondHalfList = dayOffMapper.selectEmpAnnualDayOffList(startDate, endDate, null);
        Map<String, Double> secondDayOffMap = new HashMap<>();
        for (AskForDayOffLog map : empDayOffSecondHalfList) {
            secondDayOffMap.put(map.getEmployeeId(), (double) map.getDays());
        }
        return secondDayOffMap;
    }

    /**
     * 取前半部分年假请假
     *
     * @return
     */
    public Map<String, Double> getFirstHalfYearDayOff() {
        Date startDate = AnnualVacationPeriodConfig.getFirstDateOfYear();
        Date endDate = AnnualVacationPeriodConfig.AnnualVEnd();
        List<AskForDayOffLog> firstList = dayOffMapper.selectEmpAnnualDayOffList(startDate, endDate, null);

        Map<String, Double> firstDayOffMap = new HashMap<>();
        for (AskForDayOffLog map : firstList) {
            firstDayOffMap.put(map.getEmployeeId(), (double) map.getDays());
        }
        return firstDayOffMap;
    }

    /**
     * 获取需要的字段头
     *
     * @return
     */
    public JsonResult getFields() {
        List<LayuiTableColumnVo> fields = new ArrayList();
        fields.add(new LayuiTableColumnVo("employeeId", 80, "员工号", "center", "left"));
        fields.add(new LayuiTableColumnVo("employeeName", 100, "姓名", "center", "left"));
        fields.add(new LayuiTableColumnVo("dept", 120, "部门", "center", ""));
        fields.add(new LayuiTableColumnVo("level", 120, "级别", "center", ""));
        fields.add(new LayuiTableColumnVo("workYear", 100, "工龄", "center", ""));
        fields.add(new LayuiTableColumnVo("lastYearAnnualVacationBalance", 100, "去年结余年假", "center", ""));
        fields.add(new LayuiTableColumnVo("actuallyAnnualVacationDays", 100, "今年年假额度", "center", ""));
        fields.add(new LayuiTableColumnVo("lastYearRemainAnnualVacationDays", 100, "去年可用年假剩余", "center", ""));
        fields.add(new LayuiTableColumnVo("thisYearRemainAnnualVacationDays", 100, "今年可用年假剩余", "center", ""));
        fields.add(new LayuiTableColumnVo("firstAnnualVacationDayOffDays", 100, "3月31日前请的年假", "center", ""));
        fields.add(new LayuiTableColumnVo("secondAnnualVacationDayOffDays", 100, "3月31日后请的年假", "center", ""));
        fields.add(new LayuiTableColumnVo("annualVacationDayOffDays", 100, "总计请年假", "center", ""));

        List<DayOffType> list = dayOffTypeMapper.selectList(new QueryWrapper<DayOffType>() {{
            ne("id", 1);
            eq("mark", 1);
        }});

        for (DayOffType type : list) {
            fields.add(new LayuiTableColumnVo("type_" + type.getId(), 100, type.getName(), "center", ""));
        }
        return JsonResult.success("ok", fields);
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