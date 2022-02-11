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
import me.arnu.system.constant.NoticeConstant;
import me.arnu.system.entity.Notice;
import me.arnu.system.mapper.NoticeMapper;
import me.arnu.system.query.NoticeQuery;
import me.arnu.system.service.INoticeService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.NoticeListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 通知公告 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        NoticeQuery noticeQuery = (NoticeQuery) query;
        // 查询条件
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        // 通知标题
        if (!StringUtils.isEmpty(noticeQuery.getTitle())) {
            queryWrapper.like("title", noticeQuery.getTitle());
        }
        // 通知来源：1云平台
        if (noticeQuery.getSource() != null && noticeQuery.getSource() > 0) {
            queryWrapper.eq("source", noticeQuery.getSource());
        }
        // 是否置顶：1已置顶 2未置顶
        if (noticeQuery.getIsTop() != null && noticeQuery.getIsTop() > 0) {
            queryWrapper.eq("is_top", noticeQuery.getIsTop());
        }
        // 发布状态：1草稿箱 2立即发布 3定时发布
        if (noticeQuery.getStatus() != null && noticeQuery.getStatus() > 0) {
            queryWrapper.eq("status", noticeQuery.getStatus());
        }
        // 推送状态：1已推送 2未推送
        if (noticeQuery.getIsSend() != null && noticeQuery.getIsSend() > 0) {
            queryWrapper.eq("is_send", noticeQuery.getIsSend());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Notice> page = new Page<>(noticeQuery.getPage(), noticeQuery.getLimit());
        IPage<Notice> data = noticeMapper.selectPage(page, queryWrapper);
        List<Notice> noticeList = data.getRecords();
        List<NoticeListVo> noticeListVoList = new ArrayList<>();
        if (!noticeList.isEmpty()) {
            noticeList.forEach(item -> {
                NoticeListVo noticeListVo = new NoticeListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, noticeListVo);
                // 通知来源描述
                if (noticeListVo.getSource() != null && noticeListVo.getSource() > 0) {
                    noticeListVo.setSourceName(NoticeConstant.NOTICE_SOURCE_LIST.get(noticeListVo.getSource()));
                }
                // 是否置顶描述
                if (noticeListVo.getIsTop() != null && noticeListVo.getIsTop() > 0) {
                    noticeListVo.setIsTopName(NoticeConstant.NOTICE_ISTOP_LIST.get(noticeListVo.getIsTop()));
                }
                // 发布状态描述
                if (noticeListVo.getStatus() != null && noticeListVo.getStatus() > 0) {
                    noticeListVo.setStatusName(NoticeConstant.NOTICE_STATUS_LIST.get(noticeListVo.getStatus()));
                }
                // 推送状态描述
                if (noticeListVo.getIsSend() != null && noticeListVo.getIsSend() > 0) {
                    noticeListVo.setIsSendName(NoticeConstant.NOTICE_ISSEND_LIST.get(noticeListVo.getIsSend()));
                }
                // 添加人名称
                if (noticeListVo.getCreateUser() > 0) {
                    noticeListVo.setCreateUserName(UserUtils.getName((noticeListVo.getCreateUser())));
                }
                // 更新人名称
                if (noticeListVo.getUpdateUser() > 0) {
                    noticeListVo.setUpdateUserName(UserUtils.getName((noticeListVo.getUpdateUser())));
                }
                noticeListVoList.add(noticeListVo);
            });
        }
        return JsonResult.success("操作成功", noticeListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Notice entity = (Notice) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Notice entity) {
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
        Notice entity = this.getById(id);
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
    public JsonResult setStatus(Notice entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}