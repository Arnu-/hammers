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
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.constant.ConfigConstant;
import me.arnu.system.entity.Config;
import me.arnu.system.mapper.ConfigMapper;
import me.arnu.system.query.ConfigQuery;
import me.arnu.system.service.IConfigService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.ConfigListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, Config> implements IConfigService {

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ConfigQuery configQuery = (ConfigQuery) query;
        // 查询条件
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        // 分组ID
        queryWrapper.eq("group_id", configQuery.getGroupId());
        // 配置标题
        if (!StringUtils.isEmpty(configQuery.getTitle())) {
            queryWrapper.like("title", configQuery.getTitle());
        }
        // 配置类型：hidden=隐藏 readonly=只读文本 number=数字 text=单行文本 textarea=多行文本 array=数组 password=密码 radio=单选框 checkbox=复选框 select=下拉框 icon=字体图标 date=日期 datetime=时间 image=单张图片 images=多张图片 file=单个文件 files=多个文件 ueditor=富文本编辑器 json=JSON
        if (!StringUtils.isEmpty(configQuery.getType())) {
            queryWrapper.eq("type", configQuery.getType());
        }
        // 状态：1正常 2停用
        if (configQuery.getStatus() != null && configQuery.getStatus() > 0) {
            queryWrapper.eq("status", configQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Config> page = new Page<>(configQuery.getPage(), configQuery.getLimit());
        IPage<Config> data = configMapper.selectPage(page, queryWrapper);
        List<Config> configList = data.getRecords();
        List<ConfigListVo> configListVoList = new ArrayList<>();
        if (!configList.isEmpty()) {
            configList.forEach(item -> {
                ConfigListVo configListVo = new ConfigListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, configListVo);
                // 配置类型描述
                if (!StringUtils.isEmpty(configListVo.getType())) {
                    configListVo.setTypeName(ConfigConstant.CONFIG_TYPE_LIST.get(configListVo.getType()));
                }
                // 状态描述
                if (configListVo.getStatus() != null && configListVo.getStatus() > 0) {
                    configListVo.setStatusName(ConfigConstant.CONFIG_STATUS_LIST.get(configListVo.getStatus()));
                }
                // 添加人名称
                if (configListVo.getCreateUser() > 0) {
                    configListVo.setCreateUserName(UserUtils.getName((configListVo.getCreateUser())));
                }
                // 更新人名称
                if (configListVo.getUpdateUser() > 0) {
                    configListVo.setUpdateUserName(UserUtils.getName((configListVo.getUpdateUser())));
                }
                configListVoList.add(configListVo);
            });
        }
        return JsonResult.success("操作成功", configListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Config entity = (Config) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Config entity) {
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
        Config entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(Config entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 根据分组ID获取配置列表
     *
     * @param groupId 分组ID
     * @return
     */
    @Override
    public List<Config> getConfigListByGroupId(Integer groupId) {
        // 查询条件
        QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Config> configList = configMapper.selectList(queryWrapper);
        if (!configList.isEmpty()) {
            configList.forEach(item -> {
                if (item.getType().equals("image")) {
                    // 单图处理
                    if (!StringUtils.isEmpty(item.getValue())) {
                        item.setValue(CommonUtils.getImageURL(item.getValue()));
                    }
                } else if (item.getType().equals("images")) {
                    // 多图处理
                }
            });
        }
        return configList;
    }
}