/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service;

import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.vo.AskForDayOffLogListVo;
import me.arnu.common.common.IBaseService;
import me.arnu.common.utils.JsonResult;

import java.util.List;

/**
 * <p>
 * 请假记录 服务类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
public interface IAskForDayOffLogService extends IBaseService<AskForDayOffLog> {

    /**
     * 批量添加，用于导入功能
     * @param list 数据
     * @param autoCreateType 自动创建请假类型
     * @param autoAnnualToOther 自动把不够的年假转事假
     * @return 成功或失败
     */
    JsonResult addBatch(List<AskForDayOffLogListVo> list, Boolean autoCreateType, Boolean autoAnnualToOther);

    /**
     * 获取指定的员工的年假剩余
     * @param query 查询
     * @return 年假剩余
     */
    String getAllBalance(DayOffTypeQuery query);
}