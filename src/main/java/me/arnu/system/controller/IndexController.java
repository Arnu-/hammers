/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.controller;

import me.arnu.common.config.CommonConfig;
import me.arnu.common.config.SystemConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.entity.User;
import me.arnu.system.mapper.UserMapper;
import me.arnu.system.service.IUserService;
import me.arnu.system.utils.ShiroUtils;
import me.arnu.system.dto.UpdatePasswordDto;
import me.arnu.system.dto.UpdateUserInfoDto;
import me.arnu.system.service.IMenuService;
import me.arnu.system.vo.MenuListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台 控制器
 * </p>
 *
 * @author Arnu
 * @since 2020-04-17
 */
@Controller
public class IndexController {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;

    /**
     * 列表页
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        User user = userMapper.selectById(ShiroUtils.getUserId());
        // 获取菜单
        List<MenuListVo> menuList = menuService.getNavbarMenu(ShiroUtils.getUserId());
        model.addAttribute("menuList", menuList);
        // 用户信息
        user.setAvatar(CommonUtils.getImageURL(user.getAvatar()));
        model.addAttribute("user", user);

        // 系统信息
        model.addAttribute("fullName", SystemConfig.fullName);
        model.addAttribute("nickName", SystemConfig.nickName);
        model.addAttribute("version", SystemConfig.version);
        return "index";
    }

    /**
     * 后台欢迎页
     *
     * @return
     */
    @GetMapping("/main")
    public String main() {
        return "main";
    }

    /**
     * 个人中心
     *
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        Map<String, Object> info = userService.info(ShiroUtils.getUserId());
        model.addAttribute("info", info);
        return "userInfo";
    }

    /**
     * 更新个人中心信息
     *
     * @param userInfoDto 参数
     * @return
     */
    @ResponseBody
    @PostMapping("/updateUserInfo")
    public JsonResult updateUserInfo(@RequestBody UpdateUserInfoDto userInfoDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        User entity = new User();
        entity.setId(ShiroUtils.getUserId());
        entity.setNickname(userInfoDto.getNickname());
        entity.setEmail(userInfoDto.getEmail());
        entity.setIntro(userInfoDto.getIntro());
        entity.setAddress(userInfoDto.getAddress());
        Integer result = userMapper.updateById(entity);
        if (result == 0) {
            return JsonResult.error("更新失败");
        }
        return JsonResult.success();
    }

    /**
     * 修改密码
     *
     * @param updatePasswordDto 参数
     * @return
     */
    @ResponseBody
    @PostMapping("/updatePassword")
    public JsonResult updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        // 原密码校验
        if (StringUtils.isEmpty(updatePasswordDto.getOldPassword())) {
            return JsonResult.error("原密码不能为空");
        }
        // 新密码校验
        if (StringUtils.isEmpty(updatePasswordDto.getNewPassword())) {
            return JsonResult.error("新密码不能为空");
        }
        // 确认密码
        if (StringUtils.isEmpty(updatePasswordDto.getRePassword())) {
            return JsonResult.error("确认密码不能为空");
        }
        if (!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getRePassword())) {
            return JsonResult.error("两次输入的密码不一致");
        }
        // 获取当前用户的密码
        User userInfo = userMapper.selectById(ShiroUtils.getUserId());
        if (userInfo == null) {
            return JsonResult.error("用户信息不存在");
        }
        if (!userInfo.getStatus().equals(1)) {
            return JsonResult.error("您的信息已被禁用");
        }
        userInfo.setPassword(CommonUtils.password(updatePasswordDto.getNewPassword()));
        Integer result = userMapper.updateById(userInfo);
        if (result == 0) {
            return JsonResult.error("密码修改失败");
        }
        return JsonResult.success("密码修改成功");
    }
}
