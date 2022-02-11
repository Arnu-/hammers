/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 操作日志列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Data
public class OperLogListVo {

    /**
     * 操作日志ID
     */
    private Integer id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 业务类型：0其它 1新增 2修改 3删除
     */
    private Integer businessType;

    /**
     * 业务类型描述
     */
    private String businessTypeName;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作类别：0其它 1后台用户 2手机端用户
     */
    private Integer operatorType;

    /**
     * 操作类别描述
     */
    private String operatorTypeName;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 请求URL
     */
    private String operUrl;

    /**
     * 主机地址
     */
    private String operIp;

    /**
     * 操作地点
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态：1正常 2异常
     */
    private Integer status;

    /**
     * 操作状态描述
     */
    private String statusName;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

}