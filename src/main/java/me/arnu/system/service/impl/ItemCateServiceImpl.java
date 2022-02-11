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
import me.arnu.system.constant.ItemCateConstant;
import me.arnu.system.entity.Item;
import me.arnu.system.entity.ItemCate;
import me.arnu.system.mapper.ItemCateMapper;
import me.arnu.system.mapper.ItemMapper;
import me.arnu.system.query.ItemCateQuery;
import me.arnu.system.service.IItemCateService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.ItemCateListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 栏目 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Service
public class ItemCateServiceImpl extends BaseServiceImpl<ItemCateMapper, ItemCate> implements IItemCateService {

    @Autowired
    private ItemCateMapper itemCateMapper;
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
        ItemCateQuery itemCateQuery = (ItemCateQuery) query;
        // 查询条件
        QueryWrapper<ItemCate> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotNull(itemCateQuery.getPid()) && itemCateQuery.getPid() > 0) {
            queryWrapper.eq("pid", itemCateQuery.getPid());
        } else {
            queryWrapper.eq("pid", 0);
        }
        // 栏目名称
        if (!StringUtils.isEmpty(itemCateQuery.getName())) {
            queryWrapper.like("name", itemCateQuery.getName());
        }
        // 有无封面:1有 2无
        if (itemCateQuery.getIsCover() != null) {
            queryWrapper.eq("is_cover", itemCateQuery.getIsCover());
        }
        // 状态：1启用 2停用
        if (itemCateQuery.getStatus() != null) {
            queryWrapper.eq("status", itemCateQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<ItemCate> page = new Page<>(itemCateQuery.getPage(), itemCateQuery.getLimit());
        IPage<ItemCate> data = itemCateMapper.selectPage(page, queryWrapper);
        List<ItemCate> itemCateList = data.getRecords();
        List<ItemCateListVo> itemCateListVoList = new ArrayList<>();
        if (!itemCateList.isEmpty()) {
            itemCateList.forEach(item -> {
                ItemCateListVo itemCateListVo = new ItemCateListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, itemCateListVo);
                // 所属站点
                if (itemCateListVo.getItemId() != null && itemCateListVo.getItemId() > 0) {
                    Item itemInfo = itemMapper.selectById(itemCateListVo.getItemId());
                    if (itemInfo != null) {
                        itemCateListVo.setItemName(itemInfo.getName());
                    }
                }
                // 有无封面描述
                if (itemCateListVo.getIsCover() != null && itemCateListVo.getIsCover() > 0) {
                    itemCateListVo.setIsCoverName(ItemCateConstant.ITEMCATE_ISCOVER_LIST.get(itemCateListVo.getIsCover()));
                }
                // 封面地址
                if (!StringUtils.isEmpty(itemCateListVo.getCover())) {
                    itemCateListVo.setCoverUrl(CommonUtils.getImageURL(itemCateListVo.getCover()));
                }
                // 状态描述
                if (itemCateListVo.getStatus() != null && itemCateListVo.getStatus() > 0) {
                    itemCateListVo.setStatusName(ItemCateConstant.ITEMCATE_STATUS_LIST.get(itemCateListVo.getStatus()));
                }
                // 添加人名称
                if (itemCateListVo.getCreateUser() > 0) {
                    itemCateListVo.setCreateUserName(UserUtils.getName((itemCateListVo.getCreateUser())));
                }
                // 更新人名称
                if (itemCateListVo.getUpdateUser() > 0) {
                    itemCateListVo.setUpdateUserName(UserUtils.getName((itemCateListVo.getUpdateUser())));
                }
                // 是否有子级
                if (item.getPid() == 0) {
                    itemCateListVo.setHaveChild(true);
                }
                itemCateListVoList.add(itemCateListVo);
            });
        }
        return JsonResult.success("操作成功", itemCateListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ItemCate entity = (ItemCate) super.getInfo(id);
        // 封面解析
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
    public JsonResult edit(ItemCate entity) {
        // 封面
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
        ItemCate entity = this.getById(id);
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
    public JsonResult setStatus(ItemCate entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }

    /**
     * 根据栏目ID获取栏目名称
     *
     * @param cateId    栏目ID
     * @param delimiter 拼接字符
     * @return
     */
    @Override
    public String getCateNameByCateId(Integer cateId, String delimiter) {
        List<String> nameList = new ArrayList<>();
        while (cateId > 0) {
            ItemCate cateInfo = itemCateMapper.selectById(cateId);
            if (cateInfo != null) {
                nameList.add(cateInfo.getName());
                cateId = cateInfo.getPid();
            } else {
                cateId = 0;
            }
        }
        // 使用集合工具实现数组翻转
        Collections.reverse(nameList);
        return org.apache.commons.lang3.StringUtils.join(nameList.toArray(), delimiter);
    }

    /**
     * 根据站点ID获取栏目列表
     *
     * @param itemId 站点ID
     * @param pid    栏目ID
     * @return
     */
    @Override
    public List<Map<String, Object>> getItemCateListByItemId(Integer itemId, Integer pid) {
        QueryWrapper<ItemCate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("sort");
        List<ItemCate> itemCateList = itemCateMapper.selectList(queryWrapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!itemCateList.isEmpty()) {
            itemCateList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("name", item.getName());
                mapList.add(map);

                // 获取子级
                List<Map<String, Object>> childrenList = this.getItemCateListByItemId(itemId, item.getId());
                if (!childrenList.isEmpty()) {
                    childrenList.forEach(subItem -> {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("id", subItem.get("id"));
                        map1.put("name", "|--" + subItem.get("name"));
                        mapList.add(map1);
                    });
                }

            });
        }
        return mapList;
    }
}