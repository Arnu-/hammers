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
import me.arnu.admin.hammers.constant.AskForDayOffLogConstant;
import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.mapper.AskForDayOffLogMapper;
import me.arnu.admin.hammers.mapper.DayOffMapper;
import me.arnu.admin.hammers.mapper.DayOffTypeMapper;
import me.arnu.admin.hammers.mapper.EmployeeMapper;
import me.arnu.admin.hammers.query.AskForDayOffLogQuery;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.service.IAskForDayOffLogService;
import me.arnu.admin.hammers.utils.DayOffUtil;
import me.arnu.admin.hammers.vo.AskForDayOffLogListVo;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.utils.JsonResult;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * <p>
 * 请假记录 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class AskForDayOffLogServiceImpl extends BaseServiceImpl<AskForDayOffLogMapper, AskForDayOffLog> implements IAskForDayOffLogService {

    @Autowired
    private AskForDayOffLogMapper askForDayOffLogMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DayOffTypeMapper dayOffTypeMapper;

    @Autowired
    private DayOffMapper dayOffMapper;
/*

    private Map<String, String> loadEmpRealName(List<String> empIds) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");
        queryWrapper.in("employee_id", empIds);
        List<Employee> eList = employeeMapper.selectList(queryWrapper);
        Map<String, String> eMap = new HashMap<>();
        for (Employee employee : eList) {
            eMap.put(employee.getEmployeeId(), employee.getRealname());
        }
        return eMap;
    }

    private Map<Integer, String> loadDayOffType() {
        QueryWrapper<DayOffType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");
        List<DayOffType> eList = dayOffTypeMapper.selectList(queryWrapper);
        Map<Integer, String> eMap = new HashMap<>();
        for (DayOffType type : eList) {
            eMap.put(type.getId(), type.getName());
        }
        return eMap;
    }
*/

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return 请假列表
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AskForDayOffLogQuery askForDayOffLogQuery = (AskForDayOffLogQuery) query;

        // 查询数据
        IPage<AskForDayOffLog> page = new Page<>(askForDayOffLogQuery.getPage(), askForDayOffLogQuery.getLimit());
        IPage<AskForDayOffLogListVo> data = askForDayOffLogMapper.selectVoPage(page, askForDayOffLogQuery);
        List<AskForDayOffLogListVo> askForDayOffLogListVoList = data.getRecords();
        if (!askForDayOffLogListVoList.isEmpty()) {
            askForDayOffLogListVoList.forEach(item -> {
                // 创建人名称
                if (item.getCreateUser() != null && item.getCreateUser() > 0) {
                    item.setCreateUserName(UserUtils.getName((item.getCreateUser())));
                }
                // 更新人名称
                if (item.getUpdateUser() != null && item.getUpdateUser() > 0) {
                    item.setUpdateUserName(UserUtils.getName((item.getUpdateUser())));
                }
            });
        }
        return JsonResult.success("操作成功", askForDayOffLogListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return 请假记录
     */
    @Override
    public Object getInfo(Serializable id) {
        return super.getInfo(id);
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return 编辑结果
     */
    @Override
    public JsonResult edit(AskForDayOffLog entity) {
        // 请假逻辑：
        // 1、自动根据选择的时间区间计算天数
        // 2、只能选择日期+上、下午
        if (entity.getStartDate() == entity.getEndDate()) {
            if (entity.getStartHalfDay().equals(entity.getEndHalfDay())) {
                entity.setDays(0.5f);
            } else if (entity.getStartHalfDay().equals(AskForDayOffLogConstant.PM)) {
                return JsonResult.error("请假日期区间格式不对！同一天不能 下午开始，上午结束。");
            } else {
                entity.setDays(1f);
            }
        } else if (entity.getStartDate().after(entity.getEndDate())) {
            return JsonResult.error("请假日期区间格式不对！开始日期晚于结束日期");
        } else {
            QueryWrapper<AskForDayOffLog> q = new QueryWrapper<AskForDayOffLog>();
            q.eq("mark", 1);
            q.eq("employee_id", entity.getEmployeeId());
            q.eq("start_date", entity.getStartDate());
            q.eq("start_half_day", entity.getStartHalfDay());
            q.eq("end_date", entity.getStartDate());
            q.eq("end_half_day", entity.getEndHalfDay());
            List<AskForDayOffLog> dayoffs = askForDayOffLogMapper.selectList(q);
            if (dayoffs != null && dayoffs.size() > 0) {
                // 新增，有记录就是重复
                // 修改，有两条（含）以上就是重复
                if (entity.getId() != null && entity.getId() > 0) {
                    if (dayoffs.size() > 1) {
                        // 修改请假时排除自身
                        return JsonResult.error("请假时间重复");
                    }
                } else {
                    return JsonResult.error("请假时间重复");
                }

            }

            // 2022-02-15
            Calendar start = Calendar.getInstance();
            start.setTime(entity.getStartDate());
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);

            // 2022-02-16
            // 2022-02-17
            Calendar end = Calendar.getInstance();
            end.setTime(entity.getEndDate());
            end.set(Calendar.HOUR_OF_DAY, 0);
            end.set(Calendar.MINUTE, 0);
            end.set(Calendar.SECOND, 0);
            end.set(Calendar.MILLISECOND, 0);

            float days = (float) (end.getTimeInMillis() - start.getTimeInMillis()) / 86400000;
            days = days - 1;
            if (entity.getStartHalfDay().equals(AskForDayOffLogConstant.AM)) {
                days = days + 1;
            } else {
                days = days + 0.5f;
            }

            if (entity.getEndHalfDay().equals(AskForDayOffLogConstant.PM)) {
                days = days + 1;
            } else {
                days = days + 0.5f;
            }
            entity.setDays(days);
            if (entity.getDayOffTypeId().equals(DayOffType.ANNUAL_VACATION)) {
                double allBalance = 0d;
                try {
                    allBalance = getAllBalance(entity.getEmployeeId());
                } catch (IllegalArgumentException e) {
                    return JsonResult.error(e.getMessage());
                }
                boolean autoAnnual = entity.getAutoAnnual() == 1;
                if (allBalance <= 0) {
                    // 没有年假了。
                    // 直接转事假
                    if (autoAnnual) {
                        entity.setDayOffTypeId(DayOffType.PERSONAL_LEAVE);
                        entity.setNote(entity.getNote() + "\n年假已不足，自动将请假转为事假。");
                    } else {
                        return JsonResult.error("年假不足。");
                    }
                } else {
                    if (entity.getDays() > allBalance) {
                        if (!autoAnnual) {
                            return JsonResult.error("年假不足。");
                        }
                        // 请年假超出了可用的年假
                        // 通过年假天数和请假开始时间对应出请假结束时间
                        AskForDayOffLog newlog = calcEndDate(entity, allBalance);
                        if (newlog == null) {
                            return JsonResult.error("年假不足无法请假");
                        }
                        AskForDayOffLog aLog = new AskForDayOffLog();
                        BeanUtils.copyProperties(entity, aLog);
                        aLog.setEndHalfDay(newlog.getEndHalfDay())
                                .setEndDate(newlog.getEndDate())
                                .setDays((float) allBalance)
                                .setNote(aLog.getNote() + "\n年假已不足，只能使用：" + allBalance + "天。");

                        AskForDayOffLog bLog = new AskForDayOffLog();
                        BeanUtils.copyProperties(entity, bLog);
                        bLog.setStartDate(newlog.getStartDate())
                                .setStartHalfDay(newlog.getStartHalfDay())
                                .setDays((float) (entity.getDays() - allBalance))
                                .setDayOffTypeId(DayOffType.PERSONAL_LEAVE)
                                .setNote(bLog.getNote() + "\n年假已不足，自动将请假转为事假。");

                        JsonResult ar = super.edit(aLog);
                        if (!ar.getCode().equals(JsonResult.SUCCESS_CODE)) {
                            return ar;
                        }
                        return super.edit(bLog);
                    }
                }
            }
        }
        return super.edit(entity);
    }

    public String getAllBalance(DayOffTypeQuery query) {
        try {
            return String.valueOf(getAllBalance(query.getEmpId()));
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private double getAllBalance(String employeeId) {
        // 需要判断请假类型，如果是年假，那么就需要判断年假是否够用，年假不够用的，就会分成两个部分：
        // 1、年假请一部分
        // 2、不够年假的用事假
        // todo：思考：假期余额，类似金额，要考虑是否够用，还有有效期问题，可以形成一套处理思路
        // 2.1 算出上年结余，进行扣除。
        // 2.2 算出今年剩余，进行扣除。
        // 2.3 还不够，就转事假。
        // todo：虽然现在还不考虑性能问题。但是这里的确会可能有性能问题出现。涉及到这种操作如何处理性能与准确。
        // 获取年假额度
        DayOffTypeQuery query = new DayOffTypeQuery();
        query.setEmpId(employeeId);

        List<EmployeeDayOffSummaryVo> empSummaryList = dayOffMapper.selectEmpAnnualVacationInfo(query);
        EmployeeDayOffSummaryVo theVo = null;
        for (EmployeeDayOffSummaryVo vo : empSummaryList) {
            if (vo.getEmployeeId().equals(employeeId)) {
                theVo = vo;
                break;
            }
        }
        if (theVo == null) {
            throw new IllegalArgumentException("未找到员工的年假信息：" + employeeId);
        }

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        // 获取上半年请了多少年假
        AskForDayOffLog firstH = getFirstHalfYearDayOff(employeeId, c.get(Calendar.YEAR));
        // 获取下半年请了多少年假
        AskForDayOffLog secondH = null;
        // 如果日期已经超过了上年年假有效期，那就要计算后续部分
        if (c.get(Calendar.MONTH) > AnnualVacationPeriodConfig.endMonth ||
                (c.get(Calendar.MONTH) == AnnualVacationPeriodConfig.endMonth
                        && c.get(Calendar.DATE) > AnnualVacationPeriodConfig.endDate)) {

            secondH = getSecondHalfYearDayOff(employeeId, c.get(Calendar.YEAR));
        }
        DayOffUtil.calcAnnualVacationV2(theVo, firstH == null ? 0 : firstH.getDays()
                , secondH == null ? 0 : secondH.getDays()
                , now, AnnualVacationPeriodConfig.AnnualVEnd(c.get(Calendar.YEAR)));
        // 到这里，算出了一个员工的已有年假信息、剩余年假信息等。
        // 下面开始计算请年假是否够用。由于上一步已经把上年剩余过期状况判断了，这里直接计算就可以。
        double allBalance = theVo.getLastYearRemainAnnualVacationDays() + theVo.getThisYearRemainAnnualVacationDays();
        return allBalance;
    }

    /**
     * 简单点，用剩余天乘以24，然后从开始时间加，
     * 如果是    3月7日，上午，   3月7日 下午
     * |        03-07 00:00    03-07 12:00
     * 请假 1    3月7日，下午    3月8日 上午
     * |        03-08 00:00    03-08 12:00
     * 请假 1.5  3月8日，上午    3月8日 下午
     * |        03-08 12:00    03-09 00:00
     * 请假 2   3月8日，下午     3月9日 上午
     * |        03-09 00:00    03-09 12:00
     * <p>
     * 所以要减掉12小时，就对了
     * <p>
     * 这里做了一个取巧包装，返回对象的结束时间是年假用完后能请假的结束时间，
     * <br>返回对象的开始时间，是用完年假后，下一个请假的开始时间。
     *
     * @param log  要请假的信息
     * @param days 还剩下的年假天数
     * @return 这里做了一个取巧包装，返回对象的结束时间是年假用完后能请假的结束时间，
     * <br>返回对象的开始时间，是用完年假后，下一个请假的开始时间。
     */
    private AskForDayOffLog calcEndDate(AskForDayOffLog log, double days) {
        if (days <= 0) {
            // 没年假就返回
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(log.getStartDate());
        if (log.getStartHalfDay().equals(AskForDayOffLog.AFTERNOON)) {
            c.add(Calendar.HOUR, 12);
        }
        double addHour = days * 24;
        c.add(Calendar.HOUR, (int) addHour - 12);
        int h = c.get(Calendar.HOUR);

        // 再对日期取整点
        c.set(Calendar.HOUR, 0);
        Date endDate = c.getTime();

        String endHalfD;
        String nextStartHalfD;
        if (h == 0) {
            endHalfD = AskForDayOffLog.MORNING;
            nextStartHalfD = AskForDayOffLog.AFTERNOON;
        } else {
            endHalfD = AskForDayOffLog.AFTERNOON;
            nextStartHalfD = AskForDayOffLog.MORNING;
            c.add(Calendar.DATE, 1);
        }
        Date nextStartDate = c.getTime();
        AskForDayOffLog newlog = new AskForDayOffLog();
        newlog.setStartDate(nextStartDate)
                .setStartHalfDay(nextStartHalfD)
                .setEndDate(endDate)
                .setEndHalfDay(endHalfD);
        return newlog;
    }

    public AskForDayOffLog getSecondHalfYearDayOff(String empId, int year) {
        // 取出分割后日期
        Date startDate = AnnualVacationPeriodConfig.AnnualVEnd(year);
        Date endDate = AnnualVacationPeriodConfig.getEndDateOfYear(year);
        List<AskForDayOffLog> l = dayOffMapper.selectEmpAnnualDayOffList(startDate, endDate, Collections.singletonList(empId));
        for (AskForDayOffLog log : l) {
            if (log.getEmployeeId().equals(empId)) {
                return log;
            }
        }
        return null;
    }

    /**
     * 取前半部分年假请假
     *
     * @return AskForDayOffLog
     */
    public AskForDayOffLog getFirstHalfYearDayOff(String empId, int year) {
        Date startDate = AnnualVacationPeriodConfig.getFirstDateOfYear(year);
        Date endDate = AnnualVacationPeriodConfig.AnnualVEnd(year);
        List<AskForDayOffLog> l = dayOffMapper.selectEmpAnnualDayOffList(startDate, endDate, Collections.singletonList(empId));
        for (AskForDayOffLog log : l) {
            if (log.getEmployeeId().equals(empId)) {
                return log;
            }
        }
        return null;
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return 返回删除结果
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        AskForDayOffLog entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    @Override
    public JsonResult addBatch(List<AskForDayOffLogListVo> list, Boolean autoCreateType, Boolean autoAnnualToOther) {
        // 1、请假员工、日期、不可重复
        // 2、员工号不存在不可添加
        // 3、请假类型不存在创建，可选
        // 4、假定数据不会太多。逐条搞吧

        // 获取请假类型。
        QueryWrapper<DayOffType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");
        List<DayOffType> eList = dayOffTypeMapper.selectList(queryWrapper);
        Map<String, Integer> eMap = new HashMap<>();
        for (DayOffType type : eList) {
            eMap.put(type.getName(), type.getId());
        }

        int successCount = 0;
        List<AskForDayOffLogListVo> badInfoList = new ArrayList<>();
        for (AskForDayOffLogListVo vo : list) {
            boolean skip = false;
            // 判断员工是否存在
            Employee bemp = employeeMapper.selectOne(new QueryWrapper<Employee>() {{
                eq("mark", 1);
                eq("employee_id", vo.getEmployeeId());
            }});
            if (bemp == null) {
                vo.setNote(vo.getNote() + "\n" + "员工号不存在：" + vo.getEmployeeId());
                skip = true;
            }
            // 验证半天的文字字符是不是“上午，下午”
            if (!vo.getStartHalfDay().equals(AskForDayOffLog.MORNING)
                    && !vo.getStartHalfDay().equals(AskForDayOffLog.AFTERNOON)) {
                vo.setNote(vo.getNote() + "\n" + "开始半天必须是（上午，下午）" + vo.getStartHalfDay());
                skip = true;
            } else if (!vo.getEndHalfDay().equals(AskForDayOffLog.MORNING)
                    && !vo.getEndHalfDay().equals(AskForDayOffLog.AFTERNOON)) {
                vo.setNote(vo.getNote() + "\n" + "结束半天必须是（上午，下午）" + vo.getEndHalfDay());
                skip = true;
            }
            // 验证请假类型是否存在，并判断是否自动创建。
            Integer typeId = eMap.getOrDefault(vo.getDayOffType(), null);
            if (typeId == null) {
                if (autoCreateType) {
                    DayOffType d = new DayOffType();
                    d.setName(vo.getDayOffType())
                            .setNote("导入时自动创建的。");
                    dayOffTypeMapper.insert(d);
                    typeId = d.getId();
                    eMap.put(vo.getDayOffType(), typeId);
                } else {
                    vo.setNote(vo.getNote() + "\n" + "请假类型" + vo.getDayOffType() + "不存在。");
                    skip = true;
                }
            }
            if (skip) {
                badInfoList.add(vo);
                continue;
            }
            AskForDayOffLog log = new AskForDayOffLog()
                    .setEmployeeId(vo.getEmployeeId())
                    .setDayOffTypeId(typeId)
                    .setStartDate(vo.getStartDate())
                    .setStartHalfDay(vo.getStartHalfDay())
                    .setEndDate(vo.getEndDate())
                    .setEndHalfDay(vo.getEndHalfDay())
                    .setNote(vo.getNote());

            try {
                JsonResult r = edit(log);
                if (!r.getCode().equals(0)) {
                    vo.setNote(vo.getNote() + "\n" + "错误：" + r.getMsg());
                    badInfoList.add(vo);
                } else {
                    successCount++;
                }
            } catch (Exception ex) {
                vo.setNote(vo.getNote() + "\n" + ex.getMessage());
                badInfoList.add(vo);
            }
        }
        return JsonResult.success("成功写入 " + successCount
                        + " 条数据。"
                , badInfoList);
        // return JsonResult.error("未实现功能");
    }
}