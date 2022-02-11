/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import me.arnu.common.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 职级列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Data
public class LevelListVo {

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Integer num;

    /**
     * 职级ID
     */
    @Excel(name = "职级ID", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;

    /**
     * 职级名称
     */
    @Excel(name = "职级名称")
    private String name;

    /**
     * 状态：1正常 2停用
     */
    @Excel(name = "职级状态", readConverterExp = "1=正常,2=停用")
    private Integer status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 显示顺序
     */
    @Excel(name = "排序", cellType = Excel.ColumnType.NUMERIC)
    private Integer sort;

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