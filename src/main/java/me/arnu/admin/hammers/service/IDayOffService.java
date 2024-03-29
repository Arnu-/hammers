/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service;

import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.common.common.IBaseService;
import me.arnu.common.utils.JsonResult;

import java.util.Map;

/**
 * <p>
 * 请假类型 服务类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
public interface IDayOffService {

    JsonResult getList(DayOffTypeQuery query);

    JsonResult edit(DayOffType entity);

    Map<String, Object> info(Integer id);

    /**
     * 获取需要的字段头
     * @return
     */
    JsonResult getFields();

    /**
     * 查询某个员工的年假计算明细
     * @param query
     * @return
     */
    JsonResult annualDetail(DayOffTypeQuery query);
}