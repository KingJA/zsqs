package com.kingja.zsqs.utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Description:TODO
 * Create Time:2020/1/15 0015 上午 11:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DateUtil {

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth =c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        String mWay =String.valueOf(c.get(Calendar.DAY_OF_WEEK)) ;
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        int mSecond = c.get(Calendar.SECOND);
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return String.format("%d年%02d月%02d日 星期%s %02d:%02d:%02d",mYear,mMonth,mDay,mWay,mHour,mMinute,mSecond) ;
    }

}
