package me.arnu.springboot.thyemleaf.dialect.processor;


import me.arnu.common.utils.CommonUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

// @Component
public class UploadMultipleImageElementProcessor extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "uploadMultipleImage";

    // 优先级
    private static final int PRECEDENCE = 10000;

    private static final String IMAGE_TEMP = "<div class=\"layui-upload-drag\"><div class=\"del_img\" onclick=\"remove_image_$ID$(this);\"><img src=\"/static/assets/images/delete.png\"></div>\n" +
            "<a href=\"$IMAGE$\" target=\"_blank\"><img name=\"img_src_$ID$\" src=\"$IMAGE$\" alt=\"$CHNAME$(点击放大预览)\" title=\"$CHNAME$(点击放大预览)\" width=\"$IMGWIDTH$\" height=\"$IMGHEIGHT$\"></a></div>";

    private static final String UPLOAD_SCRIPT = "<div class=\"layui-upload-drag img_upload_$ID$\"><img id=\"btnUploadImg_$ID$\"" +
            " src=\"/static/assets/images/default_upload.png\" alt=\"上传$CHNAME$\" title=\"上传$CHNAME$\"" +
            " width=\"$IMGWIDTH$\" height=\"$IMGHEIGHT$\"><input type=\"hidden\" id=\"$ID$\" name=\"$ID$\" value=\"\"></div>\n" +
            "<style type=\"text/css\">.layui-upload-drag{position:relative;padding:10px;border:1px dashed #e2e2e2;" +
            "background-color:#fff;text-align:center;cursor:pointer;color:#999;margin-right:10px;margin-bottom:10px}" +
            ".del_img{position:absolute;z-index:99;right:0;top:0;width:25px;height:25px;display:block}" +
            ".del_img img{position:absolute;z-index:9;right:0;top:0;width:25px;height:25px;display:inline-block}</style>" +
            "<script th:inline=\"javascript\" type=\"text/javascript\">layui.use([\"upload\",\"croppers\"]," +
            "function(){var layer=layui.layer,upload=layui.upload,croppers=layui.croppers,$=layui.$;var ids=\"\";" +
            "$('img[name=\"img_src_$ID$\"]').each(function(){ids+=$(this).attr(\"src\")+\",\"});ids=ids.substr(0,(ids.length-1));" +
            "$(\"#$ID$\").val(ids);if(false==1){croppers.render({elem:\"#btnUploadImg_$ID$\",name:\"$ID$\",saveW:450,saveH:450,mark:1," +
            "area:[\"750px\",\"500px\"],url:\"/upload/uploadImage?name=$USER$\",done:function(url){if(!url){return layer.msg(\"上传失败\")}" +
            "var hideStr=$(\"#$ID$\").attr(\"value\");var itemArr=hideStr.split(\",\");if(itemArr.length>=\"$MAXIMG$\")" +
            "{layer.msg(\"最多上传$MAXIMG$张图片\",{icon:5,time:1000},function(){});return false}" +
            "var attStr='<div class=\"layui-upload-drag\">'+'<div class=\"del_img\" onclick=\"remove_image_$ID$(this);\">'" +
            "+'<img src=\"/static/assets/images/delete.png\"></img>'+\"</div>\"+'<a href=\"'+url+'\" target=\"_blank\">'+" +
            "'<img name=\"img_src_$ID$\" src=\"'+url+'\" alt=\"$CHNAME$(点击放大预览)\" title=\"$CHNAME$(点击放大预览)\"" +
            " width=\"$IMGWIDTH$\" height=\"$IMGHEIGHT$\">'+\"</a>\"+\"</div>\";$(\".img_upload_$ID$\").before(attStr);" +
            "var ids=\"\";$('img[name=\"img_src_$ID$\"]').each(function(){ids+=$(this).attr(\"src\")+\",\"});" +
            "ids=ids.substr(0,(ids.length-1));$(\"#$ID$\").val(ids);return false}})}else{var uploadInst=upload.render(" +
            "{elem:\"#btnUploadImg_$ID$\",url:\"/upload/uploadImage?name=$USER$\",accept:\"images\",acceptMime:\"image/*\"," +
            "exts:\"jpg|png|gif|bmp|jpeg\",field:\"file\",size:10*1024,multiple:true,number:10,before:function(obj){}," +
            "done:function(res){var hideStr=$(\"#$ID$\").attr(\"value\");var itemArr=hideStr.split(\",\");if(itemArr.length>=" +
            "$MAXIMG$){layer.msg(\"最多上传$MAXIMG$张图片\",{icon:5,time:1000},function(){});return false}if(res.status<=0)" +
            "{return layer.msg(\"上传失败\")}var attStr='<div class=\"layui-upload-drag\">'+'<div class=\"del_img\"" +
            " onclick=\"remove_image_$ID$(this);\">'+'<img src=\"/static/assets/images/delete.png\"></img>'+\"</div>\"+" +
            "'<a href=\"'+res.data+'\" target=\"_blank\">'+'<img name=\"img_src_$ID$\" src=\"'+res.data+'\" alt=\"$CHNAME$(点击放大预览)\"" +
            " title=\"$CHNAME$(点击放大预览)\" width=\"$IMGWIDTH$\" height=\"$IMGHEIGHT$\">'+\"</a>\"+\"</div>\";" +
            "$(\".img_upload_$ID$\").before(attStr);var ids=\"\";$('img[name=\"img_src_$ID$\"]').each(function(){ids+=$(this)" +
            ".attr(\"src\")+\",\"});ids=ids.substr(0,(ids.length-1));$(\"#$ID$\").val(ids);return false}," +
            "error:function(){return layer.msg(\"数据请求异常\")}})}});function remove_image_$ID$(obj){layui.$(obj).parent().remove();" +
            "var ids=\"\";layui.$('img[name=\"img_src_$ID$\"]').each(function(){ids+=layui.$(this).attr(\"src\")+\",\"});" +
            "ids=ids.substr(0,(ids.length-1));layui.$(\"#$ID$\").val(ids)};</script>";

    public UploadMultipleImageElementProcessor(String dialectPrefix) {
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
        // "ablum__upimgs|相册图集|90x90|config|10"
        String[] vs = name.split("\\|");
        String id = vs[0];
        String chname = vs[1];
        String imgsize = vs[2];
        String user = vs[3];
        String maximg = vs[4];

        String imgwidth = imgsize.split("x")[0];
        String imgheight = imgsize.split("x")[1];

        String value = "";
        if (iProcessableElementTag.hasAttribute("value")) {
            value = iProcessableElementTag.getAttributeValue("value");
        }
        String[] imgs = value.split(",");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < imgs.length; i++) {
            String img = IMAGE_TEMP.replaceAll("\\$ID\\$", id)
                    .replaceAll("\\$CHNAME\\$", chname)
                    .replaceAll("\\$IMGWIDTH\\$", imgwidth)
                    .replaceAll("\\$IMGHEIGHT\\$", imgheight)
                    .replaceAll("\\$IMAGE\\$", CommonUtils.getImageURL(imgs[i]));
            builder.append(img);
        }

        String uploadScript = UPLOAD_SCRIPT.replaceAll("\\$ID\\$", id)
                .replaceAll("\\$CHNAME\\$", chname)
                .replaceAll("\\$IMGWIDTH\\$", imgwidth)
                .replaceAll("\\$IMGHEIGHT\\$", imgheight)
                .replaceAll("\\$USER\\$", user)
                .replaceAll("\\$MAXIMG\\$", maximg);
        builder.append(uploadScript);

        // 创建将替换自定义标签的 DOM 结构
        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();
        // 这里是将字典的内容拼装成一个下拉框
        model.add(modelFactory.createText(builder.toString()));
        // 利用引擎替换整合标签
        iElementTagStructureHandler.replaceWith(model, false);
    }
}
