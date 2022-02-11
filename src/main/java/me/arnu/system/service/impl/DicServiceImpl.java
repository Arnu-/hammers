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
import me.arnu.system.constant.DicConstant;
import me.arnu.system.entity.Dic;
import me.arnu.system.mapper.DicMapper;
import me.arnu.system.query.DicQuery;
import me.arnu.system.service.IDicService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.DicListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class DicServiceImpl extends BaseServiceImpl<DicMapper, Dic> implements IDicService {

    @Autowired
    private DicMapper dicMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DicQuery dicQuery = (DicQuery) query;
        // 查询条件
        QueryWrapper<Dic> queryWrapper = new QueryWrapper<>();
        // 字典名称
        if (!StringUtils.isEmpty(dicQuery.getTitle())) {
            queryWrapper.like("title", dicQuery.getTitle());
        }
        // 状态：1在用 2停用
        if (dicQuery.getStatus() != null) {
            queryWrapper.eq("status", dicQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Dic> page = new Page<>(dicQuery.getPage(), dicQuery.getLimit());
        IPage<Dic> data = dicMapper.selectPage(page, queryWrapper);
        List<Dic> dicList = data.getRecords();
        List<DicListVo> dicListVoList = new ArrayList<>();
        if (!dicList.isEmpty()) {
            dicList.forEach(item -> {
                DicListVo dicListVo = new DicListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, dicListVo);
                // 状态描述
                if (dicListVo.getStatus() != null && dicListVo.getStatus() > 0) {
                    dicListVo.setStatusName(DicConstant.DIC_STATUS_LIST.get(dicListVo.getStatus()));
                }
                // 添加人名称
                if (dicListVo.getCreateUser() > 0) {
                    dicListVo.setCreateUserName(UserUtils.getName((dicListVo.getCreateUser())));
                }
                // 更新人名称
                if (dicListVo.getUpdateUser() > 0) {
                    dicListVo.setUpdateUserName(UserUtils.getName((dicListVo.getUpdateUser())));
                }
                dicListVoList.add(dicListVo);
            });
        }
        return JsonResult.success("操作成功", dicListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Dic entity = (Dic) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Dic entity) {
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
        Dic entity = this.getById(id);
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
    public JsonResult setStatus(Dic entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}