package me.arnu.springboot.thyemleaf.dialect.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import java.util.Collections;
import java.util.List;

public class SelectIdNameTabElementProcessor extends SelectIdNameElementProcessorBase {

    // 标签名
    private static final String TAG_NAME = "tabSelect";

    // 优先级
    private static final int PRECEDENCE = 10000;

    private String eleName = null;

    private void parseElementNameAttr(String name) {
        // String name = iProcessableElementTag.getAttributeValue("name");
        //status|0|状态|name|id
        String[] vs = name.split("\\|");
        eleName = vs[0];
    }

    public SelectIdNameTabElementProcessor(String dialectPrefix) {
        super(dialectPrefix, TAG_NAME);
    }

    @Override
    protected String START_STR() {
        return "<ul class=\"layui-tab-title\">";
    }

    @Override
    protected String END_STR() {
        return "</ul>";
    }

    @Override
    protected String OPTION() {
        return "<li " + SELECTED + "><a href=\"#HREF#?" + ELE_NAME + "=" + OPT_ID +
                "\">" + OPT_NAME + "</a></li>";
    }

    @Override
    protected String SELECTED_STR() {
        return "class=\"layui-this\"";
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
//        if (eleName == null) {
            String name = iProcessableElementTag.getAttributeValue("name");
            parseElementNameAttr(name);
//        }
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
//        if (eleName == null) {
            String name = iProcessableElementTag.getAttributeValue("name");
            parseElementNameAttr(name);
//        }
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
        if (!iProcessableElementTag.hasAttribute("value")) {
            return Collections.emptyList();
        } else {
            String value = iProcessableElementTag.getAttributeValue("value");
            return Collections.singletonList(value);
        }
    }

    @Override
    protected String BuildElement(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        String baseString = super.BuildElement(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
        String href = iProcessableElementTag.getAttributeValue("href");
        baseString = baseString.replaceAll("#HREF#", href);
        return baseString;
    }
}
