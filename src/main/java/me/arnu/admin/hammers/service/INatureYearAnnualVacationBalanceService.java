/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service;

import me.arnu.admin.hammers.entity.NatureYearAnnualVacationBalance;
import me.arnu.admin.hammers.vo.EmployeeDayOffSummaryVo;
import me.arnu.common.common.IBaseService;
import me.arnu.common.utils.JsonResult;

import java.util.List;

/**
 * <p>
 * 自然年结余天数 服务类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-13
 */
public interface INatureYearAnnualVacationBalanceService extends IBaseService<NatureYearAnnualVacationBalance> {

    /**
     * 批量添加年假结余
     *
     * @param list      批量列表
     * @param year      操作的年
     * @param overwrite 是否覆盖
     * @return 成功/失败
     */
    JsonResult addBatch(List<EmployeeDayOffSummaryVo> list, int year, Boolean overwrite);
}