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
import me.arnu.system.constant.MessageTemplateConstant;
import me.arnu.system.entity.MessageTemplate;
import me.arnu.system.mapper.MessageTemplateMapper;
import me.arnu.system.query.MessageTemplateQuery;
import me.arnu.system.service.IMessageTemplateService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.MessageTemplateListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息模板 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-04
 */
@Service
public class MessageTemplateServiceImpl extends BaseServiceImpl<MessageTemplateMapper, MessageTemplate> implements IMessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        MessageTemplateQuery messageTemplateQuery = (MessageTemplateQuery) query;
        // 查询条件
        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        // 模板标题
        if (!StringUtils.isEmpty(messageTemplateQuery.getTitle())) {
            queryWrapper.like("title", messageTemplateQuery.getTitle());
        }
        // 模板类型：1系统模板 2短信模板 3邮件模板 4微信模板 5推送模板
        if (messageTemplateQuery.getType() != null && messageTemplateQuery.getType() > 0) {
            queryWrapper.eq("type", messageTemplateQuery.getType());
        }
        // 状态：1在用 2停用
        if (messageTemplateQuery.getStatus() != null && messageTemplateQuery.getStatus() > 0) {
            queryWrapper.eq("status", messageTemplateQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<MessageTemplate> page = new Page<>(messageTemplateQuery.getPage(), messageTemplateQuery.getLimit());
        IPage<MessageTemplate> data = messageTemplateMapper.selectPage(page, queryWrapper);
        List<MessageTemplate> messageTemplateList = data.getRecords();
        List<MessageTemplateListVo> messageTemplateListVoList = new ArrayList<>();
        if (!messageTemplateList.isEmpty()) {
            messageTemplateList.forEach(item -> {
                MessageTemplateListVo messageTemplateListVo = new MessageTemplateListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, messageTemplateListVo);
                // 模板类型描述
                if (messageTemplateListVo.getType() != null && messageTemplateListVo.getType() > 0) {
                    messageTemplateListVo.setTypeName(MessageTemplateConstant.MESSAGETEMPLATE_TYPE_LIST.get(messageTemplateListVo.getType()));
                }
                // 状态描述
                if (messageTemplateListVo.getStatus() != null && messageTemplateListVo.getStatus() > 0) {
                    messageTemplateListVo.setStatusName(MessageTemplateConstant.MESSAGETEMPLATE_STATUS_LIST.get(messageTemplateListVo.getStatus()));
                }
                // 添加人名称
                if (messageTemplateListVo.getCreateUser() > 0) {
                    messageTemplateListVo.setCreateUserName(UserUtils.getName((messageTemplateListVo.getCreateUser())));
                }
                // 更新人名称
                if (messageTemplateListVo.getUpdateUser() > 0) {
                    messageTemplateListVo.setUpdateUserName(UserUtils.getName((messageTemplateListVo.getUpdateUser())));
                }
                messageTemplateListVoList.add(messageTemplateListVo);
            });
        }
        return JsonResult.success("操作成功", messageTemplateListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        MessageTemplate entity = (MessageTemplate) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(MessageTemplate entity) {
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
        MessageTemplate entity = this.getById(id);
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
    public JsonResult setStatus(MessageTemplate entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}