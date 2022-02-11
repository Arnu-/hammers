/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.utils;

import com.alibaba.fastjson.JSONObject;
import me.arnu.common.config.CommonConfig;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共函数类
 */
public class CommonUtils {

    /**
     * 获取当前时间时间戳
     *
     * @return
     */
    public static Integer timeStamp() {
        long currentTime = System.currentTimeMillis();
        String time = String.valueOf(currentTime / 1000);
        return Integer.valueOf(time);
    }

    /**
     * 时间转为日期格式
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatTime(Integer time, String format) {
        if (StringUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        long timeLong = time.longValue() * 1000;
        Date date = new Date(timeLong);
        return dateFormat.format(date);
    }

    /**
     * 获取到图片域名的地址
     *
     * @param imageUrl
     * @return
     */
    public static String getImageURL(String imageUrl) {
        return CommonConfig.imageURL + imageUrl;
    }

    /**
     * 验证邮箱是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号是否正确
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobile);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 生成指定位数的随机字符串
     *
     * @param isNum  是否是纯数字
     * @param length 长度
     * @return
     */
    public static String getRandomStr(boolean isNum, int length) {
        String resultStr = "";
        String str = isNum ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = str.length();
        boolean isStop = true;
        do {
            resultStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = str.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                resultStr += str.charAt(intR);
            }
            if (count >= 2) {
                isStop = false;
            }
        } while (isStop);
        return resultStr;
    }

    /**
     * 判断是否在数组中
     *
     * @param s
     * @param array
     * @return
     */
    public static boolean inArray(final String s, final String[] array) {
        for (String item : array) {
            if (item != null && item.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否在数组中
     *
     * @param key 键值
     * @param map Map对象
     * @return
     */
    public static boolean inArray2(String key, Map<String, String> map) {
        return map.containsKey(key);
    }

    /**
     * 从html中提取纯文本
     *
     * @param strHtml
     * @return
     */
    public static String stripHtml(String strHtml) {
        String content = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        content = content.replaceAll("\\s*|\t|\r|\n", "");//去除字符串中的空格,回车,换行符,制表符
        return content;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     *
     * @param str 原始字符串
     * @return
     */
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

    /**
     * 判断某个元素是否在数组中
     *
     * @param key 元素
     * @param map 数组
     * @return
     */
    public static boolean inArray(String key, Map<String, String> map) {
        boolean flag = false;
        for (String k : map.keySet()) {
            if (k.equals(key)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 对象转Map
     *
     * @param obj 对象
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 判断是否是JSON格式
     *
     * @param str JSON字符串
     * @return
     */
    private boolean isJson(String str) {
        try {
            JSONObject jsonStr = JSONObject.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * MD5方法
     *
     * @param source
     * @return
     */
    public static String md5(byte[] source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            StringBuffer buf = new StringBuffer();
            for (byte b : md.digest()) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @return
     */
    public static String password(String password) {
        String md51 = md5(password.getBytes());
        String pwd = md5((md51 + "IgtUdEQJyVevaCxQnY").getBytes());
        return pwd;
    }

    /**
     * 对数组进行分组
     *
     * @param list 数据源
     * @param size 每组几个
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(List<T> list, Integer size) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        Integer count = list.size() / size;
        List<List<T>> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<T> temp = list.subList(i * size, (i + 1) * size);
            arrayList.add(temp);
        }
        Integer extra = list.size() % size;
        if (extra != 0) {
            List<T> temp = list.subList(count * size, count * size + extra);
            arrayList.add(temp);
        }
        return arrayList;
    }

}
