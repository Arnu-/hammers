/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.system.service;

import me.arnu.common.utils.JsonResult;
import me.arnu.system.dto.LevelDto;
import me.arnu.system.entity.Level;
import me.arnu.common.common.IBaseService;
import me.arnu.system.query.LevelQuery;
import me.arnu.system.vo.LevelListVo;

import java.util.List;

/**
 * <p>
 * 职级 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
public interface ILevelService extends IBaseService<Level> {

    /**
     * 批量设置状态
     *
     * @param levelDto 状态Dto
     * @return
     */
    JsonResult batchStatus(LevelDto levelDto);

    /**
     * 导出Excel
     *
     * @param levelQuery 查询条件
     * @return
     */
    List<LevelListVo> exportExcel(LevelQuery levelQuery);

}