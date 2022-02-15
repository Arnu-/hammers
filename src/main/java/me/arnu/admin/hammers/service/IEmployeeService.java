/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service;

import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.vo.EmployeeListVo;
import me.arnu.common.common.IBaseService;
import me.arnu.common.utils.JsonResult;

import java.util.List;

/**
 * <p>
 * 员工 服务类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
public interface IEmployeeService extends IBaseService<Employee> {

    /**
     * 批量添加，用于导入功能
     * @param list 数据
     * @param autoCreateDept 是否自动创建部门
     * @param autoCreateLevel 是否自动创建级别
     * @param autoCreatePosition 是否自动创建职位
     * @return 成功或失败
     */
    JsonResult addBatch(List<EmployeeListVo> list
            , boolean autoCreateDept
            , boolean autoCreateLevel
            , boolean autoCreatePosition);
}