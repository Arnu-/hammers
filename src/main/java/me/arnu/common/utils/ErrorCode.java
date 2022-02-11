/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.utils;

/**
 * 枚举错误码
 *
 * @author 牧羊人
 * @date 2019/11/29
 */
public enum ErrorCode {

    FAILED(1, "操作失败"),
    TOKEN_MISSING(300, "token丢失"),
    TOKEN_ERROR(301, "token认证失败"),
    PARAM_MISSING(400, "参数丢失"),
    PARAM_ERROR(401, "参数错误"),
    SYSTEM_ERROR(500, "系统错误"),
    UNKNOWN_ERROR(501, "未知错误");

    public static final Integer MESSAGE_PARAM_MISSING = 400;

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误描述
     */
    private String msg;

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    /**
     * 构造函数
     *
     * @param code
     * @param msg
     */
    private ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
