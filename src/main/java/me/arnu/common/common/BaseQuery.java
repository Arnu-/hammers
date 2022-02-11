/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.common;

import lombok.Data;

/**
 * 查询对象基类
 */
@Data
public class BaseQuery {
    /**
     * 页码(默认1)
     */
    private Integer page = 1;

    /**
     * 每页数(默认：20)
     */
    private Integer limit = 20;
}
