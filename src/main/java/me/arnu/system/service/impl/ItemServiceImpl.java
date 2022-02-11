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
import me.arnu.system.constant.ItemConstant;
import me.arnu.system.entity.Item;
import me.arnu.system.mapper.ItemMapper;
import me.arnu.system.query.ItemQuery;
import me.arnu.system.service.IItemService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.ItemListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 站点 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ItemQuery itemQuery = (ItemQuery) query;
        // 查询条件
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        // 站点名称
        if (!StringUtils.isEmpty(itemQuery.getName())) {
            queryWrapper.like("name", itemQuery.getName());
        }
        // 站点类型:1普通站点 2其他
        if (itemQuery.getType() != null) {
            queryWrapper.eq("type", itemQuery.getType());
        }
        // 状态：1在用 2停用
        if (itemQuery.getStatus() != null) {
            queryWrapper.eq("status", itemQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Item> page = new Page<>(itemQuery.getPage(), itemQuery.getLimit());
        IPage<Item> data = itemMapper.selectPage(page, queryWrapper);
        List<Item> itemList = data.getRecords();
        List<ItemListVo> itemListVoList = new ArrayList<>();
        if (!itemList.isEmpty()) {
            itemList.forEach(item -> {
                ItemListVo itemListVo = new ItemListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, itemListVo);
                // 站点类型描述
                if (itemListVo.getType() != null && itemListVo.getType() > 0) {
                    itemListVo.setTypeName(ItemConstant.ITEM_TYPE_LIST.get(itemListVo.getType()));
                }
                // 站点图片地址
                if (!StringUtils.isEmpty(itemListVo.getImage())) {
                    itemListVo.setImageUrl(CommonUtils.getImageURL(itemListVo.getImage()));
                }
                // 是否二级域名描述
                if (itemListVo.getIsDomain() != null && itemListVo.getIsDomain() > 0) {
                    itemListVo.setIsDomainName(ItemConstant.ITEM_ISDOMAIN_LIST.get(itemListVo.getIsDomain()));
                }
                // 状态描述
                if (itemListVo.getStatus() != null && itemListVo.getStatus() > 0) {
                    itemListVo.setStatusName(ItemConstant.ITEM_STATUS_LIST.get(itemListVo.getStatus()));
                }
                // 添加人名称
                if (itemListVo.getCreateUser() > 0) {
                    itemListVo.setCreateUserName(UserUtils.getName((itemListVo.getCreateUser())));
                }
                // 更新人名称
                if (itemListVo.getUpdateUser() > 0) {
                    itemListVo.setUpdateUserName(UserUtils.getName((itemListVo.getUpdateUser())));
                }
                itemListVoList.add(itemListVo);
            });
        }
        return JsonResult.success("操作成功", itemListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Item entity = (Item) super.getInfo(id);
        // 站点图片解析
        if (!StringUtils.isEmpty(entity.getImage())) {
            entity.setImage(CommonUtils.getImageURL(entity.getImage()));
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
    public JsonResult edit(Item entity) {
        // 站点图片
        if (entity.getImage().contains(CommonConfig.imageURL)) {
            entity.setImage(entity.getImage().replaceAll(CommonConfig.imageURL, ""));
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
        Item entity = this.getById(id);
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
    public JsonResult setStatus(Item entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}