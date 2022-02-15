/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.arnu.common.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 员工列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Data
public class EmployeeListVo {

    /**
     * 员工ID
     */
    private Integer id;

    /**
     * 员工号
     */
    @Excel(name = "员工号")
    private String employeeId;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String realname;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;

    /**
     * 性别
     */
    @Excel(name = "性别", readConverterExp = "1=男,2=女,3=保密", type = Excel.Type.EXPORT)
    private Integer gender;

    /**
     * 性别
     */
    @Excel(name = "性别", type = Excel.Type.IMPORT)
    private String genderStr;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 手机
     */
    @Excel(name = "手机")
    private String mobile;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "生日")
    private Date birthday;

    /**
     * 部门
     */
    private Integer deptId;
    @Excel(name = "部门")
    private String dept;

    /**
     * 级别
     */
    private Integer levelId;
    @Excel(name = "级别")
    private String level;

    /**
     * 职位
     */
    private Integer positionId;
    @Excel(name = "职位")
    private String position;

    /**
     * 省
     */
    private Integer provinceId;
    @Excel(name = "省", type = Excel.Type.EXPORT)
    private String province;

    /**
     * 市
     */
    private Integer cityId;

    /**
     * 城市名
     */
    @Excel(name = "城市名", type = Excel.Type.EXPORT)
    private String cityName;

    /**
     * 区/县
     */
    private Integer districtId;
    @Excel(name = "区", type = Excel.Type.EXPORT)
    private String district;

    /**
     * 地址
     */
    @Excel(name = "地址", type = Excel.Type.EXPORT)
    private String address;


    /**
     * 入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "入职日期")
    private Date enrollmentDate;

    /**
     * 转正日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "转正日期")
    private Date formalDate;

    /**
     * 离职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "离职日期")
    private Date leaveDate;

    /**
     * 工龄
     */
    @Excel(name = "工龄", cellType = Excel.ColumnType.NUMERIC)
    private Float workYear;

    /**
     * 状态
     */
    @Excel(name = "状态", readConverterExp = "1=正常,2=停用", type = Excel.Type.EXPORT)
    private Integer status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}