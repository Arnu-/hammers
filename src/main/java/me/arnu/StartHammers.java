/*

#     __                        
#    /  |  ____ ___  _          
#   / / | / __//   // / /       
#  /_/`_|/_/  / /_//___/        
create @ 2022/2/23                                
*/
package me.arnu;

import me.arnu.admin.AdminApplication;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;

/**
 * 启动系统
 */
public class StartHammers {
    public static void main(String[] args) {
        /*
        启动Hammers系统
        1、检查程序启动目录
        2、检查数据库文件，
        2.1、是否存在
        2.2、是否有备份
        3、启动hammers
        4、启动浏览器
         */
        String path = getPath(AdminApplication.class);
        System.out.println(path);
    }

    /**
     * 获取 class 的jar 路径
     * @return
     */
    public static String getPath(Class<?> clazz){
        String path = "";
        try{
            //jar 中没有目录的概念
            URL location = clazz.getProtectionDomain().getCodeSource().getLocation();//获得当前的URL
            File file = new File(location.getPath());//构建指向当前URL的文件描述符
            if(file.isDirectory()){//如果是目录,指向的是包所在路径，而不是文件所在路径
                path = file.getAbsolutePath();//直接返回绝对路径
            }else{//如果是文件,这个文件指定的是jar所在的路径(注意如果是作为依赖包，这个路径是jvm启动加载的jar文件名)
                path = file.getParent();//返回jar所在的父路径
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return path;
    }
}
