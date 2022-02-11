/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.system.entity.Role;
import me.arnu.common.common.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 根据人员ID获取角色列表
     *
     * @param userId 人员ID
     * @return
     */
    List<Role> getRoleListByUserId(Integer userId);

}