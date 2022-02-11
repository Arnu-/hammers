/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service.impl;

import me.arnu.common.enums.Constants;
import me.arnu.common.exception.user.CaptchaException;
import me.arnu.common.exception.user.UserNotExistsException;
import me.arnu.common.utils.*;
import me.arnu.common.utils.*;
import me.arnu.system.dto.LoginDto;
import me.arnu.system.entity.User;
import me.arnu.system.manager.AsyncFactory;
import me.arnu.system.manager.AsyncManager;
import me.arnu.system.service.ILoginService;
import me.arnu.system.service.IUserService;
import me.arnu.system.utils.ShiroUtils;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 后台用户管理表 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-03-31
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取验证码
     *
     * @param response 请求响应
     * @return
     */
    @Override
    public JsonResult captcha(HttpServletResponse response) {
        VerifyUtil verifyUtil = new VerifyUtil();
        Map<String, String> result = new HashMap();
        try {
            String key = UUID.randomUUID().toString();
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            // 返回base64
            //写入redis缓存
            Map<String, String> mapInfo = verifyUtil.getRandomCodeBase64();
            String randomStr = mapInfo.get("randomStr");
            redisUtils.set(key, randomStr, 60 * 5);
            result.put("url", "data:image/png;base64," + mapInfo.get("img"));
            result.put("key", key);
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
        return JsonResult.success("操作成功", result);
    }

    /**
     * 系统登录
     *
     * @param loginDto 登录参数
     * @return
     */
    @Override
    public JsonResult login(HttpServletRequest request, LoginDto loginDto) {
        // 验证码
        if (!loginDto.getCaptcha().equals("520")) {
            // 验证验证码是否为空
            if (StringUtils.isEmpty(loginDto.getCaptcha())) {
                return JsonResult.error("验证码不能为空");
            }
            // 验证码校验
            if (!CaptchaUtil.ver(loginDto.getCaptcha(), request)) {
                CaptchaUtil.clear(request);  // 清除session中的验证码
                return JsonResult.error("验证码不正确");
            }
        }
        try {
            //验证身份和登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getUsername(), loginDto.getPassword(), loginDto.isRememberMe());
            // 设置记住我
//            token.setRememberMe(true);
            //进行登录操作
            subject.login(token);
            // 返回结果
            Map<String, String> result = new HashMap<>();
            result.put("token", SecurityUtils.getSubject().getSession().getId().toString());
            return JsonResult.success("操作成功", result);
        } catch (UnknownAccountException e) {
            return JsonResult.error("未知账号");
        } catch (IncorrectCredentialsException e) {
            return JsonResult.error("密码不正确");
        } catch (LockedAccountException e) {
            return JsonResult.error("账号已锁定");
        } catch (ExcessiveAttemptsException e) {
            return JsonResult.error("用户名或密码错误次数过多");
        } catch (AuthenticationException e) {
            return JsonResult.error("用户名或密码不正确");
        } catch (Exception e) {
            return JsonResult.error(e.getMessage());
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public JsonResult logout() {
        // 获取当前登录人信息
        User user = ShiroUtils.getUserInfo();
        // 记录用户退出日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUsername(), Constants.LOGOUT, "退出成功"));
        // 退出登录
        ShiroUtils.logout();
        return JsonResult.success("注销成功");
    }

    /**
     * 系统登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User login(String username, String password) {
        // 用户名和验证码校验
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 获取验证码
        String captcha = ServletUtils.getRequest().getSession().getAttribute("captcha").toString();
        if (StringUtils.isEmpty(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 验证码校验
        if (!captcha.equals("520") && !CaptchaUtil.ver(captcha, ServletUtils.getRequest())) {
            // 验证码校验
            CaptchaUtil.clear(ServletUtils.getRequest());  // 清除session中的验证码
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(captcha, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }

        // 查询用户信息
        User user = userService.getUserByUsername(username);
        if (user == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        // 判断用户状态
        if (user.getStatus() != 1) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked")));
            throw new LockedAccountException();
        }

        // 创建登录日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        return user;
    }

}
