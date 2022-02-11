/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.utils;


import me.arnu.common.config.UploadFileConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 上传文件工具类
 */
@Component
public class UploadUtils {
    // 表单字段常量
    public static final String FORM_FIELDS = "form_fields";
    // 文件域常量
    public static final String FILE_FIELDS = "file";
    // 定义允许上传的文件扩展名
    private Map<String, String> extMap = new HashMap<String, String>();
    // 文件保存目录路径
    private String uploadPath = UploadFileConfig.uploadFolder;
    // 文件的目录名
    private String dirName = "images";
    // 上传临时路径
    private static final String TEMP_PATH = "temp";
    // 临时存相对路径
    private String tempPath = uploadPath + TEMP_PATH;
    // 单个文件最大上传大小(10M)
    private long fileMaxSize = 1024 * 1024 * 10;
    // 最大文件大小(100M)
    private long maxSize = 1024 * 1024 * 100;
    // 文件保存目录url
    private String saveUrl;
    // 文件最终的url包括文件名
    private List<String> fileUrlList = new ArrayList<>();
    // 上传文件原名
    private List<String> fileNameList = new ArrayList<>();

    /**
     * 构造函数
     */
    public UploadUtils() {
        // 其中images,flashs,medias,files,对应文件夹名称,对应dirName
        // key文件夹名称
        // value该文件夹内可以上传文件的后缀名
        extMap.put("images", "gif,jpg,jpeg,png,bmp");
        extMap.put("flashs", "swf,flv");
        extMap.put("medias", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("files", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,mp3,mp4,mp4,mov");
    }

    /**
     * 文件上传
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadFile(HttpServletRequest request, String name) {
        // 验证文件并返回错误信息
        String error = this.validateFields(request, name);
        // 初始化表单元素
        Map<String, Object> fieldsMap = new HashMap<String, Object>();
        if (error.equals("")) {
            fieldsMap = this.initFields(request);
        }
        List<FileItem> fiList = (List<FileItem>) fieldsMap.get(UploadUtils.FILE_FIELDS);
        if (fiList != null) {
            for (FileItem item : fiList) {
                // 上传文件并返回错误信息
                error = this.saveFile(item);
            }
        }
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("error", error);
        result.put("image", this.fileUrlList);
        result.put("name", this.fileNameList);
        return result;
    }

    /**
     * 上传验证并初始化目录
     *
     * @param request
     * @return
     */
    private String validateFields(HttpServletRequest request, String name) {
        String errorInfo = "";
        // 获取内容类型
        String contentType = request.getContentType();
        int contentLength = request.getContentLength();
        // 初始化上传路径，不存在则创建
        File uploadDir = new File(uploadPath);
        // 目录不存在则创建
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (contentType == null || !contentType.startsWith("multipart")) {
            // TODO
            System.out.println("请求不包含multipart/form-data流");
            errorInfo = "请求不包含multipart/form-data流";
        } else if (maxSize < contentLength) {
            // TODO
            System.out.println("上传文件大小超出文件最大大小");
            errorInfo = "上传文件大小超出文件最大大小[" + maxSize + "]";
        } else if (!ServletFileUpload.isMultipartContent(request)) {
            // TODO
            errorInfo = "请选择文件";
        } else if (!uploadDir.isDirectory()) {
            // TODO
            errorInfo = "上传目录[" + uploadPath + "]不存在";
        } else if (!uploadDir.canWrite()) {
            // TODO
            errorInfo = "上传目录[" + uploadPath + "]没有写权限";
        } else if (!extMap.containsKey(dirName)) {
            // TODO
            errorInfo = "目录名不正确";
        } else {
            // 上传路径
            uploadPath = UploadFileConfig.uploadFolder + dirName + "/" + name + "/";
            // 保存目录Url
            saveUrl = "/" + dirName + "/" + name + "/";

            // 创建一级目录
            File saveDirFile = new File(uploadPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }

            // 创建二级目录(格式：年月日)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            uploadPath += ymd + "/";
            saveUrl += ymd + "/";
            File dirFile = new File(uploadPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            // 创建上传临时目录
            File file = new File(tempPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return errorInfo;
    }

    /**
     * 处理上传内容
     *
     * @return
     */
//	@SuppressWarnings("unchecked")
    private Map<String, Object> initFields(HttpServletRequest request) {
        // 存储表单字段和非表单字段
        Map<String, Object> map = new HashMap<String, Object>();
        // 第一步：判断request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        // 第二步：解析request
        if (isMultipart) {
            // 设置环境:创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 阀值,超过这个值才会写到临时目录,否则在内存中
            factory.setSizeThreshold(1024 * 1024 * 10);
            // 设置上传文件的临时目录
            factory.setRepository(new File(tempPath));
            // 核心操作类:创建一个文件上传解析器。
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置文件名称编码(解决上传"文件名"的中文乱码)
            upload.setHeaderEncoding("UTF-8");
            // 限制单个文件上传大小
            upload.setFileSizeMax(fileMaxSize);
            // 限制总上传文件大小
            upload.setSizeMax(maxSize);
            // 使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 第3步：处理uploaded items
            if (items != null && items.size() > 0) {
                Iterator<FileItem> iter = items.iterator();
                // 文件域对象
                List<FileItem> list = new ArrayList<FileItem>();
                // 表单字段
                Map<String, String> fields = new HashMap<String, String>();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    // 处理所有表单元素和文件域表单元素
                    if (item.isFormField()) {
                        // 如果fileitem中封装的是普通输入项的数据（输出名、值）
                        String name = item.getFieldName();// 普通输入项数据的名
                        String value = item.getString();
                        fields.put(name, value);
                    } else {
                        //如果fileitem中封装的是上传文件，得到上传的文件名称
                        // 文件域表单元素
                        list.add(item);
                    }
                }
                map.put(FORM_FIELDS, fields);
                map.put(FILE_FIELDS, list);
            }
        }
        return map;
    }

    /**
     * 保存文件
     *
     * @param item
     * @return
     */
    private String saveFile(FileItem item) {
        String error = "";
        String fileName = item.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        if (item.getSize() > maxSize) { // 检查文件大小
            // TODO
            error = "上传文件大小超过限制";
        } else if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {// 检查扩展名
            error = "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。";
        } else {
            // 存储文件重命名
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + new Random().nextInt(1000) + "." + fileExt;

            // 新增文件原名数组(带后缀)
            fileNameList.add(fileName);
            // 新增值文件数组
            String filePath = saveUrl + newFileName;
            fileUrlList.add(filePath);

            // 写入文件
            try {
                File uploadedFile = new File(uploadPath, newFileName);
                item.write(uploadedFile);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("上传失败了！！！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return error;
    }

    /**
     * *********************get/set方法*********************************
     */
    public String getSaveUrl() {
        return saveUrl;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public Map<String, String> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public List getfileUrlList() {
        return fileUrlList;
    }

    public void setfileUrlList(List fileUrlList) {
        this.fileUrlList = fileUrlList;
    }
}
