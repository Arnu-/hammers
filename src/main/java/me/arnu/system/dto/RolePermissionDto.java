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
public class RolePermissionDto {

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色ID
     */
    private String authIds;

}
