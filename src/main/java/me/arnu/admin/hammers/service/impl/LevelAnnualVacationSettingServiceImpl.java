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
import me.arnu.admin.hammers.constant.LevelAnnualVacationSettingConstant;
import me.arnu.admin.hammers.entity.LevelAnnualVacationSetting;
import me.arnu.admin.hammers.mapper.LevelAnnualVacationSettingMapper;
import me.arnu.admin.hammers.query.LevelAnnualVacationSettingQuery;
import me.arnu.admin.hammers.service.ILevelAnnualVacationSettingService;
import me.arnu.system.mapper.LevelMapper;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.hammers.vo.LevelAnnualVacationSettingListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 级别年假基数 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2022-02-12
 */
@Service
public class LevelAnnualVacationSettingServiceImpl extends BaseServiceImpl<LevelAnnualVacationSettingMapper, LevelAnnualVacationSetting> implements ILevelAnnualVacationSettingService {

    @Autowired
    private LevelAnnualVacationSettingMapper levelAnnualVacationSettingMapper;
    @Autowired
    private LevelMapper levelMapper;



    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        LevelAnnualVacationSettingQuery levelAnnualVacationSettingQuery = (LevelAnnualVacationSettingQuery) query;
        // 查询条件
        QueryWrapper<LevelAnnualVacationSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<LevelAnnualVacationSetting> page = new Page<>(levelAnnualVacationSettingQuery.getPage(), levelAnnualVacationSettingQuery.getLimit());
        IPage<LevelAnnualVacationSetting> data = levelAnnualVacationSettingMapper.selectPage(page, queryWrapper);
        List<LevelAnnualVacationSetting> levelAnnualVacationSettingList = data.getRecords();
        List<LevelAnnualVacationSettingListVo> levelAnnualVacationSettingListVoList = new ArrayList<>();
        if (!levelAnnualVacationSettingList.isEmpty()) {
            levelAnnualVacationSettingList.forEach(item -> {
                LevelAnnualVacationSettingListVo levelAnnualVacationSettingListVo = new LevelAnnualVacationSettingListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, levelAnnualVacationSettingListVo);
                // 创建人名称
                if (levelAnnualVacationSettingListVo.getCreateUser() != null && levelAnnualVacationSettingListVo.getCreateUser() > 0) {
                    levelAnnualVacationSettingListVo.setCreateUserName(UserUtils.getName((levelAnnualVacationSettingListVo.getCreateUser())));
                }
                // 更新人名称
                if (levelAnnualVacationSettingListVo.getUpdateUser() != null && levelAnnualVacationSettingListVo.getUpdateUser() > 0) {
                    levelAnnualVacationSettingListVo.setUpdateUserName(UserUtils.getName((levelAnnualVacationSettingListVo.getUpdateUser())));
                }
                levelAnnualVacationSettingListVoList.add(levelAnnualVacationSettingListVo);
            });
        }
        return JsonResult.success("操作成功", levelAnnualVacationSettingListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        LevelAnnualVacationSetting entity = (LevelAnnualVacationSetting) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(LevelAnnualVacationSetting entity) {
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
        LevelAnnualVacationSetting entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}