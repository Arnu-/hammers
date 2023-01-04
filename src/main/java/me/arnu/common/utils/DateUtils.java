/*
#     __
#    /  |  ____ ___  _  
#   / / | / __//   // / /
#  /_/`_|/_/  / /_//___/
#@2021-06-10
*/


package me.arnu.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getYYYY_MM_DD() {
        return getNowDateString(YYYY_MM_DD);
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getYYYY_MM_DD_HH_MM_SS() {
        return getNowDateString(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间
     *
     * @return yyyyMMddHHmmss
     */
    public static String getYYYYMMDDHHMMSS() {
        return getNowDateString(YYYYMMDDHHMMSS);
    }

    /**
     * 按格式输出当前时间
     *
     * @param format 格式
     * @return 输出时间
     */
    public static String getNowDateString(final String format) {
        return DateToStr(format, now());
    }

    /**
     * 输出指定日期
     *
     * @param date 日期
     * @return yyyy-MM-dd
     */
    public static String getYYYY_MM_DD(final Date date) {
        return DateToStr(YYYY_MM_DD, date);
    }

    /**
     * 按指定的格式输出指定的日期
     *
     * @param format 格式
     * @param date   日期
     * @return 字符串输出
     */
    public static String DateToStr(final String format, final Date date) {
//        return new SimpleDateFormat(format).format(date);
        return DateFormatUtils.format(date, format);
    }

    /**
     * 按指定的格式将字符串格式化为Date
     *
     * @param format 格式
     * @param ts     字符串时间
     * @return 输出date
     */
    public static Date StrToDate(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        // return DateFormatUtils.format(now(), "yyyy/MM/dd");
        return DateToStr("yyyy/MM/dd", now());
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static String getYYYYMMDD() {
        // return DateFormatUtils.format(now(), "yyyyMMdd");
        return DateToStr("yyyyMMdd", now());
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算两日日期相差多少天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return differentDays(calendar1, calendar2);
    }

    /**
     * 计算两日日期相差多少天
     *
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static int differentDays(Calendar calendar1, Calendar calendar2) {
        if (calendar1.after(calendar2)) {
            Calendar c = calendar1;
            calendar1 = calendar2;
            calendar2 = c;
        }
        int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);

        if (year1 != year2)  //不同年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) { //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else { // 不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {// 同年
            return day2 - day1;
        }
    }
}
