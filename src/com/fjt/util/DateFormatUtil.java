package com.fjt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化的工具类
 */
public class DateFormatUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 提供返回格式化时间的方法，
     * @param date
     * @return 格式后的时间
     */
    public static String format(Date date){
        return format.format(date);
    }

    /**
     * parse()：将字符串类型的时间变成日期
     * getTime()：将日期变成long
     * @param formatString
     * @return
     */
    public static long toTime(String formatString){
        try {
            return  format.parse(formatString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
