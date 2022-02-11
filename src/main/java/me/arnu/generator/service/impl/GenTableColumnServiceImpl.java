/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.utils.JsonResult;
import me.arnu.generator.constant.GenTableColumnConstant;
import me.arnu.generator.entity.GenTableColumn;
import me.arnu.generator.mapper.GenTableColumnMapper;
import me.arnu.generator.query.GenTableColumnQuery;
import me.arnu.generator.service.IGenTableColumnService;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.utils.UserUtils;
import me.arnu.generator.vo.GenTableColumnListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代码生成列 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        GenTableColumnQuery genTableColumnQuery = (GenTableColumnQuery) query;
        // 查询条件
        QueryWrapper<GenTableColumn> queryWrapper = new QueryWrapper<>();
        // 是否主键：1是 2否
        if (genTableColumnQuery.getIsPk() != null && genTableColumnQuery.getIsPk() > 0) {
            queryWrapper.eq("is_pk", genTableColumnQuery.getIsPk());
        }
        // 是否自增：1是 2否
        if (genTableColumnQuery.getIsIncrement() != null && genTableColumnQuery.getIsIncrement() > 0) {
            queryWrapper.eq("is_increment", genTableColumnQuery.getIsIncrement());
        }
        // 是否必填：1是 2否
        if (genTableColumnQuery.getIsRequired() != null && genTableColumnQuery.getIsRequired() > 0) {
            queryWrapper.eq("is_required", genTableColumnQuery.getIsRequired());
        }
        // 是否为插入字段：1是 2否
        if (genTableColumnQuery.getIsInsert() != null && genTableColumnQuery.getIsInsert() > 0) {
            queryWrapper.eq("is_insert", genTableColumnQuery.getIsInsert());
        }
        // 是否编辑字段：1是 2否
        if (genTableColumnQuery.getIsEdit() != null && genTableColumnQuery.getIsEdit() > 0) {
            queryWrapper.eq("is_edit", genTableColumnQuery.getIsEdit());
        }
        // 是否列表字段：1是 2否
        if (genTableColumnQuery.getIsList() != null && genTableColumnQuery.getIsList() > 0) {
            queryWrapper.eq("is_list", genTableColumnQuery.getIsList());
        }
        // 是否查询字段：1是 2否
        if (genTableColumnQuery.getIsQuery() != null && genTableColumnQuery.getIsQuery() > 0) {
            queryWrapper.eq("is_query", genTableColumnQuery.getIsQuery());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<GenTableColumn> page = new Page<>(genTableColumnQuery.getPage(), genTableColumnQuery.getLimit());
        IPage<GenTableColumn> data = genTableColumnMapper.selectPage(page, queryWrapper);
        List<GenTableColumn> genTableColumnList = data.getRecords();
        List<GenTableColumnListVo> genTableColumnListVoList = new ArrayList<>();
        if (!genTableColumnList.isEmpty()) {
            genTableColumnList.forEach(item -> {
                GenTableColumnListVo genTableColumnListVo = new GenTableColumnListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, genTableColumnListVo);
                // 是否主键描述
                if (genTableColumnListVo.getIsPk() != null && genTableColumnListVo.getIsPk() > 0) {
                    genTableColumnListVo.setIsPkName(GenTableColumnConstant.GENTABLECOLUMN_ISPK_LIST.get(genTableColumnListVo.getIsPk()));
                }
                // 是否自增描述
                if (genTableColumnListVo.getIsIncrement() != null && genTableColumnListVo.getIsIncrement() > 0) {
                    genTableColumnListVo.setIsIncrementName(GenTableColumnConstant.GENTABLECOLUMN_ISINCREMENT_LIST.get(genTableColumnListVo.getIsIncrement()));
                }
                // 是否必填描述
                if (genTableColumnListVo.getIsRequired() != null && genTableColumnListVo.getIsRequired() > 0) {
                    genTableColumnListVo.setIsRequiredName(GenTableColumnConstant.GENTABLECOLUMN_ISREQUIRED_LIST.get(genTableColumnListVo.getIsRequired()));
                }
                // 是否为插入字段描述
                if (genTableColumnListVo.getIsInsert() != null && genTableColumnListVo.getIsInsert() > 0) {
                    genTableColumnListVo.setIsInsertName(GenTableColumnConstant.GENTABLECOLUMN_ISINSERT_LIST.get(genTableColumnListVo.getIsInsert()));
                }
                // 是否编辑字段描述
                if (genTableColumnListVo.getIsEdit() != null && genTableColumnListVo.getIsEdit() > 0) {
                    genTableColumnListVo.setIsEditName(GenTableColumnConstant.GENTABLECOLUMN_ISEDIT_LIST.get(genTableColumnListVo.getIsEdit()));
                }
                // 是否列表字段描述
                if (genTableColumnListVo.getIsList() != null && genTableColumnListVo.getIsList() > 0) {
                    genTableColumnListVo.setIsListName(GenTableColumnConstant.GENTABLECOLUMN_ISLIST_LIST.get(genTableColumnListVo.getIsList()));
                }
                // 是否查询字段描述
                if (genTableColumnListVo.getIsQuery() != null && genTableColumnListVo.getIsQuery() > 0) {
                    genTableColumnListVo.setIsQueryName(GenTableColumnConstant.GENTABLECOLUMN_ISQUERY_LIST.get(genTableColumnListVo.getIsQuery()));
                }
                // 添加人名称
                if (genTableColumnListVo.getCreateUser() != null && genTableColumnListVo.getCreateUser() > 0) {
                    genTableColumnListVo.setCreateUserName(UserUtils.getName((genTableColumnListVo.getCreateUser())));
                }
                // 更新人名称
                if (genTableColumnListVo.getUpdateUser() != null && genTableColumnListVo.getUpdateUser() > 0) {
                    genTableColumnListVo.setUpdateUserName(UserUtils.getName((genTableColumnListVo.getUpdateUser())));
                }
                genTableColumnListVoList.add(genTableColumnListVo);
            });
        }
        return JsonResult.success("操作成功", genTableColumnListVoList);
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        GenTableColumn entity = (GenTableColumn) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(GenTableColumn entity) {
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
        GenTableColumn entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

}