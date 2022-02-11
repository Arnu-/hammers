/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.admin.constant.ArticleConstant;
import me.arnu.admin.entity.Article;
import me.arnu.admin.mapper.ArticleMapper;
import me.arnu.admin.query.ArticleQuery;
import me.arnu.admin.service.IArticleService;
import me.arnu.system.service.IItemCateService;
import me.arnu.system.utils.UserUtils;
import me.arnu.admin.vo.ArticleListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章管理 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-08-11
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
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
        ArticleQuery articleQuery = (ArticleQuery) query;
        // 查询条件
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        // 文章标题
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            queryWrapper.like("title", articleQuery.getTitle());
        }
        // 是否置顶：1已置顶 2未置顶
        if (articleQuery.getIsTop() != null && articleQuery.getIsTop() > 0) {
            queryWrapper.eq("is_top", articleQuery.getIsTop());
        }
        // 状态：1已审核 2待审核 3审核失败
        if (articleQuery.getStatus() != null && articleQuery.getStatus() > 0) {
            queryWrapper.eq("status", articleQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Article> page = new Page<>(articleQuery.getPage(), articleQuery.getLimit());
        IPage<Article> data = articleMapper.selectPage(page, queryWrapper);
        List<Article> articleList = data.getRecords();
        List<ArticleListVo> articleListVoList = new ArrayList<>();
        if (!articleList.isEmpty()) {
            articleList.forEach(item -> {
                ArticleListVo articleListVo = new ArticleListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, articleListVo);
                // 首张图片编号地址
                if (!StringUtils.isEmpty(articleListVo.getCover())) {
                    articleListVo.setCoverUrl(CommonUtils.getImageURL(articleListVo.getCover()));
                }
                // 栏目分类
                if (item.getCateId() != null && item.getCateId() > 0) {
                    articleListVo.setCateName(itemCateService.getCateNameByCateId(item.getCateId(), ">>"));
                }
                // 是否置顶描述
                if (articleListVo.getIsTop() != null && articleListVo.getIsTop() > 0) {
                    articleListVo.setIsTopName(ArticleConstant.ARTICLE_ISTOP_LIST.get(articleListVo.getIsTop()));
                }
                // 状态描述
                if (articleListVo.getStatus() != null && articleListVo.getStatus() > 0) {
                    articleListVo.setStatusName(ArticleConstant.ARTICLE_STATUS_LIST.get(articleListVo.getStatus()));
                }
                // 添加人名称
                if (articleListVo.getCreateUser() != null && articleListVo.getCreateUser() > 0) {
                    articleListVo.setCreateUserName(UserUtils.getName((articleListVo.getCreateUser())));
                }
                // 更新人名称
                if (articleListVo.getUpdateUser() != null && articleListVo.getUpdateUser() > 0) {
                    articleListVo.setUpdateUserName(UserUtils.getName((articleListVo.getUpdateUser())));
                }
                articleListVoList.add(articleListVo);
            });
        }
        return JsonResult.success("操作成功", articleListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Article entity = (Article) super.getInfo(id);
        // 首张图片编号解析
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
    public JsonResult edit(Article entity) {
        // 首张图片编号
        if (entity.getCover().contains(CommonConfig.imageURL)) {
            entity.setCover(entity.getCover().replaceAll(CommonConfig.imageURL, ""));
        }
        // 图集处理
        String[] stringsVal = entity.getImgs().split(",");
        List<String> stringList = new ArrayList<>();
        for (String s : stringsVal) {
            if (s.contains(CommonConfig.imageURL)) {
                stringList.add(s.replaceAll(CommonConfig.imageURL, ""));
            } else {
                // 已上传图片
                stringList.add(s.replaceAll(CommonConfig.imageURL, ""));
            }
        }
        // 设置图集
        entity.setImgs(org.apache.commons.lang3.StringUtils.join(stringList, ","));
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
        Article entity = this.getById(id);
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
    public JsonResult setStatus(Article entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}