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
 *
 * @author 牧羊人
 * @date 2019/11/28
 */
public class JsonResult implements Serializable {
    // 错误码
    private Integer code = 0;

    // 提示语
    private String msg = "操作成功";

    // 返回对象
    private Object data;

    // 数据总数
    private long count;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(final long count) {
        this.count = count;
    }

    /**
     * 构造函数
     */
    public JsonResult() {
    }

    public JsonResult(String msg) {
        this.msg = msg;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Integer code, String msg, Object data, long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public static JsonResult success() {
        return new JsonResult();
    }

    public static JsonResult success(String msg) {
        return new JsonResult(msg);
    }

    public JsonResult success(Object data) {
        return new JsonResult(0, msg, data);
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(0, msg, data);
    }

    public static JsonResult success(String msg, Object data, long count) {
        return new JsonResult(0, msg, data, count);
    }


    public static JsonResult error() {
        return new JsonResult(-1, "操作失败");
    }

    public static JsonResult error(String msg) {
        return new JsonResult(401, msg);
    }

    public static JsonResult error(Integer code, String msg) {
        return new JsonResult(code, msg);
    }

    public static JsonResult error(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult error(ErrorCode errorCode) {
        return new JsonResult(errorCode.getCode(), errorCode.getMsg());
    }

    public static JsonResult error(HttpStatus httpStatus, String msg, Object data) {
        return new JsonResult(httpStatus.value(), msg, data);
    }

    public Object error(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
        return this;
    }

}
