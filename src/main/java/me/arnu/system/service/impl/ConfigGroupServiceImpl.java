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
import me.arnu.system.entity.ConfigGroup;
import me.arnu.system.mapper.ConfigGroupMapper;
import me.arnu.system.query.ConfigGroupQuery;
import me.arnu.system.service.IConfigGroupService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.ConfigGroupListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 配置分组 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class ConfigGroupServiceImpl extends BaseServiceImpl<ConfigGroupMapper, ConfigGroup> implements IConfigGroupService {

    @Autowired
    private ConfigGroupMapper configGroupMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ConfigGroupQuery configGroupQuery = (ConfigGroupQuery) query;
        // 查询条件
        QueryWrapper<ConfigGroup> queryWrapper = new QueryWrapper<>();
        // 分组名称
        if (!StringUtils.isEmpty(configGroupQuery.getName())) {
            queryWrapper.like("name", configGroupQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<ConfigGroup> page = new Page<>(configGroupQuery.getPage(), configGroupQuery.getLimit());
        IPage<ConfigGroup> data = configGroupMapper.selectPage(page, queryWrapper);
        List<ConfigGroup> configGroupList = data.getRecords();
        List<ConfigGroupListVo> configGroupListVoList = new ArrayList<>();
        if (!configGroupList.isEmpty()) {
            configGroupList.forEach(item -> {
                ConfigGroupListVo configGroupListVo = new ConfigGroupListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, configGroupListVo);
                // 添加人名称
                if (configGroupListVo.getCreateUser() > 0) {
                    configGroupListVo.setCreateUserName(UserUtils.getName((configGroupListVo.getCreateUser())));
                }
                // 更新人名称
                if (configGroupListVo.getUpdateUser() > 0) {
                    configGroupListVo.setUpdateUserName(UserUtils.getName((configGroupListVo.getUpdateUser())));
                }
                configGroupListVoList.add(configGroupListVo);
            });
        }
        return JsonResult.success("操作成功", configGroupListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ConfigGroup entity = (ConfigGroup) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(ConfigGroup entity) {
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
        ConfigGroup entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}