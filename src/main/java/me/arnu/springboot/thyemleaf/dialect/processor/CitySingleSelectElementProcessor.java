package me.arnu.springboot.thyemleaf.dialect.processor;


import me.arnu.common.utils.JsonResult;
import me.arnu.system.service.ICityService;
import org.apache.commons.lang3.StringUtils;
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

import java.util.List;
import java.util.Map;

// @Component
public class CitySingleSelectElementProcessor extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "citySingleSelect";

    // 优先级
    private static final int PRECEDENCE = 10000;

    private static final String template = "<div class=\"layui-input-inline\" style=\"width:165px;\">" +
            "<select name=\"provinceId\" id=\"provinceId\" lay-filter=\"provinceId\" lay-search=\"\">" +
            "<option value=''>【请选择省】</option>" +
            // "<option value=\"1\">北京市</option>" +
            "$PROVINCE$" +
            "</select>" +
            "</div>" +
            "<div class=\"layui-input-inline\" style=\"width:165px;\">" +
            "<select name=\"cityId\" id=\"cityId\" lay-filter=\"cityId\" lay-search=\"\">" +
            "<option value=''>【请选择市】</option>" +
            "$CITY$" +
            "</select></div>" +
            "<div class=\"layui-input-inline\" style=\"width:165px;\">" +
            "<select name=\"districtId\" id=\"districtId\" lay-filter=\"districtId\" lay-search=\"\">" +
            "<option value=''>【请选择县/区】</option>" +
            "$AREA$" +
            "</select></div>" +
            "<script type=\"text/javascript\">layui.use(['form'],function(){\n" +
            "\n" +
            "  // 声明变量\n" +
            "  var form = layui.form,\n" +
            "    $ = layui.$;\n" +
            "\n" +
            "  // 选择省份\n" +
            "  form.on('select(provinceId)',function(data){\n" +
            "    var id = data.value;\n" +
            "    console.log(\"省份ID:\"+id);\n" +
            "    var select = data.othis;\n" +
            "    if (select[0]) {\n" +
            "      if (id > 0) {\n" +
            "        $.post(\"/city/getCityListByPid\", { 'pid':id }, function(data){\n" +
            "          if (data.code == 0) {\n" +
            "            var str = \"\";\n" +
            "    console.log(data.data);\n" +
            "            $.each(data.data, function(i,item){\n" +
            "              str += \"<option value=\\\"\" + item.id + \"\\\" >\" + item.name + \"</option>\";\n" +
            "            });\n" +
            "            $(\"#cityId\").html('<option value=\"\">【请选择市】</option>' + str);\n" +
            "            $(\"#districtId\").html('<option value=\"\">【请选择县/区】</option>');\n" +
            "            form.render('select');\n" +
            "          }else{\n" +
            "            layer.msg(data.msg,{ icon: 5 });\n" +
            "            \n" +
            "            $(\"#cityId\").html('<option value=\"\">【请选择市】</option>');\n" +
            "            $(\"#districtId\").html('<option value=\"\">【请选择县/区】</option>');\n" +
            "            form.render('select');\n" +
            "            \n" +
            "            \n" +
            "            return false;\n" +
            "          }\n" +
            "        }, 'json');\n" +
            "      } else {\n" +
            "        \n" +
            "      }\n" +
            "    }\n" +
            "  });\n" +
            "  \n" +
            "  // 选择城市\n" +
            "  form.on('select(cityId)',function(data){\n" +
            "    var id = data.value;\n" +
            "    console.log(\"城市ID:\"+id);\n" +
            "    var select = data.othis;\n" +
            "    if (select[0]) {\n" +
            "      if (id > 0) {\n" +
            "        $.post(\"/city/getCityListByPid\", { 'pid':id }, function(data){\n" +
            "          if (data.code == 0) {\n" +
            "            var str = \"\";\n" +
            "            $.each(data.data, function(i,item){\n" +
            "              str += \"<option value=\\\"\" + item.id + \"\\\" >\" + item.name + \"</option>\";\n" +
            "            });\n" +
            "            $(\"#districtId\").html('<option value=\"\">【请选择县/区】</option>' + str);\n" +
            "            form.render('select');\n" +
            "          }\n" +
            "        }, 'json');\n" +
            "      } else {\n" +
            "        layer.msg(data.msg,{ icon: 5 });\n" +
            "        \n" +
            "        $(\"#districtId\").html('<option value=\"\">【请选择县/区】</option>');\n" +
            "        form.render('select');\n" +
            "\n" +
            "        return false;\n" +
            "      }\n" +
            "    }\n" +
            "  });\n" +
            "  \n" +
            "  // 选择县区\n" +
            "  form.on(\"select(districtId)\",function(data){\n" +
            "    var id = data.value;\n" +
            "    console.log(\"县区ID:\"+id);\n" +
            "  });\n" +
            "  \n" +
            "});</script>";

    public CitySingleSelectElementProcessor(String dialectPrefix) {
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

//        //select的id属性
//        String id = iProcessableElementTag.getAttributeValue("id");
//        //select的name属性

//        //option中value的值在数据表中的对应字段
//        String colVal = iProcessableElementTag.getAttributeValue("colVal");
//        //option中文本的值在数据表中的对应字段
//        String colText = iProcessableElementTag.getAttributeValue("colText");
//        //默认值
//        String value = iProcessableElementTag.getAttributeValue("value");
//        value = StringUtils.isBlank(value) ? "" : value;
//        //select的扩展属性
//        String otherAttrs = iProcessableElementTag.getAttributeValue("otherAttrs");
//        //sql where之后的部分
//        String options = iProcessableElementTag.getAttributeValue("options");
//        //是否显示请选择
//        String selectFlag = iProcessableElementTag.getAttributeValue("selectFlag");
//        //表名称
//        String tableName = iProcessableElementTag.getAttributeValue("tableName");
        // 获取 Spring 上下文
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
        // 获取注入bean工厂
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        // 获取所需的bean，
        ICityService service = autowireCapableBeanFactory.getBean(ICityService.class);
        String result = new String(template);
        String proId = "0";
        String value = "0";

        if (iProcessableElementTag.hasAttribute("value")) {
            // 收到区域id
            value = iProcessableElementTag.getAttributeValue("value");
        }
        if (!value.equals("0")) {
            int did = Integer.parseInt(value);
            Map<String, Object> district = service.info(did);
            // 取出父级城市信息
            int pid = (int) district.get("pid");
            // 取出市级的各区信息
            JsonResult districts = service.getCityListByPid(pid);
            List<Map<String, Object>> dlist = (List<Map<String, Object>>) districts.getData();
            String districtTag = "";
            for (Map<String, Object> map : dlist) {
                districtTag = districtTag + "<option value=\"" + map.get("id") + "\"";
                if (value.equals(map.get("id"))) {
                    districtTag = districtTag + " selected = \"selected\" ";
                }
                districtTag = districtTag + ">" + map.get("name") + "</option>";
            }
            result = result.replaceAll("\\$AREA\\$", districtTag);

            // 取出省级信息
            Map<String, Object> city = service.info(pid);
            pid = (int) city.get("pid");

            // 取出省级的各市信息
            JsonResult cities = service.getCityListByPid(pid);
            List<Map<String, Object>> clist = (List<Map<String, Object>>) cities.getData();
            String cityTag = "";
            for (Map<String, Object> map : clist) {
                cityTag = cityTag + "<option value=\"" + map.get("id") + "\"";
                if (value.equals(map.get("id"))) {
                    cityTag = cityTag + " selected = \"selected\" ";
                }
                cityTag = cityTag + ">" + map.get("name") + "</option>";
            }
            result = result.replaceAll("\\$CITY\\$", cityTag);
            proId = String.valueOf(pid);
        } else {
            result = result.replaceAll("\\$AREA\\$", "");
            result = result.replaceAll("\\$CITY\\$", "");
        }


        // 取出各省信息
        JsonResult provinces = service.getCityListByPid(0);
        List<Map<String, Object>> plist = (List<Map<String, Object>>) provinces.getData();
        String proTag = "";
        for (Map<String, Object> map : plist) {
            proTag = proTag + "<option value=\"" + map.get("id") + "\"";
            if (proId.equals(map.get("id"))) {
                proTag = proTag + " selected = \"selected\" ";
            }
            proTag = proTag + ">" + map.get("name") + "</option>";
        }
        result = result.replaceAll("\\$PROVINCE\\$", proTag);

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
