/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.system.entity.Menu;
import me.arnu.common.common.IBaseService;
import me.arnu.system.vo.MenuListVo;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-07
 */
public interface IMenuService extends IBaseService<Menu> {

    /**
     * 根据用户ID获取菜单权限
     *
     * @param userId 用户ID
     * @return
     */
    List<Menu> getMenuListByUserId(Integer userId);

    /**
     * 获取导航菜单
     *
     * @param userId 人员ID
     * @return
     */
    List<MenuListVo> getNavbarMenu(Integer userId);
}