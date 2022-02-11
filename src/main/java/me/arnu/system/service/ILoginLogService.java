/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.system.entity.LoginLog;
import me.arnu.common.common.IBaseService;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
public interface ILoginLogService extends IBaseService<LoginLog> {

    /**
     * 创建系统登录日志
     *
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

}