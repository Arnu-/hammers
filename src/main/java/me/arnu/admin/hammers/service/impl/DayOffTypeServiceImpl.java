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
import me.arnu.admin.hammers.constant.DayOffTypeConstant;
import me.arnu.admin.hammers.entity.DayOffType;
import me.arnu.admin.hammers.mapper.DayOffTypeMapper;
import me.arnu.admin.hammers.query.DayOffTypeQuery;
import me.arnu.admin.hammers.service.IDayOffTypeService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.DayOffTypeListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 请假类型 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class DayOffTypeServiceImpl extends BaseServiceImpl<DayOffTypeMapper, DayOffType> implements IDayOffTypeService {

    @Autowired
    private DayOffTypeMapper dayOffTypeMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DayOffTypeQuery dayOffTypeQuery = (DayOffTypeQuery) query;
        // 查询条件
        QueryWrapper<DayOffType> queryWrapper = new QueryWrapper<>();
        // 类型名称
        if (!StringUtils.isEmpty(dayOffTypeQuery.getName())) {
            queryWrapper.like("name", dayOffTypeQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<DayOffType> page = new Page<>(dayOffTypeQuery.getPage(), dayOffTypeQuery.getLimit());
        IPage<DayOffType> data = dayOffTypeMapper.selectPage(page, queryWrapper);
        List<DayOffType> dayOffTypeList = data.getRecords();
        List<DayOffTypeListVo> dayOffTypeListVoList = new ArrayList<>();
        if (!dayOffTypeList.isEmpty()) {
            dayOffTypeList.forEach(item -> {
                DayOffTypeListVo dayOffTypeListVo = new DayOffTypeListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, dayOffTypeListVo);
                // 创建人名称
                if (dayOffTypeListVo.getCreateUser() != null && dayOffTypeListVo.getCreateUser() > 0) {
                    dayOffTypeListVo.setCreateUserName(UserUtils.getName((dayOffTypeListVo.getCreateUser())));
                }
                // 更新人名称
                if (dayOffTypeListVo.getUpdateUser() != null && dayOffTypeListVo.getUpdateUser() > 0) {
                    dayOffTypeListVo.setUpdateUserName(UserUtils.getName((dayOffTypeListVo.getUpdateUser())));
                }
                dayOffTypeListVoList.add(dayOffTypeListVo);
            });
        }
        return JsonResult.success("操作成功", dayOffTypeListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        DayOffType entity = (DayOffType) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(DayOffType entity) {
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
        DayOffType entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}