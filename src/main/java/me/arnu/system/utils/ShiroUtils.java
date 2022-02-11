/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.utils;

import me.arnu.common.utils.SpringUtils;
import me.arnu.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import java.util.Collection;
import java.util.Objects;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    /**
     * 私有构造器
     **/
    private ShiroUtils() {
    }

    private static SessionDAO sessionDAO = SpringUtils.getBean(SessionDAO.class);

    /**
     * 获取当前用户Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户退出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static User getUserInfo() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户编号
     *
     * @return
     */
    public static Integer getUserId() {
        User user = getUserInfo();
        return user.getId();
    }

    /**
     * 删除用户缓存信息
     *
     * @param username        用户名称
     * @param isRemoveSession 是否删除Session
     */
    public static void deleteCache(String username, boolean isRemoveSession) {
        //从缓存中获取Session
        Session session = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        User user;
        Object attribute = null;
        for (Session sessionInfo : sessions) {
            //遍历Session,找到该用户名称对应的Session
            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            user = (User) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (user == null) {
                continue;
            }
            if (Objects.equals(user.getUsername(), username)) {
                session = sessionInfo;
                break;
            }
        }
        if (session == null || attribute == null) {
            return;
        }
        //删除session
        if (isRemoveSession) {
            sessionDAO.delete(session);
        }
        //删除Cache，在访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }

}
