/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.constant.RoleConstant;
import me.arnu.system.entity.Role;
import me.arnu.system.mapper.RoleMapper;
import me.arnu.system.query.RoleQuery;
import me.arnu.system.service.IRoleService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.RoleListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        RoleQuery roleQuery = (RoleQuery) query;
        // 查询条件
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        // 角色名称
        if (!StringUtils.isEmpty(roleQuery.getName())) {
            queryWrapper.like("name", roleQuery.getName());
        }
        // 状态：1正常 2禁用
        if (roleQuery.getStatus() != null) {
            queryWrapper.eq("status", roleQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Role> page = new Page<>(roleQuery.getPage(), roleQuery.getLimit());
        IPage<Role> data = roleMapper.selectPage(page, queryWrapper);
        List<Role> roleList = data.getRecords();
        List<RoleListVo> roleListVoList = new ArrayList<>();
        if (!roleList.isEmpty()) {
            roleList.forEach(item -> {
                RoleListVo roleListVo = new RoleListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, roleListVo);
                // 状态描述
                if (roleListVo.getStatus() != null && roleListVo.getStatus() > 0) {
                    roleListVo.setStatusName(RoleConstant.ROLE_STATUS_LIST.get(roleListVo.getStatus()));
                }
                // 添加人名称
                if (roleListVo.getCreateUser() != null && roleListVo.getCreateUser() > 0) {
                    roleListVo.setCreateUserName(UserUtils.getName((roleListVo.getCreateUser())));
                }
                // 更新人名称
                if (roleListVo.getUpdateUser() != null && roleListVo.getUpdateUser() > 0) {
                    roleListVo.setUpdateUserName(UserUtils.getName((roleListVo.getUpdateUser())));
                }
                roleListVoList.add(roleListVo);
            });
        }
        return JsonResult.success("操作成功", roleListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Role entity = (Role) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Role entity) {
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
        Role entity = this.getById(id);
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
    public JsonResult setStatus(Role entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 根据人员ID获取角色列表
     *
     * @param userId 人员ID
     * @return
     */
    @Override
    public List<Role> getRoleListByUserId(Integer userId) {
        return roleMapper.getRoleListByUserId(userId);
    }
}