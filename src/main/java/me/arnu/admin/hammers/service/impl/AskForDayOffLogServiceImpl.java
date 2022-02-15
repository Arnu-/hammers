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
import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.entity.NatureYearAnnualVacationBalance;
import me.arnu.admin.hammers.mapper.DayOffTypeMapper;
import me.arnu.admin.hammers.mapper.EmployeeMapper;
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.admin.hammers.constant.AskForDayOffLogConstant;
import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.mapper.AskForDayOffLogMapper;
import me.arnu.admin.hammers.query.AskForDayOffLogQuery;
import me.arnu.admin.hammers.service.IAskForDayOffLogService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.AskForDayOffLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

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

    @Autowired
    private DayOffTypeMapper dayOffTypeMapper;

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

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AskForDayOffLogQuery askForDayOffLogQuery = (AskForDayOffLogQuery) query;
        // 查询条件
        QueryWrapper<AskForDayOffLog> queryWrapper = new QueryWrapper<>();
        // 员工号
        if (!StringUtils.isEmpty(askForDayOffLogQuery.getEmployeeId())) {
            queryWrapper.like("employee_id", askForDayOffLogQuery.getEmployeeId());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<AskForDayOffLog> page = new Page<>(askForDayOffLogQuery.getPage(), askForDayOffLogQuery.getLimit());
        IPage<AskForDayOffLog> data = askForDayOffLogMapper.selectPage(page, queryWrapper);
        List<AskForDayOffLog> askForDayOffLogList = data.getRecords();
        List<AskForDayOffLogListVo> askForDayOffLogListVoList = new ArrayList<>();
        if (!askForDayOffLogList.isEmpty()) {
            List<String> empIds = new ArrayList<>();
            for (AskForDayOffLog askForDayOffLog : askForDayOffLogList) {
                empIds.add(askForDayOffLog.getEmployeeId());
            }
            Map<String, String> eMap = loadEmpRealName(empIds);
            Map<Integer, String> tMap = loadDayOffType();
            askForDayOffLogList.forEach(item -> {
                AskForDayOffLogListVo askForDayOffLogListVo = new AskForDayOffLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, askForDayOffLogListVo);
                askForDayOffLogListVo.setRealName(eMap.get(item.getEmployeeId()));
                askForDayOffLogListVo.setDayOffType(tMap.get(item.getDayOffTypeId()));
                // 创建人名称
                if (askForDayOffLogListVo.getCreateUser() != null && askForDayOffLogListVo.getCreateUser() > 0) {
                    askForDayOffLogListVo.setCreateUserName(UserUtils.getName((askForDayOffLogListVo.getCreateUser())));
                }
                // 更新人名称
                if (askForDayOffLogListVo.getUpdateUser() != null && askForDayOffLogListVo.getUpdateUser() > 0) {
                    askForDayOffLogListVo.setUpdateUserName(UserUtils.getName((askForDayOffLogListVo.getUpdateUser())));
                }
                askForDayOffLogListVoList.add(askForDayOffLogListVo);
            });
        }
        return JsonResult.success("操作成功", askForDayOffLogListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        AskForDayOffLog entity = (AskForDayOffLog) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
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
        }
        return super.edit(entity);
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
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

}