/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/

package me.arnu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.constant.OperLogConstant;
import me.arnu.system.entity.OperLog;
import me.arnu.system.mapper.OperLogMapper;
import me.arnu.system.query.OperLogQuery;
import me.arnu.system.service.IOperLogService;
import me.arnu.system.vo.OperLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        OperLogQuery operLogQuery = (OperLogQuery) query;
        // 查询条件
        QueryWrapper<OperLog> queryWrapper = new QueryWrapper<>();
        // 模块标题
        if (!StringUtils.isEmpty(operLogQuery.getTitle())) {
            queryWrapper.like("title", operLogQuery.getTitle());
        }
        // 业务类型：0其它 1新增 2修改 3删除
        if (operLogQuery.getBusinessType() != null && operLogQuery.getBusinessType() > 0) {
            queryWrapper.eq("business_type", operLogQuery.getBusinessType());
        }
        // 操作类别：0其它 1后台用户 2手机端用户
        if (operLogQuery.getOperatorType() != null && operLogQuery.getOperatorType() > 0) {
            queryWrapper.eq("operator_type", operLogQuery.getOperatorType());
        }
        // 操作状态：1正常 2异常
        if (operLogQuery.getStatus() != null && operLogQuery.getStatus() > 0) {
            queryWrapper.eq("status", operLogQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<OperLog> page = new Page<>(operLogQuery.getPage(), operLogQuery.getLimit());
        IPage<OperLog> data = operLogMapper.selectPage(page, queryWrapper);
        List<OperLog> operLogList = data.getRecords();
        List<OperLogListVo> operLogListVoList = new ArrayList<>();
        if (!operLogList.isEmpty()) {
            operLogList.forEach(item -> {
                OperLogListVo operLogListVo = new OperLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, operLogListVo);
                // 业务类型描述
                if (operLogListVo.getBusinessType() != null && operLogListVo.getBusinessType() > 0) {
                    operLogListVo.setBusinessTypeName(
                            OperLogConstant.OPERLOG_BUSINESSTYPE_LIST.get(operLogListVo.getBusinessType()));
                }
                // 操作类别描述
                if (operLogListVo.getOperatorType() != null && operLogListVo.getOperatorType() > 0) {
                    operLogListVo.setOperatorTypeName(
                            OperLogConstant.OPERLOG_OPERATORTYPE_LIST.get(operLogListVo.getOperatorType()));
                }
                // 操作状态描述
                if (operLogListVo.getStatus() != null && operLogListVo.getStatus() > 0) {
                    operLogListVo.setStatusName(OperLogConstant.OPERLOG_STATUS_LIST.get(operLogListVo.getStatus()));
                }
                operLogListVoList.add(operLogListVo);
            });
        }
        return JsonResult.success("操作成功", operLogListVoList, data.getTotal());
    }

    /**
     * 删除记录
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        OperLog entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog) {
        // 对于某些json数据过长的字段，需要处理
        if (operLog.getJsonResult().length() > 2000) {
            operLog.setJsonResult(operLog.getJsonResult().substring(0, 2000));
        }
        operLogMapper.insertOperlog(operLog);
    }

}