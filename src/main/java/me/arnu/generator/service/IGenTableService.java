/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.service;

import me.arnu.common.utils.JsonResult;
import me.arnu.generator.entity.GenTable;
import me.arnu.common.common.IBaseService;
import me.arnu.generator.query.GenTableQuery;

import java.util.List;

/**
 * <p>
 * 代码生成 服务类
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
public interface IGenTableService extends IBaseService<GenTable> {

    /**
     * 获取数据库表列表
     *
     * @param query 查询条件
     * @return
     */
    JsonResult genDbTableList(GenTableQuery query);

    /**
     * 查询据库列表
     *
     * @param tableNames 表数组
     * @return
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    void importGenTable(List<GenTable> tableList);

    /**
     * 查询业务表信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    GenTable selectGenTableById(Integer id);

    /**
     * 生成代码
     *
     * @param tableNames 数据表
     * @return
     */
    JsonResult generatorCode(String[] tableNames);

}