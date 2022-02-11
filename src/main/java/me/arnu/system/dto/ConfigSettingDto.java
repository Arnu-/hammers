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
public class ConfigSettingDto {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段值
     */
    private String value;

}
