/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import me.arnu.generator.constant.GenConstants;
import me.arnu.generator.entity.GenTable;
import me.arnu.generator.entity.GenTableColumn;
import me.arnu.generator.mapper.GenTableColumnMapper;
import me.arnu.generator.mapper.GenTableMapper;
import me.arnu.generator.query.GenTableQuery;
import me.arnu.generator.service.IGenTableService;
import me.arnu.generator.utils.CodeGenerateUtils;
import me.arnu.generator.utils.GenUtils;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.system.utils.ShiroUtils;
import me.arnu.system.utils.UserUtils;
import me.arnu.generator.vo.GenTableListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代码生成 服务实现类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
@Service
public class GenTableServiceImpl extends BaseServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private CodeGenerateUtils codeGenerateUtils;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        GenTableQuery genTableQuery = (GenTableQuery) query;
        // 查询条件
        QueryWrapper<GenTable> queryWrapper = new QueryWrapper<>();
        // 数据表名
        if (!StringUtils.isEmpty(genTableQuery.getTableName())) {
            queryWrapper.like("table_name", genTableQuery.getTableName());
        }
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<GenTable> page = new Page<>(genTableQuery.getPage(), genTableQuery.getLimit());
        IPage<GenTable> data = genTableMapper.selectPage(page, queryWrapper);
        List<GenTable> genTableList = data.getRecords();
        List<GenTableListVo> genTableListVoList = new ArrayList<>();
        if (!genTableList.isEmpty()) {
            genTableList.forEach(item -> {
                GenTableListVo genTableListVo = new GenTableListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, genTableListVo);
                // 添加人名称
                if (genTableListVo.getCreateUser() != null && genTableListVo.getCreateUser() > 0) {
                    genTableListVo.setCreateUserName(UserUtils.getName((genTableListVo.getCreateUser())));
                }
                // 更新人名称
                if (genTableListVo.getUpdateUser() != null && genTableListVo.getUpdateUser() > 0) {
                    genTableListVo.setUpdateUserName(UserUtils.getName((genTableListVo.getUpdateUser())));
                }
                genTableListVoList.add(genTableListVo);
            });
        }
        return JsonResult.success("操作成功", genTableListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        GenTable entity = (GenTable) super.getInfo(id);
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(GenTable entity) {
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
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (id == null || id == 0) {
            return JsonResult.error("记录ID不能为空");
        }
        GenTable entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        Integer result = genTableMapper.deleteById(id);
        if (result == 0) {
            return JsonResult.error();
        }
        // 删除附表信息
        Integer result2 = genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumn>()
                .eq(GenTableColumn::getTableId, id));
        if (result2 == 0) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 批量删除
     *
     * @param ids 记录ID
     * @return
     */
    @Override
    public JsonResult deleteByIds(String ids) {
        if (CommonConfig.appDebug) {
            return JsonResult.error("演示环境禁止操作");
        }
        if (StringUtils.isEmpty(ids)) {
            return JsonResult.error("记录ID不能为空");
        }
        // 计数器
        Integer totalNum = 0;
        String[] strings = ids.split(",");
        for (String string : strings) {
            GenTable entity = this.getById(string);
            if (entity == null) {
                continue;
            }
            Integer result = genTableMapper.deleteById(string);
            if (result == 0) {
                continue;
            }
            // 删除附表信息
            Integer result2 = genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumn>()
                    .eq(GenTableColumn::getTableId, string));
            if (result2 == 0) {
                continue;
            }
            totalNum++;
        }
        return JsonResult.success(String.format("本次共删除表【%s】张", totalNum));
    }

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult genDbTableList(GenTableQuery query) {
        query.setTablePrefix(codeGenerateUtils.getTablePrefix());
        List<GenTable> genTableList = genTableMapper.genDbTableList(query);
        return JsonResult.success("操作成功", genTableList);
    }

    /**
     * 查询数据库列表
     *
     * @param tableNames 表数组
     * @return
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames) {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Transactional
    @Override
    public void importGenTable(List<GenTable> tableList) {
        String operName = ShiroUtils.getUserInfo().getRealname();
        for (GenTable table : tableList) {
            try {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            } catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    /**
     * 获取业务表信息
     *
     * @param id 业务ID
     * @return
     */
    @Override
    public GenTable selectGenTableById(Integer id) {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj)) {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
        }
    }

    /**
     * 生成代码
     *
     * @param tableNames 数据表
     * @return
     */
    @Override
    public JsonResult generatorCode(String[] tableNames) {
        Integer totalNum = 0;
        for (String tableName : tableNames) {
            // 查询表信息
            GenTable tableInfo = genTableMapper.selectGenTableByName(tableName);
            try {
                // 生成文件
                codeGenerateUtils.setAuthor(tableInfo.getFunctionAuthor());
                codeGenerateUtils.setAutoRemovePre(true);
                codeGenerateUtils.setPackageName(tableInfo.getPackageName());
                codeGenerateUtils.setModuleName(tableInfo.getModuleName());
                codeGenerateUtils.setTablePrefix(tableInfo.getTablePrefix());
                codeGenerateUtils.generateFile(tableInfo.getTableName(), tableInfo.getFunctionName());
                totalNum++;
            } catch (Exception e) {

            }
        }
        return JsonResult.success(String.format("本地共生成【%s】个模块", totalNum));
    }
}