/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service;

import me.arnu.admin.hammers.entity.AskForDayOffLog;
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

    JsonResult addBatch(List<AskForDayOffLogListVo> list, Boolean autoCreateType);
}