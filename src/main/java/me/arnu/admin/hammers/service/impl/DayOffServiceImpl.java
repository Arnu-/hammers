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
import org.apache.commons.lang3.time.FastDateFormat;
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
            "actualAnnualVacationDays",
            "lastYearAnnualVacationBalance",
            "allAnnualVacationDays",
            "lastYearRemainAnnualVacationDays",
            "thisYearRemainAnnualVacationDays",
            "firstAnnualVacationDayOffDays",
            "secondAnnualVacationDayOffDays",
            "annualVacationDayOffDays");

    /**
     * 查询某个员工的年假计算明细
     *
     * @param query
     * @return
     */
    @Override
    public JsonResult annualDetail(DayOffTypeQuery query) {
        if (null == query.getName() || "".equals(query.getName())) {
            return JsonResult.success();
        }
        List<EmployeeDayOffSummaryVo> empSummaryList = dayOffMapper.selectEmpAnnualVacationInfo(query);
        if (empSummaryList.size() == 0) {
            return JsonResult.success();
        }
        int year = query.getYear();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        if (year == 0) {
            // 不输入年，就是计算今年。
            year = c.get(Calendar.YEAR);
        } else {
            c.set(Calendar.YEAR, year);
        }
        List<Map<String, String>> result = new ArrayList<>();
        EmployeeDayOffSummaryVo vo = empSummaryList.get(0);
        result.add(mapResult("姓名", vo.getEmployeeName(), "员工姓名"));
        result.add(mapResult("毕业日期", vo.getGraduationDate(), "员工毕业日期<br>毕业不满一年无年假<br>毕业满一年但未跨过第一个自然年，年假按比例折算<br>无值视为已毕业满一年"));
        result.add(mapResult("入职日期", vo.getEnrollmentDate(), "员工入职日期<br>入职不满一年，年假按比例折算<br>如果毕业满一年时间比入职时间晚，则按毕业满一年算起"));
        result.add(mapResult("转正日期", vo.getFormalDate(), "员工转正日期<br>未转正员工无年假<br>无值视为未转正"));
        result.add(mapResult("离职日期", vo.getLeaveDate(), "员工离职日期<br>离职员工年假按离职日期前的在职时长折算当年年假<br>无值视为为离职"));
        result.add(mapResult("年假基数", vo.getActualAnnualVacationDays(), "员工年假基数<br>基数由级别决定或特殊指定"));

        try {
            int r = DayOffUtil.thisYearAnnualVacationDays(vo, c.getTime());
            result.add(mapResult("计算结果", r, "年假计算结果"));
        } catch (IllegalArgumentException e) {
            result.add(mapResult("计算结果", e.getMessage(), "年假计算结果"));
        }

        return JsonResult.success("成功", result);
    }

    private Map<String, String> mapResult(String field, Object value, String describe) {
        Map<String, String> v = new HashMap<>();
        v.put("field", field);
        if (value instanceof String) {
            v.put("value", (String) value);
        } else if (value instanceof Date) {
            FastDateFormat f = FastDateFormat.getInstance("yyyy-MM-dd");
            v.put("value", f.format((Date) value));
        } else if (null == value) {
            v.put("value", "");
        } else {
            v.put("value", String.valueOf(value));
        }
        v.put("describe", describe);
        return v;
    }

    /**
     * 这个地方获取请假的一系列相关信息，输出每个员工的
     */
    @Override
    public JsonResult getList(DayOffTypeQuery query) {
        int year = query.getYear();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        if (year == 0) {
            // 不输入年，就是计算今年。
            year = c.get(Calendar.YEAR);
        } else {
            c.set(Calendar.YEAR, year);
        }

        // 1. 取出员工信息
        // 2. 取出员工年假设定
        // 3. 取出上年剩余年假
        List<EmployeeDayOffSummaryVo> empSummaryList = dayOffMapper.selectEmpAnnualVacationInfo(query);

        // 4. 取出今年已请年假，两部分，1、重叠期，2、独立期
        // 暂时先认为以自然年作为开始。就只用取出结束时间
        Map<String, Double> firstDayOffMap = getFirstHalfYearDayOff(year);
        Map<String, Double> secondDayOffMap = getSecondHalfYearDayOff(year);

        // 取出除了年假之外的请假统计
        Map<Integer, Map<String, Double>> typeDayOffMap = getOtherDayOffStat(year);

//        Map<Integer, EmployeeDayOffSummaryVo> summaryVoMap = new HashMap<>();
//        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
//            summaryVoMap.put(vo.getEmployeeId(), vo);
//        }

        // 保存计算结果
        List<JSONObject> jsonList = new ArrayList<>();
        // 取出上年的年假最后有效期
        Date endOfAnnualVExpire = AnnualVacationPeriodConfig.AnnualVEnd(year);

        // 5. 计算出实际剩余年假
        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
            // 5.1 取出对应人的请假状态
            // 5.2 根据请假前、后半年设定来计算
            // 5.3 去年残留还有就先扣去年，去年没有就开始扣今年。
            String empId = vo.getEmployeeId();
            double firstDayOff = firstDayOffMap.getOrDefault(empId, 0d);
            double secondDayOff = secondDayOffMap == null ? 0d :
                    secondDayOffMap.getOrDefault(empId, 0d);
            try {
                if (DayOffUtil.calcAnnualVacationV2(vo, firstDayOff, secondDayOff, c.getTime(), endOfAnnualVExpire)) {
                    JSONObject jobj = (JSONObject) JSONObject.toJSON(vo);
                    // 放不同类型的请假数据
                    for (Map.Entry<Integer, Map<String, Double>> dayOffMapE : typeDayOffMap.entrySet()) {
                        int k = dayOffMapE.getKey();
                        String type = "type_" + k;
                        jobj.put(type, dayOffMapE.getValue().getOrDefault(vo.getEmployeeId(), 0d));
                    }
                    jsonList.add(jobj);
                }
            } catch (IllegalArgumentException e) {
                // 这个异常就吃掉吧
            }
        }
        return JsonResult.success("ok", jsonList, jsonList.size());
    }

    /**
     * 取出除了年假之外的其他类型的请假的统计
     *
     * @param year 指定的哪年的数据
     * @return 返回所有员工的请假统计
     */
    private Map<Integer, Map<String, Double>> getOtherDayOffStat(int year) {
        // 取出请假类型id不等于1，有效=1，也就是请假类型不是年假的其他类型。
        // 用于其他类型假期的计算。
        List<DayOffType> list = dayOffTypeMapper.selectList(new QueryWrapper<DayOffType>() {{
            ne("id", 1);
            eq("mark", 1);
        }});
        // 不同类型的请假的所有员工请假统计
        Map<Integer, Map<String, Double>> typeDayOffMap = new HashMap<>();
        for (DayOffType type : list) {
            typeDayOffMap.put(type.getId(), new HashMap<>());
        }

        // 取出年的开始日期
        Date yearFirstDay = AnnualVacationPeriodConfig.getFirstDateOfYear(year);
        // 取出年的结束日期
        Date yearEndDay = AnnualVacationPeriodConfig.getEndDateOfYear(year);
        // 取出其他类型的请假详情
        List<AskForDayOffLog> otherTypeDayOffList = dayOffMapper.selectEmpOtherDayOffList(yearFirstDay, yearEndDay, null);

        // 得出员工号与请假的对应关系
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
        return typeDayOffMap;
    }

    public Map<String, Double> getSecondHalfYearDayOff(int year) {
        // 取出分割后日期
        Date startDate = AnnualVacationPeriodConfig.AnnualVEnd(year);
        Date endDate = AnnualVacationPeriodConfig.getEndDateOfYear(year);

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
    public Map<String, Double> getFirstHalfYearDayOff(int year) {
        Date startDate = AnnualVacationPeriodConfig.getFirstDateOfYear(year);
        Date endDate = AnnualVacationPeriodConfig.AnnualVEnd(year);
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
        // fields.add(new LayuiTableColumnVo("workYear", 100, "工龄", "center", ""));
        fields.add(new LayuiTableColumnVo("lastYearAnnualVacationBalance", 100, "去年结余年假", "center", ""));
        fields.add(new LayuiTableColumnVo("actualAnnualVacationDays", 100, "今年年假额度", "center", ""));
        fields.add(new LayuiTableColumnVo("lastYearRemainAnnualVacationDays", 120, "去年可用年假\n(3/31前有效)", "center", ""));
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