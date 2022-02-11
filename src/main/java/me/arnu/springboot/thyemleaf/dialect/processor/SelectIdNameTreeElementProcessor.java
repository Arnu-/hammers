package me.arnu.springboot.thyemleaf.dialect.processor;

import me.arnu.system.dto.IdNameDto;
import me.arnu.system.mapper.IdNameMapper;
import lombok.val;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SelectIdNameTreeElementProcessor extends SelectIdNameElementProcessorBase {

    // 标签名
    private static final String TAG_NAME = "treeSelect";

    // 优先级
    private static final int PRECEDENCE = 10000;

    private String eleName = null;
    private String cnName = null;
    private String isRequired = null;

    private void parseElementNameAttr(String name) {
        // String name = iProcessableElementTag.getAttributeValue("name");
        //status|0|状态|name|id
        String[] vs = name.split("\\|");
        eleName = vs[0];
        isRequired = vs[1];
        cnName = vs[2];
    }

    public SelectIdNameTreeElementProcessor(String dialectPrefix) {
        super(dialectPrefix, TAG_NAME);
    }

    @Override
    protected String START_STR() {
        return "<select name='" + ELE_NAME + "' id='" + ELE_ID +
                "' " +
                REQUIRED +
                " lay-search='' lay-filter='" + ELE_NAME +
                "'><option value=''>【请选择" + CN_NAME + "】</option>";

    }

    @Override
    protected String END_STR() {
        return "</select>";
    }

    @Override
    protected String OPTION() {
        return "<option value='" + OPT_ID + "' " + SELECTED + ">" + OPT_NAME + "</option>";
    }

    // 选中状态
    @Override
    protected String SELECTED_STR() {
        return "selected = \"selected\"";
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
    protected List<IdNameDto> getIdNameDtoList(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag) {
        List<IdNameDto> idNames = new ArrayList<>();
        if (iProcessableElementTag.hasAttribute("sql")) {
            // 获取 Spring 上下文
            ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
            // 获取注入bean工厂
            AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
            // 获取所需的bean，
            IdNameMapper mapper = autowireCapableBeanFactory.getBean(IdNameMapper.class);

            String sql = iProcessableElementTag.getAttributeValue("sql");
            List<LinkedHashMap> map = mapper.selectAny(sql);
            Map<IdNameDto, List<IdNameDto>> l1Map = new HashMap<>();
            Map<IdNameDto, List<IdNameDto>> l2Map = new HashMap<>();
            map.stream().filter(m -> m.get("pid").toString().equals("0"))
                    .forEach(n -> l1Map.put(new IdNameDto(n.get("id").toString(), n.get("name").toString()), new ArrayList<>()));
            Map<String, IdNameDto> l1IdMap = l1Map.keySet().stream().collect(Collectors.toMap(IdNameDto::getId, v -> v));

            map.stream().filter(m -> {
                String pid = m.get("pid").toString();
                return !pid.equals("0") && l1IdMap.containsKey(pid);
            }).forEach(n -> {
                String pid = n.get("pid").toString();
                val dto = new IdNameDto(n.get("id").toString(), n.get("name").toString());
                l2Map.put(dto, new ArrayList<>());

                l1Map.get(l1IdMap.get(pid)).add(dto);
            });

            Map<String, IdNameDto> l2IdMap = l2Map.keySet().stream().collect(Collectors.toMap(IdNameDto::getId, v -> v));
            map.stream().filter(m -> {
                String pid = m.get("pid").toString();
                return l2IdMap.containsKey(pid);
            }).forEach(n -> {
                String pid = n.get("pid").toString();
                val dto = new IdNameDto(n.get("id").toString(), n.get("name").toString());
                l2Map.get(l2IdMap.get(pid)).add(dto);
            });

            l1IdMap.values().stream().sorted().forEach(
                    k -> {
                        idNames.add(k);
                        List<IdNameDto> l2list = l1Map.get(k);
                        l2list.sort(IdNameDto::compareTo);
                        for (IdNameDto idNameDto : l2list) {
                            val l2 = new IdNameDto(idNameDto.getId(), "|--" + idNameDto.getName());
                            idNames.add(l2);
                            if (l2Map.containsKey(idNameDto)) {
                                List<IdNameDto> v3 = l2Map.get(idNameDto);
                                v3.sort(IdNameDto::compareTo);
                                for (IdNameDto nameDto : v3) {
                                    val l3 = new IdNameDto(nameDto.getId(), "|--|--" + nameDto.getName());
                                    idNames.add(l3);
                                }
                            }
                        }
                    });
        }
        return idNames;
    }

    @Override
    protected String BuildElement(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        String element = super.BuildElement(iTemplateContext, iProcessableElementTag, iElementTagStructureHandler);

        element = element.replace(REQUIRED, isRequired.equals("1") ? REQUIRED_STR : "");
        return element;
    }
}
