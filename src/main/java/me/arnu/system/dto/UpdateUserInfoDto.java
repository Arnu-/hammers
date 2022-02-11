/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.dto;

import lombok.Data;

/**
 * 更新个人中心信息
 */
@Data
public class UpdateUserInfoDto {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 详细地址
     */
    private String address;

}
