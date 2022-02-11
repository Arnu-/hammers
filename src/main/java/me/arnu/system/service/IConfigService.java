/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.system.entity.Config;
import me.arnu.common.common.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统配置 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
public interface IConfigService extends IBaseService<Config> {

    /**
     * 根据分组ID获取配置列表
     * @param groupId 分组ID
     * @return
     */
    List<Config> getConfigListByGroupId(Integer groupId);

}