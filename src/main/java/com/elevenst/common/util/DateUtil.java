package com.elevenst.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String getFormatString(String pattern) {
        FastDateFormat formatter = FastDateFormat.getInstance(pattern, Locale.KOREA);
        String dateString = formatter.format(new Date());
        return dateString;
    }

    public static Date check(String s, String format) throws ParseException {
        if (s == null) {
            throw new ParseException("date string to check is null", 0);
        } else if (format == null) {
            throw new ParseException("format string to check date is null", 0);
        } else {
            FastDateFormat formatter = FastDateFormat.getInstance(format, Locale.KOREA);
            Date date = null;

            try {
                date = formatter.parse(s);
            } catch (ParseException var5) {
                throw new ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
            }

            if (!formatter.format(date).equals(s)) {
                throw new ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"", 0);
            } else {
                return date;
            }
        }
    }

    public static int daysBetween(String from, String to) throws ParseException {
        return daysBetween(from, to, "yyyyMMdd");
    }

    public static int daysBetween(String from, String to, String format) throws ParseException {
        Date d1 = check(from, format);
        Date d2 = check(to, format);
        long duration = d2.getTime() - d1.getTime();
        return (int)(duration / 86400000L);
    }

    public static String formatDate(Date src, String pattern) {
        if (src != null && StringUtil.isNotEmpty(pattern)) {
            FastDateFormat sdf = FastDateFormat.getInstance(pattern);
            return sdf.format(src);
        } else {
            return null;
        }
    }

    public static String formatDate(String pattern) {
        if (StringUtil.isNotEmpty(pattern)) {
            FastDateFormat sdf = FastDateFormat.getInstance(pattern);
            return sdf.format(new Date());
        } else {
            return null;
        }
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }


    /**
     * return add day to date strings with user defined format.
     * @param s date string
     * @param day 더할 초
     * @param format string representation of the date format. For example, "yyyy-MM-dd".
     * @return int 날짜 형식이 맞고, 존재하는 날짜일 때 일수 더하기
     *           형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
     */
    public static String addSeconds(String s, int day, String format) throws java.text.ParseException{
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
        java.util.Date date = check(s, format);

        date.setTime(date.getTime() + ((long)day * 1000));
        return formatter.format(date);
    }


    public static boolean isValid(String s, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
        Date date = null;

        try {
            date = formatter.parse(s);
        } catch (ParseException var5) {
            return false;
        }

        return formatter.format(date).equals(s);
    }

    /**
     * 시작일자에서 경과된 날짜 구하기
     */
    public static String addDays(String startDate, int addDays, String format){
        int year = Integer.parseInt(startDate.substring(0, 4));
        int month = Integer.parseInt(startDate.substring(4, 6));
        int day = Integer.parseInt(startDate.substring(6, 8));
        Calendar cCal = Calendar.getInstance();
        cCal.set(year, month-1, day-1);

        cCal.add(cCal.DATE, addDays);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
        String endDate = formatter.format(cCal.getTime());
        return endDate;
    }
}
