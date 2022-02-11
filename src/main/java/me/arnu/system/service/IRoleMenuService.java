/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.common.utils.JsonResult;
import me.arnu.system.dto.RolePermissionDto;
import me.arnu.system.entity.RoleMenu;
import me.arnu.common.common.IBaseService;


/**
 * <p>
 * 角色菜单关系 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-08
 */
public interface IRoleMenuService extends IBaseService<RoleMenu> {

    /**
     * 根据角色ID获取菜单列表
     *
     * @param roleId 角色ID
     * @return
     */
    JsonResult getRolePermissionByRoleId(Integer roleId);

    /**
     * 设置角色菜单权限
     *
     * @param rolePermissionDto 角色菜单权限
     * @return
     */
    JsonResult setRolePermission(RolePermissionDto rolePermissionDto);

}