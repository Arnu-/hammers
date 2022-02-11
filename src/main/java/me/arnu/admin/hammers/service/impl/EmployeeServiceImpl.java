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
import me.arnu.admin.hammers.constant.EmployeeConstant;
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.mapper.EmployeeMapper;
import me.arnu.admin.hammers.query.EmployeeQuery;
import me.arnu.admin.hammers.service.IEmployeeService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.EmployeeListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        EmployeeQuery employeeQuery = (EmployeeQuery) query;
        // 查询条件
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        // 手机
        if (!StringUtils.isEmpty(employeeQuery.getMobile())) {
            queryWrapper.like("mobile", employeeQuery.getMobile());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Employee> page = new Page<>(employeeQuery.getPage(), employeeQuery.getLimit());
        IPage<Employee> data = employeeMapper.selectPage(page, queryWrapper);
        List<Employee> employeeList = data.getRecords();
        List<EmployeeListVo> employeeListVoList = new ArrayList<>();
        if (!employeeList.isEmpty()) {
            employeeList.forEach(item -> {
                EmployeeListVo employeeListVo = new EmployeeListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, employeeListVo);
                // 头像地址
                if (!StringUtils.isEmpty(employeeListVo.getAvatar())) {
                    employeeListVo.setAvatarUrl(CommonUtils.getImageURL(employeeListVo.getAvatar()));
                }
                // 创建人名称
                if (employeeListVo.getCreateUser() != null && employeeListVo.getCreateUser() > 0) {
                    employeeListVo.setCreateUserName(UserUtils.getName((employeeListVo.getCreateUser())));
                }
                // 更新人名称
                if (employeeListVo.getUpdateUser() != null && employeeListVo.getUpdateUser() > 0) {
                    employeeListVo.setUpdateUserName(UserUtils.getName((employeeListVo.getUpdateUser())));
                }
                employeeListVoList.add(employeeListVo);
            });
        }
        return JsonResult.success("操作成功", employeeListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
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
     * @return
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
     * @return
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
     * @return
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
}