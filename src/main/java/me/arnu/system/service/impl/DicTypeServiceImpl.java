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
import me.arnu.system.entity.DicType;
import me.arnu.system.mapper.DicTypeMapper;
import me.arnu.system.query.DicTypeQuery;
import me.arnu.system.service.IDicTypeService;
import me.arnu.system.utils.UserUtils;
import me.arnu.system.vo.DicTypeListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-04-20
 */
@Service
public class DicTypeServiceImpl extends BaseServiceImpl<DicTypeMapper, DicType> implements IDicTypeService {

    @Autowired
    private DicTypeMapper dicTypeMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        DicTypeQuery dicTypeQuery = (DicTypeQuery) query;
        // 查询条件
        QueryWrapper<DicType> queryWrapper = new QueryWrapper<>();
        // 字典名称
        if (!StringUtils.isEmpty(dicTypeQuery.getName())) {
            queryWrapper.like("name", dicTypeQuery.getName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<DicType> page = new Page<>(dicTypeQuery.getPage(), dicTypeQuery.getLimit());
        IPage<DicType> data = dicTypeMapper.selectPage(page, queryWrapper);
        List<DicType> dicTypeList = data.getRecords();
        List<DicTypeListVo> dicTypeListVoList = new ArrayList<>();
        if (!dicTypeList.isEmpty()) {
            dicTypeList.forEach(item -> {
                DicTypeListVo dicTypeListVo = new DicTypeListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, dicTypeListVo);
                // 添加人名称
                if (dicTypeListVo.getCreateUser() > 0) {
                    dicTypeListVo.setCreateUserName(UserUtils.getName((dicTypeListVo.getCreateUser())));
                }
                // 更新人名称
                if (dicTypeListVo.getUpdateUser() > 0) {
                    dicTypeListVo.setUpdateUserName(UserUtils.getName((dicTypeListVo.getUpdateUser())));
                }
                dicTypeListVoList.add(dicTypeListVo);
            });
        }
        return JsonResult.success("操作成功", dicTypeListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        DicType entity = (DicType) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(DicType entity) {
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
        DicType entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}