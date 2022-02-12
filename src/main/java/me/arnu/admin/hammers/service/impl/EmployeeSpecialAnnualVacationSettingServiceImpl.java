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
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.admin.hammers.constant.EmployeeSpecialAnnualVacationSettingConstant;
import me.arnu.admin.hammers.entity.EmployeeSpecialAnnualVacationSetting;
import me.arnu.admin.hammers.mapper.EmployeeSpecialAnnualVacationSettingMapper;
import me.arnu.admin.hammers.query.EmployeeSpecialAnnualVacationSettingQuery;
import me.arnu.admin.hammers.service.IEmployeeSpecialAnnualVacationSettingService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.EmployeeSpecialAnnualVacationSettingListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 员工年假特殊设置 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class EmployeeSpecialAnnualVacationSettingServiceImpl extends BaseServiceImpl<EmployeeSpecialAnnualVacationSettingMapper, EmployeeSpecialAnnualVacationSetting> implements IEmployeeSpecialAnnualVacationSettingService {

    @Autowired
    private EmployeeSpecialAnnualVacationSettingMapper employeeSpecialAnnualVacationSettingMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EmployeeSpecialAnnualVacationSettingQuery employeeSpecialAnnualVacationSettingQuery = (EmployeeSpecialAnnualVacationSettingQuery) query;
        // 查询条件
        QueryWrapper<EmployeeSpecialAnnualVacationSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<EmployeeSpecialAnnualVacationSetting> page = new Page<>(employeeSpecialAnnualVacationSettingQuery.getPage(), employeeSpecialAnnualVacationSettingQuery.getLimit());
        IPage<EmployeeSpecialAnnualVacationSetting> data = employeeSpecialAnnualVacationSettingMapper.selectPage(page, queryWrapper);
        List<EmployeeSpecialAnnualVacationSetting> employeeSpecialAnnualVacationSettingList = data.getRecords();
        List<EmployeeSpecialAnnualVacationSettingListVo> employeeSpecialAnnualVacationSettingListVoList = new ArrayList<>();
        if (!employeeSpecialAnnualVacationSettingList.isEmpty()) {
            employeeSpecialAnnualVacationSettingList.forEach(item -> {
                EmployeeSpecialAnnualVacationSettingListVo employeeSpecialAnnualVacationSettingListVo = new EmployeeSpecialAnnualVacationSettingListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, employeeSpecialAnnualVacationSettingListVo);
                // 创建人名称
                if (employeeSpecialAnnualVacationSettingListVo.getCreateUser() != null && employeeSpecialAnnualVacationSettingListVo.getCreateUser() > 0) {
                    employeeSpecialAnnualVacationSettingListVo.setCreateUserName(UserUtils.getName((employeeSpecialAnnualVacationSettingListVo.getCreateUser())));
                }
                // 更新人名称
                if (employeeSpecialAnnualVacationSettingListVo.getUpdateUser() != null && employeeSpecialAnnualVacationSettingListVo.getUpdateUser() > 0) {
                    employeeSpecialAnnualVacationSettingListVo.setUpdateUserName(UserUtils.getName((employeeSpecialAnnualVacationSettingListVo.getUpdateUser())));
                }
                employeeSpecialAnnualVacationSettingListVoList.add(employeeSpecialAnnualVacationSettingListVo);
            });
        }
        return JsonResult.success("操作成功", employeeSpecialAnnualVacationSettingListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        EmployeeSpecialAnnualVacationSetting entity = (EmployeeSpecialAnnualVacationSetting) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(EmployeeSpecialAnnualVacationSetting entity) {
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
        EmployeeSpecialAnnualVacationSetting entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}