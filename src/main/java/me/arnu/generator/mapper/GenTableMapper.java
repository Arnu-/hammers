/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.mapper;

import me.arnu.generator.entity.GenTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.arnu.generator.query.GenTableQuery;

import java.util.List;

/**
 * <p>
 * 代码生成 Mapper 接口
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 获取数据库表
     *
     * @param query 查询条件
     * @return
     */
    List<GenTable> genDbTableList(GenTableQuery query);

    /**
     * 根据表明获取数据库列表
     *
     * @param tableNames 数据库名
     * @return
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 插入数据表
     *
     * @param genTable 待生成数据表
     * @return
     */
    int insertGenTable(GenTable genTable);

    /**
     * 根据表名查询业务表
     *
     * @param tableName 表名
     * @return
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * 根据业务ID获取表信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Integer id);

}
