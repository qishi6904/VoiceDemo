package com.svw.dealerapp.util;

import android.provider.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qinshi on 5/12/2017.
 */

public class DateUtils {

    /**
     * 日期时间格式转换
     * @param formatStr         要转成的日期时间格式
     * @param currentFormatStr  当前的日期时间格式
     * @param dateStr           日期时间字符串
     * @return
     */
    public static String dateFormat(String formatStr, String currentFormatStr, String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            SimpleDateFormat currentFormat = new SimpleDateFormat(currentFormatStr);
            Date date = currentFormat.parse(dateStr);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取当前日期
     * @param formatStr 需要的日期格式，如 yyyy-MM-dd
     * @return
     */
    public static String getCurrentDate(String formatStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 添加月份的天
     * @param formatStr
     * @param date
     * @return
     */
    public static String addDay(String formatStr, Date date, int addDay){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        if(addDay == 0) {
            return simpleDateFormat.format(date);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, addDay);
        Date addDate = cal.getTime();
        return simpleDateFormat.format(addDate);
    }

    /**
     * 在给定的日期上添加天数
     * @param original 给定的日期
     * @param delay 添加的天数
     * @return 结果Calendar
     */
    public static Calendar addDay(Calendar original, int delay) {
        if (original == null) {
            return null;
        }
        Calendar resultCalendar = (Calendar) original.clone();
        resultCalendar.add(Calendar.DAY_OF_YEAR, delay);
        return resultCalendar;
    }

    public static Calendar addMonth(Calendar original, int delay){
        if (original == null) {
            return null;
        }
        Calendar resultCalendar = (Calendar) original.clone();
        resultCalendar.add(Calendar.MONTH, delay);
        return resultCalendar;
    }

    /**
     * 在给定的日期上添加月
     * @param formatStr
     * @param date
     * @param offsetMonth
     * @return
     */
    public static String addMonth(String formatStr, Date date, int offsetMonth){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, offsetMonth);
        Date addDate = cal.getTime();
        return simpleDateFormat.format(addDate);
    }

    /**
     * 根据给定的日期时间格式和日期时间字符串获取Date对象
     * @param formatStr
     * @param dateStr
     * @return
     */
    public static Date getDateFromStr(String formatStr, String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 指定日期时间和当前日期时间比较
     * @param formatStr
     * @param dateStr
     * @return
     */
    public static long compareDateWithNow(String formatStr, String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            Date date =  simpleDateFormat.parse(dateStr);
            long dateMill = date.getTime();
            long nowMill = System.currentTimeMillis();
            return dateMill - nowMill;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定日期时间的毫秒值
     * @param formatStr
     * @param dateStr
     * @return
     */
    public static long getDateMill(String formatStr, String dateStr){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            Date date =  simpleDateFormat.parse(dateStr);
            return date.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据指定日期时间字符串获取Calendar
     * @param formatStr
     * @param dateStr
     * @return
     */
    public static Calendar getCalendar(String formatStr, String dateStr){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromStr(formatStr, dateStr));
        return calendar;
    }

    /**
     * 比较两个日期时间字符串的大小关系
     * @param dateFormatStr1
     * @param dateStr1
     * @param dateFormatStr2
     * @param dateStr2
     * @return  正数：第一个比第二个大，负数：第一个比第二个小，0：相等
     */
    public static long compareDate(String dateFormatStr1, String dateStr1,
                                  String dateFormatStr2, String dateStr2){
        try {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(dateFormatStr1);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(dateFormatStr2);

            Date date1 = simpleDateFormat1.parse(dateStr1);
            Date date2 = simpleDateFormat2.parse(dateStr2);

            return date1.getTime() - date2.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
