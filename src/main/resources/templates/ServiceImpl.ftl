/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package ${packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.arnu.common.common.BaseQuery;
import me.arnu.system.common.BaseServiceImpl;
import me.arnu.common.config.CommonConfig;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.JsonResult;
import me.arnu.common.utils.StringUtils;
import ${packageName}.constant.${entityName}Constant;
import ${packageName}.entity.${entityName};
import ${packageName}.mapper.${entityName}Mapper;
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.I${entityName}Service;
import me.arnu.system.utils.UserUtils;
import ${packageName}.vo.${entityName}ListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ${tableAnnotation} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}Mapper, ${entityName}> implements I${entityName}Service {

    @Autowired
    private ${entityName}Mapper ${entityName?uncap_first}Mapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public JsonResult getList(BaseQuery query) {
        ${entityName}Query ${entityName?uncap_first}Query = (${entityName}Query) query;
        // 查询条件
        QueryWrapper<${entityName}> queryWrapper = new QueryWrapper<>();
<#if model_column?exists>
    <#list model_column as model>
        <#if model.columnName = 'name'>
        // ${model.columnComment}
        if (!StringUtils.isEmpty(${entityName?uncap_first}Query.getName())) {
            queryWrapper.like("name", ${entityName?uncap_first}Query.getName());
        }
        </#if>
        <#if model.columnName = 'title'>
        // ${model.columnComment}
        if (!StringUtils.isEmpty(${entityName?uncap_first}Query.getTitle())) {
            queryWrapper.like("title", ${entityName?uncap_first}Query.getTitle());
        }
        </#if>
        <#if model.columnName = 'mobile'>
        // ${model.columnComment}
        if (!StringUtils.isEmpty(${entityName?uncap_first}Query.getMobile())) {
            queryWrapper.like("mobile", ${entityName?uncap_first}Query.getMobile());
        }
        </#if>
        <#if model.hasColumnCommentValue = true>
        // ${model.columnComment}
        <#if model.columnNumberValue = true>
        if (${entityName?uncap_first}Query.get${model.changeColumnName}() != null && ${entityName?uncap_first}Query.get${model.changeColumnName}() > 0) {
            queryWrapper.eq("${model.columnName}", ${entityName?uncap_first}Query.get${model.changeColumnName}());
        }
        <#else>
        if (!StringUtils.isEmpty(${entityName?uncap_first}Query.get${model.changeColumnName}())) {
            queryWrapper.eq("${model.columnName}", ${entityName?uncap_first}Query.get${model.changeColumnName}());
        }
        </#if>
        </#if>
    </#list>
</#if>
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByDesc("id");

        // 查询数据
        IPage<${entityName}> page = new Page<>(${entityName?uncap_first}Query.getPage(), ${entityName?uncap_first}Query.getLimit());
        IPage<${entityName}> data = ${entityName?uncap_first}Mapper.selectPage(page, queryWrapper);
        List<${entityName}> ${entityName?uncap_first}List = data.getRecords();
        List<${entityName}ListVo> ${entityName?uncap_first}ListVoList = new ArrayList<>();
        if (!${entityName?uncap_first}List.isEmpty()) {
            ${entityName?uncap_first}List.forEach(item -> {
                ${entityName}ListVo ${entityName?uncap_first}ListVo = new ${entityName}ListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, ${entityName?uncap_first}ListVo);
    <#if model_column?exists>
        <#list model_column as model>
            <#if model.hasColumnCommentValue = true>
                // ${model.columnCommentName}描述
                <#if model.columnNumberValue = true>
                if (${entityName?uncap_first}ListVo.get${model.changeColumnName}() != null && ${entityName?uncap_first}ListVo.get${model.changeColumnName}() > 0) {
                    ${entityName?uncap_first}ListVo.set${model.changeColumnName}Name(${entityName}Constant.${entityName?upper_case}_${model.changeColumnName?upper_case}_LIST.get(${entityName?uncap_first}ListVo.get${model.changeColumnName}()));
                }
                <#else>
                if (!StringUtils.isEmpty(${entityName?uncap_first}ListVo.get${model.changeColumnName}())) {
                    ${entityName?uncap_first}ListVo.set${model.changeColumnName}Name(${entityName}Constant.${entityName?upper_case}_${model.changeColumnName?upper_case}_LIST.get(${entityName?uncap_first}ListVo.get${model.changeColumnName}()));
                }
                </#if>
            </#if>
           <#if model.columnImage == true>
                // ${model.columnComment}地址
                if (!StringUtils.isEmpty(${entityName?uncap_first}ListVo.get${model.changeColumnName}())) {
                    ${entityName?uncap_first}ListVo.set${model.changeColumnName}Url(CommonUtils.getImageURL(${entityName?uncap_first}ListVo.get${model.changeColumnName}()));
                }
            </#if>
            <#if (model.changeColumnName?uncap_first = "createUser" || model.changeColumnName?uncap_first = "updateUser")>
                // ${model.columnComment}名称
                if (${entityName?uncap_first}ListVo.get${model.changeColumnName}() != null && ${entityName?uncap_first}ListVo.get${model.changeColumnName}() > 0) {
                    ${entityName?uncap_first}ListVo.set${model.changeColumnName}Name(UserUtils.getName((${entityName?uncap_first}ListVo.get${model.changeColumnName}())));
                }
            </#if>
        </#list>
    </#if>
                ${entityName?uncap_first}ListVoList.add(${entityName?uncap_first}ListVo);
            });
        }
        return JsonResult.success("操作成功", ${entityName?uncap_first}ListVoList, data.getTotal());
    }

    /**
     * 获取记录详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        ${entityName} entity = (${entityName}) super.getInfo(id);
    <#if model_column?exists>
        <#list model_column as model>
        <#if model.columnImage == true>
        // ${model.columnComment}解析
        if (!StringUtils.isEmpty(entity.get${model.changeColumnName}())) {
            entity.set${model.changeColumnName}(CommonUtils.getImageURL(entity.get${model.changeColumnName}()));
        }
        </#if>
        </#list>
    </#if>
        return entity;
    }

    /**
     * 添加或编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult edit(${entityName} entity) {
<#if model_column?exists>
    <#list model_column as model>
      <#if model.columnImage == true>
        // ${model.columnComment}
        if (entity.get${model.changeColumnName}().contains(CommonConfig.imageURL)) {
            entity.set${model.changeColumnName}(entity.get${model.changeColumnName}().replaceAll(CommonConfig.imageURL, ""));
        }
        </#if>
    </#list>
</#if>
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
        ${entityName} entity = this.getById(id);
        if (entity == null) {
            return JsonResult.error("记录不存在");
        }
        return super.delete(entity);
    }

<#if model_column?exists>
    <#list model_column as model>
    <#if model.changeColumnName?uncap_first = 'status'>
    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public JsonResult setStatus(${entityName} entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return JsonResult.error("记录ID不能为空");
        }
        if (entity.getStatus() == null) {
            return JsonResult.error("记录状态不能为空");
        }
        return super.setStatus(entity);
    }
    </#if>
    </#list>
</#if>
}