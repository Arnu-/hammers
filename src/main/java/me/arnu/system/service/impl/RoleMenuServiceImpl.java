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
import me.arnu.common.utils.StringUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.dto.RolePermissionDto;
import me.arnu.system.entity.Menu;
import me.arnu.system.entity.RoleMenu;
import me.arnu.system.mapper.MenuMapper;
import me.arnu.system.mapper.RoleMenuMapper;
import me.arnu.system.query.RoleMenuQuery;
import me.arnu.system.service.IRoleMenuService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.RoleMenuListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关系 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-08
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        RoleMenuQuery roleMenuQuery = (RoleMenuQuery) query;
        // 查询条件
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<RoleMenu> page = new Page<>(roleMenuQuery.getPage(), roleMenuQuery.getLimit());
        IPage<RoleMenu> data = roleMenuMapper.selectPage(page, queryWrapper);
        List<RoleMenu> roleMenuList = data.getRecords();
        List<RoleMenuListVo> roleMenuListVoList = new ArrayList<>();
        if (!roleMenuList.isEmpty()) {
            roleMenuList.forEach(item -> {
                RoleMenuListVo roleMenuListVo = new RoleMenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, roleMenuListVo);
                // 添加人名称
                if (roleMenuListVo.getCreateUser() != null && roleMenuListVo.getCreateUser() > 0) {
                    roleMenuListVo.setCreateUserName(UserUtils.getName((roleMenuListVo.getCreateUser())));
                }
                // 更新人名称
                if (roleMenuListVo.getUpdateUser() != null && roleMenuListVo.getUpdateUser() > 0) {
                    roleMenuListVo.setUpdateUserName(UserUtils.getName((roleMenuListVo.getUpdateUser())));
                }
                roleMenuListVoList.add(roleMenuListVo);
            });
        }
        return JsonResult.success("操作成功", roleMenuListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        RoleMenu entity = (RoleMenu) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(RoleMenu entity) {
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
        RoleMenu entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 根据角色ID获取菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    @Override
    public JsonResult getRolePermissionByRoleId(Integer roleId) {
        // 查询条件
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            // 获取已配置菜单权限
            List<RoleMenu> roleMenuList = this.getRoleMenuListByRoleId(roleId);
            List<Integer> integerList = roleMenuList.stream().map(o -> o.getMenuId()).collect(Collectors.toList());
            menuList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                // 菜单ID
                map.put("id", item.getId());
                // 菜单名称
                map.put("name", item.getName());
                // 上级ID
                map.put("pId", item.getPid());
                // 是否选中
                if (integerList.contains(item.getId())) {
                    map.put("checked", true);
                } else {
                    map.put("checked", false);
                }
                // 是否展开
                map.put("open", true);
                mapList.add(map);
            });
        }
        return JsonResult.success("操作成功", mapList);
    }

    /**
     * 根据ID获取角色菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    private List<RoleMenu> getRoleMenuListByRoleId(Integer roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("mark", 1);
        List<RoleMenu> roleMenuList = roleMenuMapper.selectList(queryWrapper);
        return roleMenuList;
    }

    /**
     * 设置角色菜单权限
     *
     * @param rolePermissionDto 角色菜单权限
     * @return
     */
    @Override
    public JsonResult setRolePermission(RolePermissionDto rolePermissionDto) {
        // 角色ID
        if (rolePermissionDto.getRoleId() == null || rolePermissionDto.getRoleId() <= 0) {
            return JsonResult.error("角色ID不能为空");
        }
        // 菜单权限
        if (StringUtils.isEmpty(rolePermissionDto.getAuthIds())) {
            return JsonResult.error("请选择菜单权限");
        }
        String[] authList = rolePermissionDto.getAuthIds().split(",");
        List<String> integerList = Arrays.asList(authList);
        // 删除已存在的角色权限
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", rolePermissionDto.getRoleId());
        roleMenuMapper.delete(queryWrapper);
//        roleMenuMapper.deleteBatchIds(integerList);
        for (String s : authList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(rolePermissionDto.getRoleId());
            roleMenu.setMenuId(Integer.valueOf(s));
            roleMenuMapper.insert(roleMenu);
        }
        return JsonResult.success("操作成功");
    }
}