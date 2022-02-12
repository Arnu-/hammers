/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.generator.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import me.arnu.common.utils.CommonUtils;
import me.arnu.common.utils.DateUtils;
import me.arnu.common.utils.StringUtils;
import me.arnu.system.entity.Menu;
import me.arnu.system.mapper.MenuMapper;
import me.arnu.system.utils.ShiroUtils;
import freemarker.template.Template;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@Data
public class CodeGenerateUtils {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 作者
     */
    @Value("${generate.author}")
    public String author = "";
    /**
     * 创建时间
     */
    private String createTime = DateUtils.getYYYY_MM_DD();
    /**
     * 数据表名
     */
    private String tableName = "";
    /**
     * 数据表前缀
     */
    @Value("${generate.tablePrefix}")
    public String tablePrefix = "";
    /**
     * 表描述
     */
    private String tableAnnotation = "";
    /**
     * 包名
     */
    @Value("${generate.packageName}")
    public String packageName = "";
    /**
     * 模块名
     */
    @Value("${generate.moduleName}")
    public String moduleName = "";
    /**
     * 自动去除表前缀
     */
    @Value("${generate.autoRemovePre}")
    public boolean autoRemovePre = true;
    /**
     * 数据库连接池
     */
    @Value("${spring.datasource.url}")
    private String url = "jdbc:mysql://127.0.0.1:3306/taskwebui?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8&useSSL=true&tinyInt1isBit=false";
    /**
     * 数据库用户名
     */
    @Value("${spring.datasource.username}")
    private String username = "root";
    /**
     * 数据库密码
     */
    @Value("${spring.datasource.password}")
    private String password = "";
    /**
     * 数据库驱动
     */
    private String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * 项目根目录
     */
    String projectPath = System.getProperty("user.dir");
    private String targetPath = "";
    /**
     * 实体对象名
     */
    private String entityName = "";

    /**
     * 连接数据库
     *
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    /**
     * 程序主入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        codeGenerateUtils.generateFile("pms_product", "商品管理");
    }

    /**
     * 根据模板创建文件
     *
     * @throws Exception
     */
    public void generateFile(String tableName, String tableAnnotation) throws Exception {
        try {
            // 数据表名
            this.tableName = tableName;
            // 数据表描述
            this.tableAnnotation = tableAnnotation;
            // 实体对象名
            if (this.autoRemovePre) {
                this.entityName = replaceUnderLineAndUpperCase(tableName.replace(this.tablePrefix, ""));
            } else {
                this.entityName = replaceUnderLineAndUpperCase(tableName);
            }
            // 目标文件路径
            String[] packageArr = packageName.split("\\.");
            targetPath = projectPath + "/" + moduleName + "/src/main/java/" + StringUtils.join(packageArr, "/");

            // 获取数据表信息
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, "%", tableName, "%");

            // 获取数据表列信息
            Map<String, Object> dataMap = getColumnsList(resultSet);

            /**
             * 生成实体Entity文件
             */
            generateEntityFile(dataMap);
            /**
             * 生成Mapper文件
             */
            generateMapperFile();
            /**
             * 生成Dao文件
             */
            generateDaoFile();
            /**
             * 生成查询条件文件
             */
            generateQueryFile(dataMap);
            /**
             * 生成服务类接口文件
             */
            generateIServiceFile();
            /**
             * 生成服务类接口实现文件
             */
            generateServiceImplFile(dataMap);
            /**
             * 生成实体列表Vo
             */
            generateEntityListVoFile(dataMap);
            /**
             * 生成控制器
             */
            generateControllerFile(dataMap);
            /**
             * 生成JS文件
             */
            generateJsFile(dataMap);
            /**
             * 生成模块常亮
             */
            generateConstantFile(dataMap);
            /**
             * 创建列表模板
             */
            generateIndexFile(dataMap);
            /**
             * 创建表单模板
             */
            generateEditFile(dataMap);
            /**
             * 创建菜单
             */
            generateMenu(entityName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    /**
     * 生成实体对象Entity.java文件
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateEntityFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = targetPath + "/entity/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = ".java";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "Entity.ftl";
        File entityFile = new File(filePath);
        generateFileByTemplate(templateName, entityFile, dataMap);
    }

    /**
     * 生成Mapper.xml文件
     *
     * @throws Exception
     */
    private void generateMapperFile() throws Exception {
        // 文件路径
        String path = projectPath + "/" + this.moduleName + "/src/main/resources/mapper/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = "Mapper.xml";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "Mapper.ftl";
        File mapperFile = new File(filePath);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    /**
     * 生成Dao.java文件
     *
     * @throws Exception
     */
    private void generateDaoFile() throws Exception {
        // 文件路径
        String path = targetPath + "/mapper/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = "Mapper.java";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "Dao.ftl";
        File daoFile = new File(filePath);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, daoFile, dataMap);
    }

    /**
     * 生成Query.java查询文件
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateQueryFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = targetPath + "/query/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = "Query.java";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "Query.ftl";
        File queryFile = new File(filePath);
        generateFileByTemplate(templateName, queryFile, dataMap);
    }

    /**
     * 生成服务接口文件
     *
     * @throws Exception
     */
    private void generateIServiceFile() throws Exception {
        // 文件路径
        String path = targetPath + "/service/";
        // 初始化文件路径
        initFileDir(path);
        // 文件前缀
        String prefix = "I";
        // 文件后缀
        String suffix = "Service.java";
        // 完整的文件路径
        String filePath = path + prefix + entityName + suffix;
        // 模板文件
        String templateName = "IService.ftl";
        File serviceFile = new File(filePath);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, serviceFile, dataMap);
    }

    /**
     * 生成服务类实现文件
     *
     * @throws Exception
     */
    private void generateServiceImplFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = targetPath + "/service/impl/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = "ServiceImpl.java";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "ServiceImpl.ftl";
        File serviceImplFile = new File(filePath);
        generateFileByTemplate(templateName, serviceImplFile, dataMap);
    }

    /**
     * 生成列表ListVo文件
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateEntityListVoFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = targetPath + "/vo/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = ".java";
        // 完整的文件路径
        String filePath = path + entityName + "ListVo" + suffix;
        // 模板文件
        String templateName = "EntityListVo.ftl";
        File listVoFile = new File(filePath);
        generateFileByTemplate(templateName, listVoFile, dataMap);
    }

    /**
     * 生成控制器文件
     *
     * @throws Exception
     */
    private void generateControllerFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = targetPath + "/controller/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = "Controller.java";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "Controller.ftl";
        File controllerFile = new File(filePath);
        generateFileByTemplate(templateName, controllerFile, dataMap);
    }

    /**
     * 创建Index列表模板文件
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateIndexFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = projectPath + "/taskwebui-admin/src/main/resources/templates/" + entityName.toLowerCase() + "/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = ".html";
        // 完整的文件路径
        String filePath = path + "index" + suffix;
        // 模板文件
        String templateName = "Index.ftl";
        File entityFile = new File(filePath);
        generateFileByTemplate(templateName, entityFile, dataMap);
    }

    /**
     * 创建Edit表单模板文件
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateEditFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = projectPath + "/taskwebui-admin/src/main/resources/templates/" + entityName.toLowerCase() + "/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = ".html";
        // 完整的文件路径
        String filePath = path + "edit" + suffix;
        // 模板文件
        String templateName = "Edit.ftl";
        File entityFile = new File(filePath);

        List<ColumnClass> columnClasses = (List<ColumnClass>) dataMap.get("model_column");
        List<ColumnClass> arrayList = new ArrayList<>();
        List<ColumnClass> tempList = new ArrayList<>();
        List<ColumnClass> rowList = new ArrayList<>();
        for (ColumnClass columnClass : columnClasses) {
            if (columnClass.getChangeColumnName().equals("CreateUser")) {
                continue;
            }
            if (columnClass.getChangeColumnName().equals("CreateTime")) {
                continue;
            }
            if (columnClass.getChangeColumnName().equals("UpdateUser")) {
                continue;
            }
            if (columnClass.getChangeColumnName().equals("UpdateTime")) {
                continue;
            }
            if (columnClass.getChangeColumnName().equals("Mark")) {
                continue;
            }
            // 图片
            if (columnClass.getColumnName().contains("cover")
                    || columnClass.getColumnName().contains("avatar")
                    || columnClass.getColumnName().contains("image")
                    || columnClass.getColumnName().contains("logo")
                    || columnClass.getColumnName().contains("pic")) {
                tempList.add(columnClass);
                continue;
            }
            // 单行显示常用字段处理
            if (columnClass.getColumnName().contains("note")
                    || columnClass.getColumnName().contains("remark")
                    || columnClass.getColumnName().contains("content")
                    || columnClass.getColumnName().contains("description")
                    || columnClass.getColumnName().contains("intro")) {
                rowList.add(columnClass);
                continue;
            }
            arrayList.add(columnClass);
        }
        if (arrayList.size() + rowList.size() + tempList.size() > 5) {
            List<List<ColumnClass>> columnClassesList = CommonUtils.split(arrayList, 2);
            if (tempList.size() > 0) {
                columnClassesList.add(0, tempList);
            }
            if (rowList.size() > 0) {
                List<List<ColumnClass>> arrayColumnList = CommonUtils.split(rowList, 1);
                arrayColumnList.forEach(item -> {
                    columnClassesList.add(item);
                });
            }
            dataMap.put("model_column", columnClassesList);
            dataMap.put("is_split", true);
        } else {
            dataMap.put("is_split", false);
        }
        generateFileByTemplate(templateName, entityFile, dataMap);
    }

    /**
     * 自动化生成菜单
     *
     * @param entityName 实体对象
     */
    private void generateMenu(String entityName) {
        // 查询菜单是否存在
        Menu entity = menuMapper.selectOne(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getPermission, String.format("sys:%s:index", entityName.toLowerCase()))
                .eq(Menu::getMark, 1)
                .last("limit 1"));

        if (entity != null) {
            // 更新
            entity.setName(tableAnnotation);
            entity.setIcon("layui-icon-home");
            entity.setUrl(String.format("/%s/index", entityName.toLowerCase()));
            entity.setPid(107); // 系统基础信息id
            entity.setType(3);
            entity.setPermission(String.format("sys:%s:index", entityName.toLowerCase()));
            entity.setStatus(1);
            entity.setSort(5);
            entity.setUpdateUser(ShiroUtils.getUserId());
            entity.setUpdateTime(DateUtils.now());
            menuMapper.updateById(entity);
        } else {
            // 创建
            entity = new Menu();
            entity.setName(tableAnnotation);
            entity.setIcon("layui-icon-home");
            entity.setUrl(String.format("/%s/index", entityName.toLowerCase()));
            entity.setPid(112); // 系统工具菜单ID
            entity.setType(3);
            entity.setPermission(String.format("sys:%s:index", entityName.toLowerCase()));
            entity.setStatus(1);
            entity.setSort(5);
            entity.setCreateUser(ShiroUtils.getUserId());
            entity.setCreateTime(DateUtils.now());
            Integer result = menuMapper.insert(entity);
            if (result == 1) {
                // 创建菜单节点 1=列表,5=添加,10=修改,15=删除,20=状态,25=批量删除,30=全部展开,35=全部折叠,40=添加子级
                Integer[] funcList = new Integer[]{1, 5, 10, 15, 25};
                for (Integer item : funcList) {
                    if (item.equals(1)) {
                        // 列表
                        Menu menuList = new Menu();
                        menuList.setName("列表");
                        menuList.setUrl(String.format("/%s/list", entityName.toLowerCase()));
                        menuList.setPid(entity.getId());
                        menuList.setType(4);
                        menuList.setPermission(String.format("sys:%s:list", entityName.toLowerCase()));
                        menuList.setStatus(1);
                        menuList.setSort(item);
                        menuList.setCreateUser(ShiroUtils.getUserId());
                        menuList.setCreateTime(DateUtils.now());
                        menuMapper.insert(menuList);
                    } else if (item.equals(5)) {
                        // 添加
                        Menu menuAdd = new Menu();
                        menuAdd.setName("添加");
                        menuAdd.setUrl(String.format("/%s/add", entityName.toLowerCase()));
                        menuAdd.setPid(entity.getId());
                        menuAdd.setType(4);
                        menuAdd.setPermission(String.format("sys:%s:add", entityName.toLowerCase()));
                        menuAdd.setStatus(1);
                        menuAdd.setSort(item);
                        menuAdd.setCreateUser(ShiroUtils.getUserId());
                        menuAdd.setCreateTime(DateUtils.now());
                        menuMapper.insert(menuAdd);
                    } else if (item.equals(10)) {
                        // 修改
                        Menu menuEdit = new Menu();
                        menuEdit.setName("修改");
                        menuEdit.setUrl(String.format("/%s/update", entityName.toLowerCase()));
                        menuEdit.setPid(entity.getId());
                        menuEdit.setType(4);
                        menuEdit.setPermission(String.format("sys:%s:update", entityName.toLowerCase()));
                        menuEdit.setStatus(1);
                        menuEdit.setSort(item);
                        menuEdit.setCreateUser(ShiroUtils.getUserId());
                        menuEdit.setCreateTime(DateUtils.now());
                        menuMapper.insert(menuEdit);
                    } else if (item.equals(15)) {
                        // 删除
                        Menu menuDel = new Menu();
                        menuDel.setName("删除");
                        menuDel.setUrl(String.format("/%s/delete", entityName.toLowerCase()));
                        menuDel.setPid(entity.getId());
                        menuDel.setType(4);
                        menuDel.setPermission(String.format("sys:%s:delete", entityName.toLowerCase()));
                        menuDel.setStatus(1);
                        menuDel.setSort(item);
                        menuDel.setCreateUser(ShiroUtils.getUserId());
                        menuDel.setCreateTime(DateUtils.now());
                        menuMapper.insert(menuDel);
                    } else if (item.equals(25)) {
                        // 批量删除
                        Menu menuDall = new Menu();
                        menuDall.setName("批量删除");
                        menuDall.setUrl(String.format("/%s/batchDelete", entityName.toLowerCase()));
                        menuDall.setPid(entity.getId());
                        menuDall.setType(4);
                        menuDall.setPermission(String.format("sys:%s:dall", entityName.toLowerCase()));
                        menuDall.setStatus(1);
                        menuDall.setSort(item);
                        menuDall.setCreateUser(ShiroUtils.getUserId());
                        menuDall.setCreateTime(DateUtils.now());
                        menuMapper.insert(menuDall);
                    }
                }
            }
        }
    }

    /**
     * 生成JS文件
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateJsFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = projectPath + "/taskwebui-admin/src/main/resources/static/module/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = ".js";
        // 完整的文件路径
        String filePath = path + "hammers_" + entityName.toLowerCase() + suffix;
        // 模板文件
        String templateName = "Js.ftl";
        File entityFile = new File(filePath);
        generateFileByTemplate(templateName, entityFile, dataMap);
    }

    /**
     * 生成模块常量
     *
     * @param dataMap 参数
     * @throws Exception
     */
    private void generateConstantFile(Map<String, Object> dataMap) throws Exception {
        // 文件路径
        String path = targetPath + "/constant/";
        // 初始化文件路径
        initFileDir(path);
        // 文件后缀
        String suffix = "Constant.java";
        // 完整的文件路径
        String filePath = path + entityName + suffix;
        // 模板文件
        String templateName = "Constant.ftl";
        File controllerFile = new File(filePath);
        generateFileByTemplate(templateName, controllerFile, dataMap);
    }

    /**
     * 生成模板文件
     *
     * @param templateName 模板名称
     * @param file         生成文件
     * @param dataMap      生成参数
     * @throws Exception
     */
    private void generateFileByTemplate(String templateName, File file, Map<String, Object> dataMap) throws Exception {
        Template template = FreeMarkerUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("tableName", tableName);
        dataMap.put("entityName", entityName);
        dataMap.put("author", author);
        dataMap.put("date", createTime);
        dataMap.put("packageName", packageName);
        dataMap.put("tableAnnotation", tableAnnotation);
        // 设置*号字符替换
        dataMap.put("tplTag", "$");
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
    }

    /**
     * 获取数据表列信息
     *
     * @param resultSet
     * @return
     * @throws IOException
     */
    private Map<String, Object> getColumnsList(ResultSet resultSet) throws IOException {
        // 初始化Map对象
        Map<String, Object> dataMap = new HashMap<>();
        try {
            // 获取列信息
            List<ColumnClass> columnClassList = new ArrayList<>();
            ColumnClass columnClass = null;
            boolean hasPid = false;
            boolean columnNumberValue = false;
            while (resultSet.next()) {
                //id字段略过
                if (resultSet.getString("COLUMN_NAME").equals("id")) {
                    continue;
                }
                // 判断是否存在pid
                if (resultSet.getString("COLUMN_NAME").equals("pid")) {
                    hasPid = true;
                }
                columnClass = new ColumnClass();
                //获取字段名称
                columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
                //获取字段类型
                columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
                //转换字段名称，如 sys_name 变成 SysName
                columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
                // 字段默认值
                columnClass.setColumnDefaultValue(resultSet.getString("COLUMN_DEF"));
                //字段在数据库的注释
                String remarks = resultSet.getString("REMARKS");
                columnClass.setColumnComment(remarks);
                // 注解分析
                if (remarks.contains(":") || remarks.contains("：")) {
                    // 获取数字
                    String regets = ":|：|\\s";
                    //在分割的时候顺带把空格给去掉，data的格式基本为: 18:00
                    String[] times = remarks.split(regets);
                    // 标题描述
                    remarks = times[0];
                    Map<String, String> map = new HashMap<>();
                    List<String> columnValue = new ArrayList<>();
                    List<String> columnValue2 = new ArrayList<>();
                    for (int i = 1; i < times.length; i++) {
                        if (StringUtils.isEmpty(times[i])) {
                            continue;
                        }
                        if (times[i].contains("=")) {
                            String[] item = times[i].split("=");
                            System.out.println("Key:" + item[0] + " " + "Val:" + item[1]);
                            map.put(item[0], item[1]);
                            columnValue.add(String.format("%s=%s", item[0], item[1]));
                            columnValue2.add(String.format("%s", item[1]));
                            columnNumberValue = false;
                        } else {
                            String key = Pattern.compile("[^0-9]").matcher(times[i]).replaceAll("").trim();
                            String value = times[i].replaceAll(key, "").trim();
                            System.out.println("Key:" + key + " " + "Val:" + value);
                            map.put(key, value);
                            columnValue.add(String.format("%s=%s", key, value));
                            columnValue2.add(String.format("%s", value));
                            columnNumberValue = true;
                        }
                    }
                    columnClass.setHasColumnCommentValue(true);
                    columnClass.setColumnCommentName(remarks);
                    columnClass.setColumnCommentValue(map);
                    columnClass.setColumnNumberValue(columnNumberValue);
                    // 列值
                    if ((columnClass.getColumnName().equals("status") && columnValue2.size() == 2) || columnClass.getColumnName().startsWith("is_")) {
                        columnClass.setColumnSwitchValue(StringUtils.join(columnValue2, "|"));
                        columnClass.setColumnSwitch(true);
                    } else {
                        columnClass.setColumnSwitch(false);
                    }
                    columnClass.setColumnValue(StringUtils.join(columnValue, ","));
                }
                // 判断是否是图片字段
                if (columnClass.getColumnName().contains("cover")
                        || columnClass.getColumnName().contains("avatar")
                        || columnClass.getColumnName().contains("image")
                        || columnClass.getColumnName().contains("logo")
                        || columnClass.getColumnName().contains("pic")) {
                    // 设置图片字段标识
                    columnClass.setColumnImage(true);
                }
                // 判断是否是多行文本字段
                if (columnClass.getColumnName().contains("note")
                        || columnClass.getColumnName().contains("remark")
                        || columnClass.getColumnName().contains("content")
                        || columnClass.getColumnName().contains("description")
                        || columnClass.getColumnName().contains("intro")) {
                    // 设置多行文本标识
                    columnClass.setColumnTextArea(true);
                }
                columnClassList.add(columnClass);
            }
            dataMap.put("model_column", columnClassList);
            dataMap.put("hasPid", hasPid);
        } catch (Exception e) {

        }
        return dataMap;
    }

    /**
     * 根据路径创建文件夹
     *
     * @param path 路径
     */
    private void initFileDir(String path) {
        // 文件路径
        File file = new File(path);
        // 判断文件路径是否存在
        if (!file.exists()) {
            // 创建文件路径
            file.mkdirs();
        }
    }

    /**
     * 字符串转换函数
     * 如：sys_name 变成 SysName
     *
     * @param str 字符串
     * @return
     */
    public String replaceUnderLineAndUpperCase(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return StringUtils.capitalize(result);
    }

}
