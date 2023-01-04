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
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.mapper.EmployeeMapper;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.admin.hammers.entity.NatureYearAnnualVacationBalance;
import me.arnu.admin.hammers.mapper.NatureYearAnnualVacationBalanceMapper;
import me.arnu.admin.hammers.query.NatureYearAnnualVacationBalanceQuery;
import me.arnu.admin.hammers.service.INatureYearAnnualVacationBalanceService;
import me.arnu.system.entity.Dept;
import me.arnu.system.entity.Level;
import me.arnu.system.entity.Position;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.NatureYearAnnualVacationBalanceListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自然年结余天数 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-13
 */
@Service
public class NatureYearAnnualVacationBalanceServiceImpl extends BaseServiceImpl<NatureYearAnnualVacationBalanceMapper, NatureYearAnnualVacationBalance> implements INatureYearAnnualVacationBalanceService {

    @Autowired
    private NatureYearAnnualVacationBalanceMapper natureYearAnnualVacationBalanceMapper;

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

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        NatureYearAnnualVacationBalanceQuery natureYearAnnualVacationBalanceQuery = (NatureYearAnnualVacationBalanceQuery) query;
        // 查询条件
        QueryWrapper<NatureYearAnnualVacationBalance> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(natureYearAnnualVacationBalanceQuery.getEmployeeId())) {
            queryWrapper.like("employee_id", natureYearAnnualVacationBalanceQuery.getEmployeeId());
        }
        if (!StringUtils.isEmpty(natureYearAnnualVacationBalanceQuery.getYear())) {
            queryWrapper.like("year", natureYearAnnualVacationBalanceQuery.getYear());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<NatureYearAnnualVacationBalance> page = new Page<>(natureYearAnnualVacationBalanceQuery.getPage(), natureYearAnnualVacationBalanceQuery.getLimit());
        IPage<NatureYearAnnualVacationBalance> data = natureYearAnnualVacationBalanceMapper.selectPage(page, queryWrapper);
        List<NatureYearAnnualVacationBalance> natureYearAnnualVacationBalanceList = data.getRecords();
        List<NatureYearAnnualVacationBalanceListVo> natureYearAnnualVacationBalanceListVoList = new ArrayList<>();
        if (!natureYearAnnualVacationBalanceList.isEmpty()) {
            List<String> empIds = new ArrayList<>();
            for (NatureYearAnnualVacationBalance natureYearAnnualVacationBalance : natureYearAnnualVacationBalanceList) {
                empIds.add(natureYearAnnualVacationBalance.getEmployeeId());
            }
            Map<String, String> eMap = loadEmpRealName(empIds);
            natureYearAnnualVacationBalanceList.forEach(item -> {
                NatureYearAnnualVacationBalanceListVo natureYearAnnualVacationBalanceListVo = new NatureYearAnnualVacationBalanceListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, natureYearAnnualVacationBalanceListVo);
                // 员工姓名
                natureYearAnnualVacationBalanceListVo.setRealName(eMap.get(natureYearAnnualVacationBalanceListVo.getEmployeeId()));
                // 创建人名称
                if (natureYearAnnualVacationBalanceListVo.getCreateUser() != null && natureYearAnnualVacationBalanceListVo.getCreateUser() > 0) {
                    natureYearAnnualVacationBalanceListVo.setCreateUserName(UserUtils.getName((natureYearAnnualVacationBalanceListVo.getCreateUser())));
                }
                // 更新人名称
                if (natureYearAnnualVacationBalanceListVo.getUpdateUser() != null && natureYearAnnualVacationBalanceListVo.getUpdateUser() > 0) {
                    natureYearAnnualVacationBalanceListVo.setUpdateUserName(UserUtils.getName((natureYearAnnualVacationBalanceListVo.getUpdateUser())));
                }
                natureYearAnnualVacationBalanceListVoList.add(natureYearAnnualVacationBalanceListVo);
            });
        }
        return JsonResult.success("操作成功", natureYearAnnualVacationBalanceListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        NatureYearAnnualVacationBalance entity = (NatureYearAnnualVacationBalance) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(NatureYearAnnualVacationBalance entity) {
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
        NatureYearAnnualVacationBalance entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }


    /**
     * 批量添加，用于导入功能
     *
     * @param list      数据
     * @param year
     * @param overwrite 是否覆盖
     * @return 成功或失败
     */
    @Override
    public JsonResult addBatch(List<EmployeeDayOffSummaryVo> list, int year, Boolean overwrite) {
        List<EmployeeDayOffSummaryVo> result = new ArrayList<>();
        int successCount = 0;
        // 数据也不多，一条一条的来吧
        for (EmployeeDayOffSummaryVo vo : list) {
            QueryWrapper<NatureYearAnnualVacationBalance> w = new QueryWrapper<NatureYearAnnualVacationBalance>();

            if (vo.getThisYearRemainAnnualVacationDays() <= 0) {
                vo.setDept("结余天数不可小于等于0");
                result.add(vo);
                continue;
            }
            // 如果不是覆盖，就需要看看现有的
            w.eq("employee_id", vo.getEmployeeId());
            w.eq("year", year);

            NatureYearAnnualVacationBalance b = getOne(w, false);
            if (b != null) {
                if (overwrite) {
                    b.setDays((float) vo.getThisYearRemainAnnualVacationDays());
                    super.edit(b);
                    successCount ++;
                } else {
                    vo.setDept("数据存在已跳过");
                    result.add(vo);
                }
                continue;
            }
            QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("mark", 1);
            queryWrapper.eq("employee_id", vo.getEmployeeId());
            Employee e = employeeMapper.selectOne(queryWrapper);
            if (e == null) {
                vo.setDept("员工号不存在");
                result.add(vo);
                continue;
            }

            b = new NatureYearAnnualVacationBalance();
            b.setEmployeeId(vo.getEmployeeId());
            b.setYear(year);
            b.setNote("自动计算批量写入");
            b.setDays((float) vo.getThisYearRemainAnnualVacationDays());
            super.add(b);
            successCount++;
        }
        return JsonResult.success("成功写入 " + successCount
                + " 条数据。", result, result.size());
        // return JsonResult.error("未实现功能");
    }
}