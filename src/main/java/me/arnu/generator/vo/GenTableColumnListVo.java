/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 代码生成列列表Vo
 * </p>
 *
 * @author Arnu
 * @since 2020-05-10
 */
@Data
public class GenTableColumnListVo {

    /**
     * 代码生成列ID
     */
    private Integer id;

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
     * 是否主键描述
     */
    private String isPkName;

    /**
     * 是否自增：1是 2否
     */
    private Integer isIncrement;

    /**
     * 是否自增描述
     */
    private String isIncrementName;

    /**
     * 是否必填：1是 2否
     */
    private Integer isRequired;

    /**
     * 是否必填描述
     */
    private String isRequiredName;

    /**
     * 是否为插入字段：1是 2否
     */
    private Integer isInsert;

    /**
     * 是否为插入字段描述
     */
    private String isInsertName;

    /**
     * 是否编辑字段：1是 2否
     */
    private Integer isEdit;

    /**
     * 是否编辑字段描述
     */
    private String isEditName;

    /**
     * 是否列表字段：1是 2否
     */
    private Integer isList;

    /**
     * 是否列表字段描述
     */
    private String isListName;

    /**
     * 是否查询字段：1是 2否
     */
    private Integer isQuery;

    /**
     * 是否查询字段描述
     */
    private String isQueryName;

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

    /**
     * 添加人
     */
    private Integer createUser;

    /**
     * 添加人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

}