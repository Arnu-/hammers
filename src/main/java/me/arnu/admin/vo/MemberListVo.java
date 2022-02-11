/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 会员用户列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class MemberListVo {

    /**
     * 会员用户ID
     */
    private Integer id;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别：1男 2女 3未知
     */
    private Integer gender;

    /**
     * 性别描述
     */
    private String genderName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date birthday;

    /**
     * 密码
     */
    private String password;

    /**
     * 户籍省份编号
     */
    private Integer provinceId;

    /**
     * 户籍城市编号
     */
    private Integer cityId;

    /**
     * 户籍区/县编号
     */
    private Integer districtId;

    /**
     * 所在城市
     */
    private String cityArea;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 设备类型：1苹果 2安卓 3WAP站 4PC站 5微信小程序 6后台添加
     */
    private Integer device;

    /**
     * 设备类型描述
     */
    private String deviceName;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 用户状态：1登录 2登出
     */
    private Integer loginStatus;

    /**
     * 用户状态描述
     */
    private String loginStatusName;

    /**
     * 推送的别名
     */
    private String pushAlias;

    /**
     * 用户来源：1注册会员 2马甲会员
     */
    private Integer source;

    /**
     * 用户来源描述
     */
    private String sourceName;

    /**
     * 是否启用：1启用  2停用
     */
    private Integer status;

    /**
     * 是否启用描述
     */
    private String statusName;

    /**
     * 客户端版本号
     */
    private String appVersion;

    /**
     * 我的推广码
     */
    private String code;

    /**
     * 推广二维码路径
     */
    private String qrcode;

    /**
     * 推广二维码地址
     */
    private String qrcodeUrl;

    /**
     * 最近登录IP
     */
    private String loginIp;

    /**
     * 上次登录地点
     */
    private String loginRegion;

    /**
     * 最近登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date loginTime;

    /**
     * 登录总次数
     */
    private Integer loginCount;

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private Integer updateUser;

    /**
     * 修改人名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

}