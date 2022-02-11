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
import me.arnu.system.constant.LinkConstant;
import me.arnu.system.entity.Item;
import me.arnu.system.entity.Link;
import me.arnu.system.mapper.ItemMapper;
import me.arnu.system.mapper.LinkMapper;
import me.arnu.system.query.LinkQuery;
import me.arnu.system.service.IItemCateService;
import me.arnu.system.service.ILinkService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.LinkListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-03
 */
@Service
public class LinkServiceImpl extends BaseServiceImpl<LinkMapper, Link> implements ILinkService {

    @Autowired
    private LinkMapper linkMapper;
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
        LinkQuery linkQuery = (LinkQuery) query;
        // 查询条件
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        // 友链名称
        if (!StringUtils.isEmpty(linkQuery.getName())) {
            queryWrapper.like("name", linkQuery.getName());
        }
        // 类型：1友情链接 2合作伙伴
        if (linkQuery.getType() != null && linkQuery.getType() > 0) {
            queryWrapper.eq("type", linkQuery.getType());
        }
        // 平台：1PC站 2WAP站 3微信小程序 4APP应用
        if (linkQuery.getPlatform() != null && linkQuery.getPlatform() > 0) {
            queryWrapper.eq("platform", linkQuery.getPlatform());
        }
        // 友链形式：1文字链接 2图片链接
        if (linkQuery.getForm() != null && linkQuery.getForm() > 0) {
            queryWrapper.eq("form", linkQuery.getForm());
        }
        // 状态：1在用 2停用
        if (linkQuery.getStatus() != null && linkQuery.getStatus() > 0) {
            queryWrapper.eq("status", linkQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Link> page = new Page<>(linkQuery.getPage(), linkQuery.getLimit());
        IPage<Link> data = linkMapper.selectPage(page, queryWrapper);
        List<Link> linkList = data.getRecords();
        List<LinkListVo> linkListVoList = new ArrayList<>();
        if (!linkList.isEmpty()) {
            linkList.forEach(item -> {
                LinkListVo linkListVo = new LinkListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, linkListVo);
                // 类型描述
                if (linkListVo.getType() != null && linkListVo.getType() > 0) {
                    linkListVo.setTypeName(LinkConstant.LINK_TYPE_LIST.get(linkListVo.getType()));
                }
                // 平台描述
                if (linkListVo.getPlatform() != null && linkListVo.getPlatform() > 0) {
                    linkListVo.setPlatformName(LinkConstant.LINK_PLATFORM_LIST.get(linkListVo.getPlatform()));
                }
                // 友链形式描述
                if (linkListVo.getForm() != null && linkListVo.getForm() > 0) {
                    linkListVo.setFormName(LinkConstant.LINK_FORM_LIST.get(linkListVo.getForm()));
                }
                // 友链图片地址
                if (!StringUtils.isEmpty(linkListVo.getImage())) {
                    linkListVo.setImageUrl(CommonUtils.getImageURL(linkListVo.getImage()));
                }
                // 状态描述
                if (linkListVo.getStatus() != null && linkListVo.getStatus() > 0) {
                    linkListVo.setStatusName(LinkConstant.LINK_STATUS_LIST.get(linkListVo.getStatus()));
                }
                // 所属站点
                if (linkListVo.getItemId() != null && linkListVo.getItemId() > 0) {
                    Item itemInfo = itemMapper.selectById(linkListVo.getItemId());
                    if (itemInfo != null) {
                        linkListVo.setItemName(itemInfo.getName());
                    }
                }
                // 栏目
                String cateName = itemCateService.getCateNameByCateId(linkListVo.getCateId(), ">>");
                linkListVo.setCateName(cateName);
                // 添加人名称
                if (linkListVo.getCreateUser() > 0) {
                    linkListVo.setCreateUserName(UserUtils.getName((linkListVo.getCreateUser())));
                }
                // 更新人名称
                if (linkListVo.getUpdateUser() > 0) {
                    linkListVo.setUpdateUserName(UserUtils.getName((linkListVo.getUpdateUser())));
                }
                linkListVoList.add(linkListVo);
            });
        }
        return JsonResult.success("操作成功", linkListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Link entity = (Link) super.getInfo(id);
        // 友链图片解析
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
    public JsonResult edit(Link entity) {
        // 友链图片
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
        Link entity = this.getById(id);
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
    public JsonResult setStatus(Link entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}