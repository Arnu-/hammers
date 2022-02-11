/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.common.utils.JsonResult;

import java.util.Map;


/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public interface IConfigWebService {

    /**
     * 编辑配置信息
     *
     * @param map 参数
     * @return
     */
    JsonResult configEdit(Map<String, Object> map);

}