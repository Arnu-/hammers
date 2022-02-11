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
import me.arnu.system.constant.AdSortConstant;
import me.arnu.system.entity.AdSort;
import me.arnu.system.entity.Item;
import me.arnu.system.mapper.AdSortMapper;
import me.arnu.system.mapper.ItemMapper;
import me.arnu.system.query.AdSortQuery;
import me.arnu.system.service.IAdSortService;
import me.arnu.system.service.IItemCateService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.AdSortListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 广告位描述 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-01
 */
@Service
public class AdSortServiceImpl extends BaseServiceImpl<AdSortMapper, AdSort> implements IAdSortService {

    @Autowired
    private AdSortMapper adSortMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private IItemCateService itemCateService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        AdSortQuery adSortQuery = (AdSortQuery) query;
        // 查询条件
        QueryWrapper<AdSort> queryWrapper = new QueryWrapper<>();
        // 广告位名称
        if (!StringUtils.isEmpty(adSortQuery.getName())) {
            queryWrapper.like("name", adSortQuery.getName());
        }
        // 站点类型：1PC网站 2WAP手机站 3微信小程序 4APP移动端
        if (adSortQuery.getPlatform() != null) {
            queryWrapper.eq("platform", adSortQuery.getPlatform());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<AdSort> page = new Page<>(adSortQuery.getPage(), adSortQuery.getLimit());
        IPage<AdSort> data = adSortMapper.selectPage(page, queryWrapper);
        List<AdSort> adSortList = data.getRecords();
        List<AdSortListVo> adSortListVoList = new ArrayList<>();
        if (!adSortList.isEmpty()) {
            adSortList.forEach(item -> {
                AdSortListVo adSortListVo = new AdSortListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, adSortListVo);
                // 站点名称
                if (adSortListVo.getItemId() != null && adSortListVo.getItemId() > 0) {
                    Item itemInfo = itemMapper.selectById(adSortListVo.getItemId());
                    if (itemInfo != null) {
                        adSortListVo.setItemName(itemInfo.getName());
                    }
                }
                // 栏目名称
                if (adSortListVo.getCateId() != null && adSortListVo.getCateId() > 0) {
                    String cateName = itemCateService.getCateNameByCateId(adSortListVo.getCateId(), ">>");
                    adSortListVo.setCateName(cateName);
                }
                // 站点类型描述
                if (adSortListVo.getPlatform() != null && adSortListVo.getPlatform() > 0) {
                    adSortListVo.setPlatformName(AdSortConstant.ADSORT_PLATFORM_LIST.get(adSortListVo.getPlatform()));
                }
                // 添加人名称
                if (adSortListVo.getCreateUser() > 0) {
                    adSortListVo.setCreateUserName(UserUtils.getName((adSortListVo.getCreateUser())));
                }
                // 更新人名称
                if (adSortListVo.getUpdateUser() > 0) {
                    adSortListVo.setUpdateUserName(UserUtils.getName((adSortListVo.getUpdateUser())));
                }
                adSortListVoList.add(adSortListVo);
            });
        }
        return JsonResult.success("操作成功", adSortListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        AdSort entity = (AdSort) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(AdSort entity) {
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
        AdSort entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}