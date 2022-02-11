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
import me.arnu.system.constant.PositionConstant;
import me.arnu.system.entity.Position;
import me.arnu.system.mapper.PositionMapper;
import me.arnu.system.query.PositionQuery;
import me.arnu.system.service.IPositionService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.PositionListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 岗位 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class PositionServiceImpl extends BaseServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        PositionQuery positionQuery = (PositionQuery) query;
        // 查询条件
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        // 岗位名称
        if (!StringUtils.isEmpty(positionQuery.getName())) {
            queryWrapper.like("name", positionQuery.getName());
        }
        // 状态：1正常 2停用
        if (positionQuery.getStatus() != null) {
            queryWrapper.eq("status", positionQuery.getStatus());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<Position> page = new Page<>(positionQuery.getPage(), positionQuery.getLimit());
        IPage<Position> data = positionMapper.selectPage(page, queryWrapper);
        List<Position> positionList = data.getRecords();
        List<PositionListVo> positionListVoList = new ArrayList<>();
        if (!positionList.isEmpty()) {
            positionList.forEach(item -> {
                PositionListVo positionListVo = new PositionListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, positionListVo);
                // 状态描述
                if (positionListVo.getStatus() != null && positionListVo.getStatus() > 0) {
                    positionListVo.setStatusName(PositionConstant.POSITION_STATUS_LIST.get(positionListVo.getStatus()));
                }
                // 添加人名称
                if (positionListVo.getCreateUser() > 0) {
                    positionListVo.setCreateUserName(UserUtils.getName((positionListVo.getCreateUser())));
                }
                // 更新人名称
                if (positionListVo.getUpdateUser() > 0) {
                    positionListVo.setUpdateUserName(UserUtils.getName((positionListVo.getUpdateUser())));
                }
                positionListVoList.add(positionListVo);
            });
        }
        return JsonResult.success("操作成功", positionListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        Position entity = (Position) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(Position entity) {
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
        Position entity = this.getById(id);
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
    public JsonResult setStatus(Position entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
}