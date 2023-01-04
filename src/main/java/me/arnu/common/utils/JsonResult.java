/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.utils;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * JSON回应类
 */
public class JsonResult implements Serializable {
    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "操作成功";
    public static final Object DEFAULT_DATA = null;
    // 错误码
    private final Integer code;

    // 提示语
    private final String msg;//  = "操作成功";

    // 返回对象
    private final Object data;

    // 数据总数
    private final long count;

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getData() {
        return this.data;
    }

    public long getCount() {
        return this.count;
    }

    /**
     * 构造函数
     */
    public JsonResult(Integer code, String msg, Object data, long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public static JsonResult success() {
        return new JsonResult(SUCCESS_CODE, SUCCESS_MSG, DEFAULT_DATA, 0);
    }

    public static JsonResult success(String msg) {
        return new JsonResult(SUCCESS_CODE, msg, DEFAULT_DATA, 0);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(SUCCESS_CODE, SUCCESS_MSG, data, 0);
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(SUCCESS_CODE, msg, data, 0);
    }

    public static JsonResult success(String msg, Object data, long count) {
        return new JsonResult(SUCCESS_CODE, msg, data, count);
    }


    public static JsonResult error() {
        return new JsonResult(-1, "操作失败", DEFAULT_DATA, 0);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(400, msg, DEFAULT_DATA, 0);
    }

    public static JsonResult error(Integer code, String msg) {
        return new JsonResult(code, msg, DEFAULT_DATA, 0);
    }

    public static JsonResult error(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data, 0);
    }

    public static JsonResult error(ErrorCode errorCode) {
        return new JsonResult(errorCode.getCode(), errorCode.getMsg(), DEFAULT_DATA, 0);
    }

    public static JsonResult error(HttpStatus httpStatus, String msg, Object data) {
        return new JsonResult(httpStatus.value(), msg, data, 0);
    }

    public static JsonResult error(HttpStatus httpStatus, String msg) {
        return new JsonResult(httpStatus.value(), msg, null, 0);
    }

}
