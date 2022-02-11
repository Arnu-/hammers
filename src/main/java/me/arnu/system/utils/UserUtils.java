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
import me.arnu.system.mapper.UserMapper;

public class UserUtils {

    /**
     * 根据ID获取人员名称
     *
     * @param id 人员ID
     * @return
     */
    public static String getName(Integer id) {
        UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
        User user = userMapper.selectById(id);
        return user.getRealname();
    }
}
