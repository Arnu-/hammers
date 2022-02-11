/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import me.arnu.common.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 代码生成列
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_gen_table_column")
public class GenTableColumn extends BaseEntity {

    /**
     * 归属表编号
     */
    private Integer tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键：1是 2否
     */
    private Integer isPk;

    /**
     * 是否自增：1是 2否
     */
    private Integer isIncrement;

    /**
     * 是否必填：1是 2否
     */
    private Integer isRequired;

    /**
     * 是否为插入字段：1是 2否
     */
    private Integer isInsert;

    /**
     * 是否编辑字段：1是 2否
     */
    private Integer isEdit;

    /**
     * 是否列表字段：1是 2否
     */
    private Integer isList;

    /**
     * 是否查询字段：1是 2否
     */
    private Integer isQuery;

    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    private String queryType;

    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

}