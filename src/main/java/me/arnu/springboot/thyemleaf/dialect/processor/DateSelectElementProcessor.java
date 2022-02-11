package me.arnu.springboot.thyemleaf.dialect.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class DateSelectElementProcessor extends AbstractElementTagProcessor {

    // 标签名
    private static final String TAG_NAME = "dateSelect";

    // 优先级
    private static final int PRECEDENCE = 10000;

    public DateSelectElementProcessor(String dialectPrefix) {
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

//        //select的name属性
        String name = iProcessableElementTag.getAttributeValue("name");
        // birthday|出生日期|date
        String[] vs = name.split("\\|");
        String eleName = vs[0];
        String cnName = vs[1];
        String type = vs[2];
        String value = "";
        if (iProcessableElementTag.hasAttribute("value")) {
            value = iProcessableElementTag.getAttributeValue("value");
        }
        String result = "<input name=\"#ELENAME#\" id=\"#ELENAME#\" value=\"#VALUE#\" lay-verify=\"#TYPE#\" placeholder=\"请选择#CNNAME#\" autocomplete=\"off\" class=\"layui-input date-icon\" type=\"text\"><script th:inline=\"javascript\" type=\"text/javascript\">layui.use(['func'], function () {\n" +
                "    var func = layui.func;\n" +
                "        func.initDate(['#ELENAME#|#TYPE#||'], function (value, date) {\n" +
                "            console.log(\"当前选择日期:\" + value);\n" +
                "            console.log(\"日期详细信息：\" + JSON.stringify(date));\n" +
                "        });});</script>";
        result = result.replaceAll("#ELENAME#", eleName)
                .replaceAll("#CNNAME#", cnName)
                .replaceAll("#TYPE#", type)
                .replaceAll("#VALUE#", value);

        // 创建将替换自定义标签的 DOM 结构
        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();
        // 这里是将字典的内容拼装成一个下拉框
        model.add(modelFactory.createText(result));
        // 利用引擎替换整合标签
        iElementTagStructureHandler.replaceWith(model, false);
    }
}
