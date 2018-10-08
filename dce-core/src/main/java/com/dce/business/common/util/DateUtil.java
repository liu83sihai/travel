package com.dce.business.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dce.business.common.enums.KLineTypeEnum;

public final class DateUtil {
    public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat YYYY_MM_DD_HH = new SimpleDateFormat("yyyy-MM-dd HH");
    public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");
    public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 时间转换为yyyy-MM-dd格式的字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
    	if(null == date){return "";}
        return YYYY_MM_DD.format(date);
    }
    
    /**
     * 时间转换为yyyy-MM-dd  hh:mm:ss格式的字符串 
     * @return
     */
    public static String dateformat(Date date){
    	if(null == date){
    		return "";
    	}
    	return YYYY_MM_DD_MM_HH_SS.format(date);
    }
    
    public static String klineDate(Date date,KLineTypeEnum kLineType){
    	if (KLineTypeEnum.KLINE_TYPE_HOUR.equals(kLineType)) {
            return YYYY_MM_DD_HH.format(date);
        } else if (KLineTypeEnum.KLINE_TYPE_DAY.equals(kLineType)) {
            return YYYY_MM_DD.format(date);
        } else if (KLineTypeEnum.KLINE_TYPE_WEEK.equals(kLineType)) {
            return YYYY_MM_DD.format(date);
        }else if (KLineTypeEnum.KLINE_TYPE_MONTH.equals(kLineType)) {
	    	return YYYY_MM.format(date);
	    }
    	return null;
    }
    public static Date parseDate(String date,KLineTypeEnum kLineType) throws ParseException{
    	if (KLineTypeEnum.KLINE_TYPE_HOUR.equals(kLineType)) {
    		return YYYY_MM_DD_HH.parse(date);
    	} else if (KLineTypeEnum.KLINE_TYPE_DAY.equals(kLineType)) {
    		return YYYY_MM_DD.parse(date);
    	} else if (KLineTypeEnum.KLINE_TYPE_WEEK.equals(kLineType)) {
    		return YYYY_MM_DD.parse(date);
    	}else if (KLineTypeEnum.KLINE_TYPE_MONTH.equals(kLineType)) {
    		return YYYY_MM.parse(date);
    	}
    	return null;
    }
    
    public static Date getDate(Date date, int minute, Integer hour, Integer day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        if (hour != null && hour == 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        } else if (hour != null && hour != 0) {
            calendar.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (day != null) {
            calendar.add(Calendar.DATE, day);
        }

        return calendar.getTime();
    }
    
    public static Date getDate(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        if (day != null) {
            calendar.add(Calendar.DATE, day);
        }

        return calendar.getTime();
    }

    public static Date parserStr(String dateStr,int type) throws ParseException{
    	if(type == 0){
    		return YYYY_MM_DD_MM_HH_SS.parse(dateStr + " 00:00:00");
    	}else{
    		
    		return YYYY_MM_DD_MM_HH_SS.parse(dateStr + "23:59:59");
    	}
    }
    //根据日期时间获取对应的java时间戳
    public static long getTimeStamp(String dateTime) throws ParseException{
    	
    	return YYYY_MM_DD_MM_HH_SS.parse(dateTime).getTime();
    }
    /**
     * 计算两个日期相隔天数
     * @param begin
     * @param end
     * @return
     */
	public static int diffdays(Date begin, Date end) {
		Calendar calst = java.util.Calendar.getInstance();
		Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(begin);
		caled.setTime(end);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = (int) (((caled.getTime().getTime() / 1000) - (calst.getTime().getTime() / 1000)) / 3600 / 24);

		return days;

	}
	
	/**
	 * 得到当前日期
	 * @return
	 */
	public static Date getCurrentDate(){
		 Calendar calendar = Calendar.getInstance();
		 return calendar.getTime();
	}
    public static void main(String[] args) {
        Date a = getDate(new Date(), 0, 0, -1);
        System.out.println(a);
        a = getDate(a, 0, 0, -1);
        System.out.println(a);

        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例  
        ca.setTime(new Date()); // 设置时间为当前时间  
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        //ca.set(2011, 11, 17);// 月份是从0开始的，所以11表示12月  
        //ca.add(Calendar.YEAR, -1); // 年份减1  
        //ca.add(Calendar.MONTH, -1);// 月份减1  
        ca.add(Calendar.DATE, -1);// 日期减1  
        Date resultDate = ca.getTime(); // 结果  
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(resultDate);

        ca = Calendar.getInstance();
        ca.setTime(resultDate);
        ca.add(Calendar.DATE, -1);// 日期减1  
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        resultDate = ca.getTime(); // 结果  
        System.out.println(resultDate);
    }
}
