/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.controller;

import me.arnu.common.config.SystemConfig;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.RedisUtils;
import me.arnu.system.dto.LoginDto;
import me.arnu.system.service.ILoginService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 系统登录 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-04-17
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登录首页
     *
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("fullName", SystemConfig.fullName);
        return "login";
    }

    /**
     * 系统登录
     *
     * @param request  网络请求
     * @param loginDto 登录参数
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public JsonResult login(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        return loginService.login(request, loginDto);
    }

    /**
     * 获取验证码
     *
     * @param response
     * @return
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 未认证通过
     *
     * @return
     */
    @GetMapping("/unauth")
    public String unauth() {
        return "unauth";
    }

}
