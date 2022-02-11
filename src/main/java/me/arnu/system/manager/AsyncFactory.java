/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.manager;

import me.arnu.common.enums.Constants;
import me.arnu.common.utils.IpUtils;
import me.arnu.common.utils.LogUtils;
import me.arnu.common.utils.ServletUtils;
import me.arnu.common.utils.SpringUtils;
import me.arnu.system.entity.LoginLog;
import me.arnu.system.entity.OperLog;
import me.arnu.system.service.ILoginLogService;
import me.arnu.system.service.IOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 */
public class AsyncFactory {

    private static final Logger sysAdminLogger = LoggerFactory.getLogger("sys-user");

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
                                             final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                String address = IpUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sysAdminLogger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                LoginLog loginLog = new LoginLog();
                if (Constants.LOGIN_SUCCESS.equals(status)) {
                    loginLog.setTitle("登录系统");
                    loginLog.setType(1);
                } else if (Constants.LOGOUT.equals(status)) {
                    loginLog.setTitle("退出系统");
                    loginLog.setType(2);
                }
                loginLog.setLoginName(username);
                loginLog.setLoginIp(ip);
                loginLog.setLoginLocation(address);
                loginLog.setBrowser(browser);
                loginLog.setOs(os);
                loginLog.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    loginLog.setStatus(Integer.valueOf(Constants.SUCCESS));
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    loginLog.setStatus(Integer.valueOf(Constants.FAIL));
                }
                // 插入数据
                SpringUtils.getBean(ILoginLogService.class).insertLoginLog(loginLog);
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(IpUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
            }
        };
    }

}
