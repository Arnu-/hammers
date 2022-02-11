package me.arnu.springboot.thyemleaf.dialect.processor;


import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

// @Component
public class UploadSingleImageElementProcessor extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "uploadSingleImage";

    // 优先级
    private static final int PRECEDENCE = 10000;

    // <a href="http://images.taskwebui.vip//images/config/20210129/20210129131608808.jpg" target="_blank">
    // /static/assets/images/default_upload.png
    // 上传图片
    // 点击查看大图
    private static final String HTML_ELEMENT = "<div class=\"layui-input-block\"><div class=\"layui-upload-drag\"><a href=\"#IMGVALUEBLANK#\">" +
            "<img id=\"$ID$_show_id\" src=\"#IMGVALUE#\" alt=\"#ALTVALUE#\" width=\"$IMGWIDTH$\" height=\"$IMGHEIGHT$\"></a>" +
            "<input type=\"hidden\" id=\"$ID$\" name=\"$ID$\" value=\"#INPUTVALUE#\"></div><div style=\"margin-top:10px;\">" +
            "<button type=\"button\" class=\"layui-btn\" id=\"btnUploadImg_$ID$\">" +
            "<i class=\"layui-icon\">&#xe67c;</i>上传$CHNAME$</button></div>" +
            "<div class=\"layui-form-mid layui-word-aux\">建议尺寸：$SUGGEST$</div></div><style type=\"text/css\">.layui-upload-drag {\n" +
            "    position: relative;\n" +
            "    padding: 10px;\n" +
            "    border: 1px dashed #e2e2e2;\n" +
            "    background-color: #fff;\n" +
            "    text-align: center;\n" +
            "    cursor: pointer;\n" +
            "    color: #999;\n" +
            "}</style><script th:inline=\"javascript\" type=\"text/javascript\">layui.use(['upload','croppers'],function(){\n" +
            "//声明变量\n" +
            "var layer = layui.layer\n" +
            ",upload = layui.upload\n" +
            ",croppers = layui.croppers\n" +
            ",$ = layui.$;\n" +
            "\n" +
            "if(true) {\n" +
            "\n" +
            "//图片裁剪组件\n" +
            "    croppers.render({\n" +
            "        elem: '#btnUploadImg_$ID$'\n" +
            "        ,name:\"$ID$\"\n" +
            "        ,saveW:$SAVEWIDTH$     //保存宽度\n" +
            "        ,saveH:$SAVEHEIGHT$\n" +
            "        ,mark:1.0/1    //选取比例\n" +
            "        ,area:['750px','500px']  //弹窗宽度\n" +
            "        ,url: \"/upload/uploadImage?name=$USER$\"\n" +
            "        ,done: function(url){ \n" +
            "        \t//上传完毕回调\n" +
            "            $('#$ID$').val(url);\n" +
            "            $('#$ID$_show_id').attr('src',url);\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "}else{\n" +
            "\n" +
            "/**\n" +
            " * 普通图片上传\n" +
            " */\n" +
            "var uploadInst = upload.render({\n" +
            "    elem: '#btnUploadImg_$ID$'\n" +
            ",url: \"/upload/uploadImage?name=$USER$\"\n" +
            ",accept:'images'\n" +
            ",acceptMime:'image/*'\n" +
            ",exts: \"jpg|png|gif|bmp|jpeg\"\n" +
            ",field:'file'//文件域字段名\n" +
            ",size: 1024 * 10 //最大允许上传的文件大小\n" +
            ",before: function(obj){\n" +
            "//预读本地文件\n" +
            "}\n" +
            ",done: function(res){\n" +
            "//上传完毕回调\n" +
            "\n" +
            "if(res.code!=0){\n" +
            "layer.msg(res.msg,{ icon: 5 });\n" +
            "return false;\n" +
            "}\n" +
            "//上传成功\n" +
            "$('#$ID$_show_id').attr('src', res.data);\n" +
            "$('#$ID$').val(res.data);\n" +
            "}\n" +
            ",error: function(){\n" +
            "//请求异常回调\n" +
            "return layer.msg('数据请求异常');\n" +
            "}\n" +
            "});\n" +
            "}\n" +
            "});</script>";

    public UploadSingleImageElementProcessor(String dialectPrefix) {
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
//        // 获取 Spring 上下文
//        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
//        // 获取注入bean工厂
//        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
//        // 获取所需的bean，一般情况下这里我们直接使用Jdbc来操作数据库，因为它是一个公共组件，数据源不确定，所以要使用动态sql
//        JdbcOperations jdbcOperations = autowireCapableBeanFactory.getBean(JdbcOperations.class);
//        //select的id属性
//        String id = iProcessableElementTag.getAttributeValue("id");
        //select的name属性
        String name = iProcessableElementTag.getAttributeValue("name");

        // avatar|头像|90x90|admin|建议上传尺寸450x450|450x450
        String[] vs = name.split("\\|");
        String id = vs[0];
        String chname = vs[1];
        String imgsize = vs[2];
        String user = vs[3];
        String suggest = vs[4];

        String savesize = "450x450";
        if (vs.length > 5) {
            savesize = vs[5];
        }
        String imgwidth = imgsize.split("x")[0];
        String imgheight = imgsize.split("x")[1];

        String savewidth = savesize.split("x")[0];
        String saveheight = savesize.split("x")[1];


        String result = HTML_ELEMENT.replaceAll("\\$ID\\$", id)
                .replaceAll("\\$CHNAME\\$", chname)
                .replaceAll("\\$IMGWIDTH\\$", imgwidth)
                .replaceAll("\\$IMGHEIGHT\\$", imgheight)
                .replaceAll("\\$USER\\$", user)
                .replaceAll("\\$SUGGEST\\$", suggest)
                .replaceAll("\\$SAVEWIDTH\\$", savewidth)
                .replaceAll("\\$SAVEHEIGHT\\$", saveheight);
        String value = "";
        if (iProcessableElementTag.hasAttribute("value")) {
            value = iProcessableElementTag.getAttributeValue("value");
        }

        if (value.length() > 0) {
            result = result.replaceAll("#IMGVALUE#", value)
                    .replaceAll("#INPUTVALUE#", value)
                    .replaceAll("#IMGVALUEBLANK#", value + "\" target=\"_blank\"")
                    .replaceAll("#ALTVALUE#", "点击查看大图");
        } else {
            result = result.replaceAll("#IMGVALUE#", "/static/assets/images/default_upload.png")
                    .replaceAll("#INPUTVALUE#", "")
                    .replaceAll("#IMGVALUEBLANK#", "javascript:void(0);")
                    .replaceAll("#ALTVALUE#", "上传图片");
        }

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
