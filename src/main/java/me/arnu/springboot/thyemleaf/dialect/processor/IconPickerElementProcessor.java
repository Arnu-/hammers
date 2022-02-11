package me.arnu.springboot.thyemleaf.dialect.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class IconPickerElementProcessor extends AbstractElementTagProcessor {

    // 标签名
    private static final String TAG_NAME = "iconPicker";

    // 优先级
    private static final int PRECEDENCE = 10000;

    public IconPickerElementProcessor(String dialectPrefix) {
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

        String name = iProcessableElementTag.getAttributeValue("name");
        String value = iProcessableElementTag.getAttributeValue("value");
        String result = "<input type=\"hidden\" id=\"#ELE_ID#\" name=\"#ELE_NAME#\" lay-filter=\"#ELE_NAME#\" value=\"#VALUE#\"><style type=\"text/css\">.layui-iconpicker .layui-anim {\n" +
                "    display: none;\n" +
                "    position: absolute;\n" +
                "    left: 0;\n" +
                "    top: 42px;\n" +
                "    padding: 5px 0;\n" +
                "    z-index: 899;\n" +
                "    min-width: 150% !important;\n" +
                "    border: 1px solid #d2d2d2;\n" +
                "    max-height: 300px;\n" +
                "    overflow-y: auto;\n" +
                "    background-color: #fff;\n" +
                "    border-radius: 2px;\n" +
                "    box-shadow: 0 2px 4px rgba(0,0,0,.12);\n" +
                "    box-sizing: border-box;\n" +
                "}</style><script th:inline=\"javascript\" type=\"text/javascript\">layui.use(['iconPicker'], function () {\n" +
                "            var iconPicker = layui.iconPicker;\n" +
                "            \n" +
                "            iconPicker.render({\n" +
                "                // 选择器，推荐使用input\n" +
                "                elem: '##ELE_ID#',\n" +
                "                // 数据类型：fontClass/unicode，推荐使用fontClass\n" +
                "                type: 'fontClass',\n" +
                "                // 是否开启搜索：true/false，默认true\n" +
                "                search: true,\n" +
                "                // 是否开启分页：true/false，默认true\n" +
                "                page: true,\n" +
                "                // 每页显示数量，默认12\n" +
                "                limit: 12,\n" +
                "                // 点击回调\n" +
                "                click: function (data) {\n" +
                "                    console.log(data);\n" +
                "                },\n" +
                "                // 渲染成功后的回调\n" +
                "                success: function(d) {\n" +
                "                    console.log(d);\n" +
                "                }\n" +
                "            });\n" +
                "/**\n" +
                " * 选中图标 （常用于更新时默认选中图标）\n" +
                " * @param filter lay-filter\n" +
                " * @param iconName 图标名称，自动识别fontClass/unicode\n" +
                " */\n" +
                "iconPicker.checkIcon('#ELE_ID#', '#VALUE#')});</script>";
        result = result.replaceAll("#ELE_NAME#", name)
                .replaceAll("#ELE_ID#", name)
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
