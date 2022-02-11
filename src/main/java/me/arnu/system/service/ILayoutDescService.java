/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.system.entity.LayoutDesc;
import me.arnu.common.common.IBaseService;

/**
 * <p>
 * 布局描述 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
public interface ILayoutDescService extends IBaseService<LayoutDesc> {

    /**
     * 根据ID获取位置描述
     * @param itemId 页面位置ID
     * @param locId 位置ID
     * @return
     */
    LayoutDesc getLocDescById(Integer itemId, Integer locId);

}