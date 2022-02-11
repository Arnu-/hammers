package me.arnu.springboot.thyemleaf.dialect.processor;


import me.arnu.system.dto.IdNameDto;
import me.arnu.system.service.IIdNameService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.List;

// @Component
public abstract class SelectIdNameElementProcessorBase extends AbstractElementTagProcessor {

    // 优先级
    private static final int PRECEDENCE = 10000;

    // 拼接标签的基础字符串

    // 选项ID占位符
    protected static final String OPT_ID = "#OPT_ID#";
    // 选项名称占位符
    protected static final String OPT_NAME = "#OPT_NAME#";
    // 选项选中与否占位符
    protected static final String SELECTED = "#SELECTED#";
    // 元素Id占位符
    protected static final String ELE_ID = "#ELE_ID#";
    // 元素名称占位符
    protected static final String ELE_NAME = "#ELE_NAME#";
    // 元素中文名称占位符
    protected static final String CN_NAME = "#CN_NAME#";

    // 必填项占位符
    protected static final String REQUIRED = "#REQUIRED#";
    // 必填项属性
    protected static final String REQUIRED_STR = "lay-verify='required'";

    // 组装用的字符串开始部分
    protected abstract String START_STR();

    // 组装用的字符串结束部分
    protected abstract String END_STR();

    // 选项字符串
    protected abstract String OPTION();

    // 选中状态
    protected abstract String SELECTED_STR();


    public SelectIdNameElementProcessorBase(String dialectPrefix, String tag_name) {
        super(
                // 模板类型为HTML
                TemplateMode.HTML,
                // 标签方言前缀
                dialectPrefix,
                // 标签名称
                tag_name,
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
     * 获取元素名称部分的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的名称
     */
    protected abstract String GetEleName(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler);

    /**
     * 获取元素中文名称部分的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的中文名称
     */
    protected abstract String GetCnName(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler);

    /**
     * 获取元素Id部分的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的Id
     */
    protected abstract String GetEleId(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler);

    /**
     * 获取选中数据的代码
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 返回元素的选中数据
     */
    protected abstract List<String> GetSelectedValues(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler);

    /**
     * 处理自定义标签 DOM 结构
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     */
    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {

//        //select的id属性
//        String id = iProcessableElementTag.getAttributeValue("id");
//        //select的name属性

        String result = BuildElement(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);

        // 创建将替换自定义标签的 DOM 结构
        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();
        // 这里是将字典的内容拼装成一个下拉框
        model.add(modelFactory.createText(result));
        // 利用引擎替换整合标签
        iElementTagStructureHandler.replaceWith(model, false);
    }

    /**
     * 创建元素的html
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     * @param iElementTagStructureHandler 元素标签结构处理器
     * @return 创建元素的html
     */
    protected String BuildElement(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        String ele_name = GetEleName(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
        String ele_id = GetEleId(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
        String cnName = GetCnName(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
        List<String> indexList = GetSelectedValues(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);
        List<IdNameDto> idNames = getIdNameDtoList(iTemplateContext, iProcessableElementTag);
        String options = BuildOptions(idNames, indexList);
        String result = START_STR() + options + END_STR();
        result = result.replaceAll(ELE_NAME, ele_name)
                .replaceAll(ELE_ID, ele_id)
                .replaceAll(CN_NAME, cnName);
        return result;
    }

    /**
     * 构建选项
     *
     * @param idNames       选项
     * @param selectedValue 选中值
     * @return 选项的html
     */
    public String BuildOptions(List<IdNameDto> idNames, List<String> selectedValue) {
        StringBuilder result = new StringBuilder();
        for (IdNameDto idName : idNames) {
            String opt = OPTION()
                    .replaceAll(OPT_ID, idName.getId())
                    .replaceAll(OPT_NAME, idName.getName())
                    .replaceAll(SELECTED, selectedValue.contains(idName.getId())
                            ? SELECTED_STR() : "");
            result.append(opt);
        }
        return result.toString();
    }

    /**
     * 根据标签获取选项值
     * 如果标签包含data部分，就从data取。
     * 如果标签包含sql部分，就从数据库取。
     *
     * @param iTemplateContext       上下文
     * @param iProcessableElementTag 标签属性
     * @return 返回选项
     */
    protected List<IdNameDto> getIdNameDtoList(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag) {
        List<IdNameDto> idNames = new ArrayList<>();
        if (iProcessableElementTag.hasAttribute("data")) {//select的data属性
            String data = iProcessableElementTag.getAttributeValue("data");
            String[] dv = data.replace("{", "")
                    .replace("}", "")
                    .replaceAll("\\n", "")
                    .split(",");
            idNames = new ArrayList<>();
            // {1=男, 2=女, 3=保密}
            for (int i = 0; i < dv.length; i++) {
                String[] ov = dv[i].trim().split("=");
                idNames.add(new IdNameDto(ov[0], ov[1]));
            }
        } else if (iProcessableElementTag.hasAttribute("sql")) {
            // 获取 Spring 上下文
            ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
            // 获取注入bean工厂
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            // 获取所需的bean，
            IIdNameService service = autowireCapableBeanFactory.getBean(IIdNameService.class);

            String sql = iProcessableElementTag.getAttributeValue("sql");
            idNames = service.GetList(sql);
        }
        return idNames;
    }
}
