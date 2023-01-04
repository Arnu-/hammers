/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/

package me.arnu.admin.hammers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.mapper.EmployeeMapper;
import me.arnu.admin.hammers.query.EmployeeQuery;
import me.arnu.admin.hammers.service.IEmployeeService;
import me.arnu.admin.hammers.utils.DayOffUtil;
import me.arnu.admin.hammers.vo.EmployeeListVo;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.DateUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.entity.Dept;
import me.arnu.system.entity.Level;
import me.arnu.system.entity.Position;
import me.arnu.system.mapper.DeptMapper;
import me.arnu.system.mapper.LevelMapper;
import me.arnu.system.mapper.PositionMapper;
import me.arnu.system.utils.ShiroUtils;
import me.arnu.system.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DeptMapper detpMapper;

    @Autowired
    private LevelMapper levelMapper;

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return 返回员工列表
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EmployeeQuery employeeQuery = (EmployeeQuery) query;
        // 查询条件
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        // 手机
        if (!StringUtils.isEmpty(employeeQuery.getMobile())) {
            queryWrapper.like("real_name", employeeQuery.getMobile()).or()
                    .like("mobile", employeeQuery.getMobile());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Employee> page = new Page<>(employeeQuery.getPage(), employeeQuery.getLimit());

        // IPage<Employee> data = employeeMapper.selectPage(page, queryWrapper);
        IPage<EmployeeListVo> data = employeeMapper.selectVoPage(page, employeeQuery);
        // List<EmployeeListVo> employeeList = data.getRecords();
        // List<EmployeeListVo> employeeListVoList = new ArrayList<>();
        List<EmployeeListVo> employeeListVoList = data.getRecords();
        if (!employeeListVoList.isEmpty()) {
            employeeListVoList.forEach(item -> {
                item.setWorkYear(DayOffUtil.calcWorkYearToThisYearEnd(item.getEnrollmentDate()));
                // EmployeeListVo employeeListVo = new EmployeeListVo();
                // 拷贝属性
                // BeanUtils.copyProperties(item, employeeListVo);
                // 头像地址
                if (!StringUtils.isEmpty(item.getAvatar())) {
                    item.setAvatarUrl(CommonUtils.getImageURL(item.getAvatar()));
                }
                // 创建人名称
                if (item.getCreateUser() != null && item.getCreateUser() > 0) {
                    item.setCreateUserName(UserUtils.getName((item.getCreateUser())));
                }
                // 更新人名称
                if (item.getUpdateUser() != null && item.getUpdateUser() > 0) {
                    item.setUpdateUserName(UserUtils.getName((item.getUpdateUser())));
                }
                // employeeListVoList.add(employeeListVo);
            });
        }
        return JsonResult.success("操作成功", employeeListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return 获取员工详情
     */
    @Override
    public Object getInfo(Serializable id) {
        Employee entity = (Employee) super.getInfo(id);
        // 头像解析
        if (!StringUtils.isEmpty(entity.getAvatar())) {
            entity.setAvatar(CommonUtils.getImageURL(entity.getAvatar()));
        }
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return 返回编辑结果
     */
    @Override
    public JsonResult edit(Employee entity) {
        // 头像
        if (entity.getAvatar().contains(CommonConfig.imageURL)) {
            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
        }
        return super.edit(entity);
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
        Employee entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return 返回状态结果
     */
    @Override
    public JsonResult setStatus(Employee entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 批量添加，用于导入功能
     *
     * @param list               数据
     * @param autoCreateDept     是否自动创建部门
     * @param autoCreateLevel    是否自动创建级别
     * @param autoCreatePosition 是否自动创建职位
     * @return 成功或失败
     */
    @Override
    public JsonResult addBatch(List<EmployeeListVo> list, boolean autoCreateDept, boolean autoCreateLevel,
                               boolean autoCreatePosition) {
        // 1、工号不可重复
        // 2、部门不存在则创建，可选
        // 3、级别不存在则创建，可选
        // 4、职位不存在则创建，可选
        // 5、自动判断性别数值
        // 6、默认status都是正常
        List<Dept> depts = detpMapper.selectList(new QueryWrapper<Dept>() {
            {
                eq("mark", 1);
            }
        });
        Map<String, Integer> deptMap = new HashMap<>();
        for (Dept dept : depts) {
            deptMap.put(dept.getName(), dept.getId());
        }
        List<Level> levels = levelMapper.selectList(new QueryWrapper<Level>() {
            {
                eq("mark", 1);
            }
        });
        Map<String, Integer> levelMap = new HashMap<>();
        for (Level level : levels) {
            levelMap.put(level.getName(), level.getId());
        }
        List<Position> positions = positionMapper.selectList(new QueryWrapper<Position>() {
            {
                eq("mark", 1);
            }
        });
        Map<String, Integer> positionMap = new HashMap<>();
        for (Position position : positions) {
            positionMap.put(position.getName(), position.getId());
        }
        Map<String, Integer> genderMap = new HashMap<>();
        genderMap.put("男", 1);
        genderMap.put("女", 2);
        int successCount = 0;
        List<EmployeeListVo> badInfoList = new ArrayList<>();
        for (EmployeeListVo vo : list) {
            boolean skip = false;
            // 验证员工号重复
            Employee bemp = employeeMapper.selectOne(new QueryWrapper<Employee>() {
                {
                    eq("mark", 1);
                    eq("employee_id", vo.getEmployeeId());
                }
            });
            if (bemp != null) {
                vo.setNote(vo.getNote() + "\n" + "员工号重复：" + vo.getEmployeeId());
                skip = true;
            }
            Integer deptId = deptMap.getOrDefault(vo.getDept(), null);
            if (deptId == null) {
                if (autoCreateDept) {
                    Dept d = new Dept();
                    d.setName(vo.getDept())
                            .setHasChild(1)
                            .setSort(1)
                            .setType(2);
                    detpMapper.insert(d);
                    deptId = d.getId();
                    deptMap.put(vo.getDept(), deptId);
                } else {
                    vo.setNote(vo.getNote() + "\n" + "部门" + vo.getDept() + "不存在。");
                    skip = true;
                }
            }
            Integer levelId = levelMap.getOrDefault(vo.getLevel(), null);
            if (levelId == null) {
                if (autoCreateLevel) {
                    Level l = new Level();
                    l.setName(vo.getLevel())
                            .setSort(1)
                            .setStatus(1);
                    levelMapper.insert(l);
                    levelId = l.getId();
                    levelMap.put(l.getName(), l.getId());
                } else {
                    vo.setNote(vo.getNote() + "\n" + "级别" + vo.getLevel() + "不存在。");
                    skip = true;
                }
            }
            Integer posId = positionMap.getOrDefault(vo.getPosition(), null);
            if (!vo.getPosition().isEmpty()) {
                if (posId == null) {
                    if (autoCreatePosition) {
                        Position p = new Position();
                        p.setName(vo.getPosition())
                                .setSort(1)
                                .setStatus(1);
                        positionMapper.insert(p);
                        posId = p.getId();
                        positionMap.put(p.getName(), p.getId());
                    } else {
                        vo.setNote(vo.getNote() + "\n" + "职位" + vo.getPosition() + "不存在。");
                        skip = true;
                    }
                }
            }
            if (skip) {
                badInfoList.add(vo);
                continue;
            }

            Employee emp = new Employee()
                    .setEmployeeId(vo.getEmployeeId())
                    .setRealname(vo.getRealname())
                    .setNickname(vo.getNickname())
                    .setGender(genderMap.getOrDefault(vo.getGenderStr(), 3))
                    .setMobile(vo.getMobile())
                    .setEmail(vo.getEmail())
                    .setBirthday(vo.getBirthday())
                    .setDeptId(deptId)
                    .setLevelId(levelId)
                    .setPositionId(posId)
                    .setWorkYear(vo.getWorkYear())
                    .setGraduationDate(vo.getGraduationDate())
                    .setEnrollmentDate(vo.getEnrollmentDate())
                    .setFormalDate(vo.getFormalDate())
                    .setLeaveDate(vo.getLeaveDate())
                    .setNote(vo.getNote());
            try {
                employeeMapper.insert(emp);
                successCount++;
            } catch (Exception ex) {
                vo.setNote(vo.getNote() + "\n" + ex.getMessage());
                badInfoList.add(vo);
            }
        }
        return JsonResult.success("成功写入 " + successCount
                + " 条数据。", badInfoList);
        // return JsonResult.error("未实现功能");
    }

    @Override
    public JsonResult setEmpLeaveDate(Employee entity) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id", entity.getEmployeeId());
        queryWrapper.eq("mark", "1");
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if (null == emp) {
            return JsonResult.error("员工“" + entity.getEmployeeId() + "”不存在。");
        }

        UpdateWrapper<Employee> uw = new UpdateWrapper<>();
        uw.eq("id", emp.getId());
        uw.set("update_user", ShiroUtils.getUserId());
        uw.set("update_time", DateUtils.now());

        // 0、如果离职日期为空，则表示取消离职
        if (null == entity.getLeaveDate()) {
            uw.set("status", 1);
            uw.set("leave_date", null);
            employeeMapper.update(null, uw);
            return JsonResult.success("修改成功");
        }
        // 1、离职日期不能早于入职日期
        if (entity.getLeaveDate().before(emp.getEnrollmentDate())) {
            return JsonResult.error("离职日期不能早于入职日期");
        }
        uw.set("status", 2);
        uw.set("leave_date", entity.getLeaveDate());
        employeeMapper.update(null, uw);
        return JsonResult.success("修改成功");
    }
}