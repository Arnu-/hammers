/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.common.utils.JsonResult;
import me.arnu.system.entity.City;
import me.arnu.common.common.IBaseService;

/**
 * <p>
 * 城市 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public interface ICityService extends IBaseService<City> {

    /**
     * 根据父级ID获取下级城市
     *
     * @param pid 父级ID
     * @return
     */
    JsonResult getCityListByPid(Integer pid);

    /**
     * 根据城市ID获取城市名称
     * @param cityId 城市ID
     * @param delimiter 拼接字符
     * @return
     */
    String getCityNameByCityId(Integer cityId, String delimiter);

}