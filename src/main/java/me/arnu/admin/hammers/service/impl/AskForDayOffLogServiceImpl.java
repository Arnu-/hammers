/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.hammers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.admin.hammers.constant.AskForDayOffLogConstant;
import me.arnu.admin.hammers.entity.AskForDayOffLog;
import me.arnu.admin.hammers.mapper.AskForDayOffLogMapper;
import me.arnu.admin.hammers.query.AskForDayOffLogQuery;
import me.arnu.admin.hammers.service.IAskForDayOffLogService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.AskForDayOffLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 请假记录 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class AskForDayOffLogServiceImpl extends BaseServiceImpl<AskForDayOffLogMapper, AskForDayOffLog> implements IAskForDayOffLogService {

    @Autowired
    private AskForDayOffLogMapper askForDayOffLogMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AskForDayOffLogQuery askForDayOffLogQuery = (AskForDayOffLogQuery) query;
        // 查询条件
        QueryWrapper<AskForDayOffLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<AskForDayOffLog> page = new Page<>(askForDayOffLogQuery.getPage(), askForDayOffLogQuery.getLimit());
        IPage<AskForDayOffLog> data = askForDayOffLogMapper.selectPage(page, queryWrapper);
        List<AskForDayOffLog> askForDayOffLogList = data.getRecords();
        List<AskForDayOffLogListVo> askForDayOffLogListVoList = new ArrayList<>();
        if (!askForDayOffLogList.isEmpty()) {
            askForDayOffLogList.forEach(item -> {
                AskForDayOffLogListVo askForDayOffLogListVo = new AskForDayOffLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, askForDayOffLogListVo);
                // 创建人名称
                if (askForDayOffLogListVo.getCreateUser() != null && askForDayOffLogListVo.getCreateUser() > 0) {
                    askForDayOffLogListVo.setCreateUserName(UserUtils.getName((askForDayOffLogListVo.getCreateUser())));
                }
                // 更新人名称
                if (askForDayOffLogListVo.getUpdateUser() != null && askForDayOffLogListVo.getUpdateUser() > 0) {
                    askForDayOffLogListVo.setUpdateUserName(UserUtils.getName((askForDayOffLogListVo.getUpdateUser())));
                }
                askForDayOffLogListVoList.add(askForDayOffLogListVo);
            });
        }
        return JsonResult.success("操作成功", askForDayOffLogListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        AskForDayOffLog entity = (AskForDayOffLog) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(AskForDayOffLog entity) {
        return super.edit(entity);
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
        AskForDayOffLog entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}