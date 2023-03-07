package me.arnu.springboot.thyemleaf.dialect.processor;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

// @Component
public class SwitchCheckElementProcessor extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "switchcheck";

    // 优先级
    private static final int PRECEDENCE = 10000;

    public SwitchCheckElementProcessor(String dialectPrefix) {
        super(
                // 模板类型为HTML
                TemplateMode.HTML,
                // 标签方言前缀
                dialectPrefix,
                // 标签名称
                TAG_NAME,
                // 将标签前缀应用于标签名称
                true,
                // 无属性名称：将通过标签名称匹配
                null,
                // 没有要应用于属性名称的前缀
                false,
                // 优先级
                PRECEDENCE
        );
    }

    /**
     * 处理自定义标签 DOM 结构
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     */
    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        // data="显示|不显示"
        String result = "<input name=\"#ELE_NAME#\"id=\"#ELE_ID#\"value=\"#VALUE#\"lay-skin=\"switch\"" +
                "lay-filter=\"#ELE_NAME#\"lay-text=\"#TEXT#\"type=\"checkbox\"#CHECKED#>" +
                "<script th:inline=\"javascript\"type=\"text/javascript\">" +
                "layui.use(['form'],function(){var form=layui.form,$=layui.$;" +
                "$(\"##ELE_ID#\").attr('type','hidden').val(#VALUE#);" +
                "form.on('switch(#ELE_ID#)',function(data){" +
                "$(data.elem).attr('type','hidden').val(this.checked?1:2)})});</script>";

//        //select的name属性
        String name = iProcessableElementTag.getAttributeValue("name");
        String data = iProcessableElementTag.getAttributeValue("data");
        String value = iProcessableElementTag.getAttributeValue("value");
        result = result.replaceAll("#ELE_NAME#", name)
                .replaceAll("#ELE_ID#", name)
                .replaceAll("#TEXT#", data)
                .replaceAll("#VALUE#", value);
        if(value.equals("1")){
            result = result.replaceAll("#CHECKED#", "checked=\"\"");
        }else{
            result = result.replaceAll("#CHECKED#", "");
        }
//        StringBuffer result = new StringBuffer("<select><option value=''>--请选择--</option></select>");
//        if(validParamIsNotNull(id,name,colText, colVal, tableName)) {
//            //最终拼接的sql语句，这里可以使用jdbc来查询
//            String selectSql = "select " + colVal + "," + colText + " from " + tableName + options;
//            List<Map<String, Object>> mapList = jdbcOperations.queryForList(selectSql);
//            if (mapList != null && mapList.size() >= 0) {
//                result = new StringBuffer("<select ");
//                result.append(" id='").append(id).append("'");
//                result.append(" name='").append(name).append("'");
//                if(StringUtils.isNotBlank(otherAttrs)) {
//                    result.append(" " + otherAttrs +" ");
//                }
//                result.append(">");
//                if(StringUtils.isBlank(selectFlag) || (StringUtils.isNotBlank(selectFlag) && "true".equalsIgnoreCase(selectFlag))) {
//                    result.append("<option value=''>--请选择--</option>");
//                }
//                for (Map<String, Object> vo : mapList) {
//                    String dictValue = MapUtils.getString(vo, colVal);
//                    String dictName = MapUtils.getString(vo, colText);
//                    if (value.equals(dictValue)) {
//                        result.append("<option value='").append(dictValue).append("' selected>").append(dictName).append("</option>");
//                    } else {
//                        result.append("<option value='").append(dictValue).append("'>").append(dictName).append("</option>");
//                    }
//                }
//                result.append("</select>");
//            }
//        }
        // 创建将替换自定义标签的 DOM 结构
        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();
        // 这里是将字典的内容拼装成一个下拉框
        model.add(modelFactory.createText(result));
        // 利用引擎替换整合标签
        iElementTagStructureHandler.replaceWith(model, false);
    }

    /*
    <input name="status" id="status" th:value="1" lay-skin="switch" lay-filter="status" lay-text="正常|禁用" type="checkbox"checked=""><script th:inline="javascript" type="text/javascript">layui.use(['form'], function(){
	var form = layui.form,
		$ = layui.$;

    if (1 == 1) {
        $("#status").attr('type', 'hidden').val(1);
    } else {
        $("#status").attr('type', 'hidden').val(2);
    }
	form.on('switch(status)', function(data) {
		console.log('switch开关选择状态：'+this.checked);
	    $(data.elem).attr('type', 'hidden').val(this.checked ? 1 : 2);
	});
});</script>

     */

    /**
     * 验证参数是否不为空
     *
     * @param params
     * @return true, 不为空；false，为空
     */
    public static Boolean validParamIsNotNull(String... params) {
        for (String param : params) {
            if (StringUtils.isBlank(param)) {
                return false;
            }
        }
        return true;
    }
}
