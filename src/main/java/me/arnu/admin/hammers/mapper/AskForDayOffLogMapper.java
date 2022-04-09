/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.arnu.admin.hammers.entity.AskForDayOffLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.arnu.admin.hammers.entity.Employee;
import me.arnu.admin.hammers.query.AskForDayOffLogQuery;
import me.arnu.admin.hammers.query.EmployeeQuery;
import me.arnu.admin.hammers.vo.AskForDayOffLogListVo;
import me.arnu.admin.hammers.vo.EmployeeListVo;

/**
 * <p>
 * 请假记录 Mapper 接口
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
public interface AskForDayOffLogMapper extends BaseMapper<AskForDayOffLog> {
    IPage<AskForDayOffLogListVo> selectVoPage(IPage<AskForDayOffLog> page, AskForDayOffLogQuery query);
}
