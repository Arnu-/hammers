package me.arnu.springboot.thyemleaf.dialect.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @Component
public class SelectIdNameRadioElementProcessor extends SelectIdNameElementProcessorBase {
    // 标签名
    private static final String TAG_NAME = "radioSelect";

    // 优先级
    private static final int PRECEDENCE = 10000;

    // 拼接标签的基础字符串

    // 组装用的字符串开始部分
    @Override
    protected String START_STR() {
        return "";
    }

    // 组装用的字符串结束部分
    @Override
    protected String END_STR() {
        return "";
    }

    @Override
    protected String OPTION() {
        return "<input type=\"radio\" name=\"" + ELE_NAME + "\" value=\"" + OPT_ID + "\" title=\"" + OPT_NAME + "\" " + SELECTED + ">";
    }

    @Override
    protected String SELECTED_STR() {
        return "checked=\"\"";
    }

    public SelectIdNameRadioElementProcessor(String dialectPrefix) {
        super(dialectPrefix, TAG_NAME);
    }

    /**
     * 获取元素名称部分的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的名称
     */
    @Override
    protected String GetEleName(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        String eleName = "";
        if (iProcessableElementTag.hasAttribute("name")) {
            String name = iProcessableElementTag.getAttributeValue("name");
            // "checkbox|name|id"
            String[] vs = name.split("\\|");
            eleName = vs[0];
        }
        return eleName;
    }

    /**
     * 获取元素中文名称部分的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的中文名称
     */
    @Override
    protected String GetCnName(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        return "";
    }

    /**
     * 获取元素Id部分的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的Id
     */
    @Override
    protected String GetEleId(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        String eleName = "";
        if (iProcessableElementTag.hasAttribute("name")) {
            String name = iProcessableElementTag.getAttributeValue("name");
            // "checkbox|name|id"
            String[] vs = name.split("\\|");
            eleName = vs[0];
        }
        return eleName;
    }

    /**
     * 获取选中数据的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的选中数据
     */
    @Override
    protected List<String> GetSelectedValues(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        List<String> selectedValues = new ArrayList<>();
        if (iProcessableElementTag.hasAttribute("value")) {
            String value = iProcessableElementTag.getAttributeValue("value");
            selectedValues = Arrays.asList(value.split(","));
            // index = Integer.parseInt(value);
        }
        return selectedValues;
    }
}
