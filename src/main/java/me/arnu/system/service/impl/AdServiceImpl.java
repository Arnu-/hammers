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
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.constant.AdConstant;
import me.arnu.system.entity.Ad;
import me.arnu.system.entity.AdSort;
import me.arnu.system.mapper.AdMapper;
import me.arnu.system.mapper.AdSortMapper;
import me.arnu.system.query.AdQuery;
import me.arnu.system.service.IAdService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.AdListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 广告 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Service
public class AdServiceImpl extends BaseServiceImpl<AdMapper, Ad> implements IAdService {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    private AdSortMapper adSortMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AdQuery adQuery = (AdQuery) query;
        // 查询条件
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        // 广告标题
        if (!StringUtils.isEmpty(adQuery.getTitle())) {
            queryWrapper.like("title", adQuery.getTitle());
        }
        // 广告格式：1图片 2文字 3视频 4推荐
        if (adQuery.getType() != null) {
            queryWrapper.eq("type", adQuery.getType());
        }
        // 状态：1在用 2停用
        if (adQuery.getStatus() != null) {
            queryWrapper.eq("status", adQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Ad> page = new Page<>(adQuery.getPage(), adQuery.getLimit());
        IPage<Ad> data = adMapper.selectPage(page, queryWrapper);
        List<Ad> adList = data.getRecords();
        List<AdListVo> adListVoList = new ArrayList<>();
        if (!adList.isEmpty()) {
            adList.forEach(item -> {
                AdListVo adListVo = new AdListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, adListVo);
                // 广告图片地址
                if (!StringUtils.isEmpty(adListVo.getCover())) {
                    adListVo.setCoverUrl(CommonUtils.getImageURL(adListVo.getCover()));
                }
                // 广告格式描述
                if (adListVo.getType() != null && adListVo.getType() > 0) {
                    adListVo.setTypeName(AdConstant.AD_TYPE_LIST.get(adListVo.getType()));
                }
                // 状态描述
                if (adListVo.getStatus() != null && adListVo.getStatus() > 0) {
                    adListVo.setStatusName(AdConstant.AD_STATUS_LIST.get(adListVo.getStatus()));
                }
                // 广告位
                AdSort adSort = adSortMapper.selectById(adListVo.getSortId());
                if (adSort != null) {
                    adListVo.setSortName(String.format("%s=>%s", adSort.getName(), adSort.getLocId()));
                }
                // 添加人名称
                if (adListVo.getCreateUser() > 0) {
                    adListVo.setCreateUserName(UserUtils.getName((adListVo.getCreateUser())));
                }
                // 更新人名称
                if (adListVo.getUpdateUser() > 0) {
                    adListVo.setUpdateUserName(UserUtils.getName((adListVo.getUpdateUser())));
                }
                adListVoList.add(adListVo);
            });
        }
        return JsonResult.success("操作成功", adListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Ad entity = (Ad) super.getInfo(id);
        // 广告图片解析
        if (!StringUtils.isEmpty(entity.getCover())) {
            entity.setCover(CommonUtils.getImageURL(entity.getCover()));
        }
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Ad entity) {
        // 广告图片
        if (entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
        }
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
        Ad entity = this.getById(id);
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
    public JsonResult setStatus(Ad entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}