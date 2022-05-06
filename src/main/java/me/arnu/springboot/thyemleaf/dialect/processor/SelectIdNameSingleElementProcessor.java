package me.arnu.springboot.thyemleaf.dialect.processor;


import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import java.util.Collections;
import java.util.List;

// @Component
public class SelectIdNameSingleElementProcessor extends SelectIdNameElementProcessorBase {
    // 标签名
    private static final String TAG_NAME = "singleSelect";

    // 优先级
    private static final int PRECEDENCE = 10000;

    // 组装用的字符串开始部分
    @Override
    protected String START_STR() {
        return "<select name='" + ELE_NAME +
                "' id='" + ELE_ID + "' " + 
                REQUIRED +
                " lay-search='' lay-filter='" + ELE_NAME +
                "'><option value=''>【请选择" + CN_NAME +
                "】</option>";
    }

    // 组装用的字符串结束部分
    @Override
    protected String END_STR() {
        return "</select>";
    }

    // 选项字符串
    @Override
    protected String OPTION() {
        return "<option value='" + OPT_ID + "' " + SELECTED + ">" + OPT_NAME + "</option>";
    }

    // 选中状态
    @Override
    protected String SELECTED_STR() {
        return "selected = \"selected\"";
    }

    public SelectIdNameSingleElementProcessor(String dialectPrefix) {
        super(dialectPrefix, TAG_NAME);
    }

    private String eleName = null;
    private String cnName = null;
    private String isRequired = null;
    //lay-verify="required

    private void parseElementNameAttr(String name) {
        // String name = iProcessableElementTag.getAttributeValue("name");
        //status|0|状态|name|id
        String[] vs = name.split("\\|");
        eleName = vs[0];
        isRequired = vs[1];
        cnName = vs[2];
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
        //status|0|状态|name|id
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
        //status|0|状态|name|id
//        if (eleName == null) {
        String name = iProcessableElementTag.getAttributeValue("name");
        parseElementNameAttr(name);
//        }
        return cnName;
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
        //status|0|状态|name|id
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
        if (iProcessableElementTag.hasAttribute("value")) {
            String value = iProcessableElementTag.getAttributeValue("value");
            return Collections.singletonList(value);
        }
        return Collections.emptyList();
    }

    @Override
    protected String BuildElement(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        String element = super.BuildElement(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);

        element = element.replace(REQUIRED, isRequired.equals("1") ? REQUIRED_STR : "");
        return element;
    }
}
