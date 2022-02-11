/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.dto;

import lombok.Data;

@Data
public class LevelDto {

    /**
     * 数据源ID(逗号分隔)
     */
    String ids;

    /**
     * 设置状态
     */
    Integer status;

}
