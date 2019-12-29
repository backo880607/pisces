package com.pisces.platform.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    public static final Date MIN;
    public static final Date MAX = new Date(Long.MAX_VALUE);
    public static final long PER_SECOND = 1000;
    public static final long PER_MINUTE = 60000;
    public static final long PER_HOUR = 3600000;
    public static final long PER_DAY = 86400000;
    public static final Date INVALID = new Date(0);
    public static final String SIMPLE_FROMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    private static long ZONE_TIME = 0;

    static {
        Calendar cal = Calendar.getInstance();
        ZONE_TIME = cal.get(Calendar.ZONE_OFFSET);
        cal.set(1970, 0, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        MIN = cal.getTime();
    }

    protected DateUtils() {}

    private static ThreadLocal<Calendar> calendarLocal = new ThreadLocal<>();

    private static Calendar getCalendar(Date date) {
        Calendar calendar = calendarLocal.get();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            calendarLocal.set(calendar);
        }
        calendar.setTime(date);
        return calendar;
    }

    private static ThreadLocal<Map<String, SimpleDateFormat>> formatLocal = new ThreadLocal<>();

    private static SimpleDateFormat getSimpleDateFormat(String format) {
        Map<String, SimpleDateFormat> formatMap = formatLocal.get();
        if (formatMap == null) {
            formatMap = new HashMap<>();
            formatLocal.set(formatMap);
        }
        SimpleDateFormat simpleDateFormat = formatMap.get(format);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(format);
            formatMap.put(format, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static long getZoneTime() {
        return ZONE_TIME;
    }

    public static boolean isValid(Date date) {
        return date != null && date != DateUtils.INVALID;
    }

    public static String format(Date date, String format) {
        if (date == null || date.getTime() == 0 || date == DateUtils.INVALID) {
            return "";
        }
        return getSimpleDateFormat(format).format(date);
    }

    public static String format(Date date) {
        return format(date, SIMPLE_FROMAT);
    }

    public static Date parse(String value, String format) throws ParseException {
        if (value == null || value.isEmpty()) {
            return DateUtils.INVALID;
        }
        return getSimpleDateFormat(format).parse(value);
    }

    public static Date parse(String value) throws ParseException {
        return parse(value, SIMPLE_FROMAT);
    }

    public static Date today() {
        return null;
    }

    /**
     * 返回今天是周几
     * 返回值：1/2/3/4/5/6/7
     *
     * @param date 日期
     * @return int
     */
    public static int dayOfWeek(Date date) {
        int week = getCalendar(date).get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                week = 7;
                break;
            case 2:
                week = 1;
                break;
            case 3:
                week = 2;
                break;
            case 4:
                week = 3;
                break;
            case 5:
                week = 4;
                break;
            case 6:
                week = 5;
                break;
            case 7:
                week = 6;
                break;
        }
        return week;
    }

    /**
     * 返回这一年的第几天
     *
     * @param date 日期
     * @return int
     */
    public static int dayOfYear(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 返回这个月的第几周  (1号开始 ,7天为一周)
     *
     * @param date 日期
     * @return int
     */
    public static int dayOfWeekInMonth(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 返回这个月的第几周 (周一开始,到周日为一周)
     *
     * @param date 日期
     * @return int
     */
    public static int weekOfMonth(Date date) {
        return getCalendar(date).get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 返回这一年的第几周 (1月1日开始,7天为一周)
     *
     * @param date
     * @return
     */
    public static int weekOfYear(Date date) {
        return getCalendar(date).get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 返回上午还是下午
     *
     * @param date 日期
     * @return int
     */
    public static int amPm(Date date) {
        return getCalendar(date).get(Calendar.AM_PM);
    }

    /**
     * 返回这一天的第几个小时
     *
     * @param date 日期
     * @return int
     */
    public static int hourOfDay(Date date) {
        return getCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回年份
     *
     * @param date 日期
     * @return int
     */
    public static int year(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date 日期
     * @return int
     */
    public static int month(Date date) {
        return getCalendar(date).get(Calendar.MONTH) + 1;
    }

    /**
     * 返回一个月中的第几天
     *
     * @param date 日期
     * @return int
     */
    public static int day(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return int
     */
    public static int hour(Date date) {
        return getCalendar(date).get(Calendar.HOUR);
    }

    /**
     * 返回分钟
     *
     * @param date 日期
     * @return int
     */
    public static int minute(Date date) {
        return getCalendar(date).get(Calendar.MINUTE);
    }

    /**
     * 返回秒数
     *
     * @param date 日期
     * @return int
     */
    public static int second(Date date) {
        return getCalendar(date).get(Calendar.SECOND);
    }

    /**
     * 返回  年份+月份
     *
     * @param date 日期
     * @return int
     */
    public static Date yearMonth(Date date) {
        long result = ((date.getTime() + ZONE_TIME) / PER_DAY) * PER_DAY - ZONE_TIME;
        return new Date(result - (day(date) - 1) * PER_DAY);
    }

    /**
     * 返回 年份+月份+日期
     *
     * @param date 日期
     * @return int
     */
    public static Date yearMonthDay(Date date) {
        return new Date(((date.getTime() + ZONE_TIME) / PER_DAY) * PER_DAY - ZONE_TIME);
    }

    /**
     * 返回  小时+分钟+秒数
     *
     * @param date 日期
     * @return int
     */
    public static Date hourMinuteSecond(Date date) {
        long result = ((date.getTime() + ZONE_TIME) / PER_DAY) * PER_DAY - ZONE_TIME;
        return new Date(date.getTime() - result - ZONE_TIME);
    }

    /**
     * 增加天数
     *
     * @param date  日期
     * @param value 正数增加，负数减少
     * @return Date
     */
    public static Date addDay(Date date, int value) {
        return new Date(date.getTime() + value * PER_DAY);
    }

    /**
     * 增加小时
     *
     * @param date  日期
     * @param value 正数增加，负数减少
     * @return Date
     */
    public static Date addHour(Date date, int value) {
        return new Date(date.getTime() + value * PER_HOUR);
    }

    /**
     * 增加分钟
     *
     * @param date  日期
     * @param value 正数增加，负数减少
     * @return Date
     */
    public static Date addMinute(Date date, int value) {
        return new Date(date.getTime() + value * PER_MINUTE);
    }

    /**
     * 增加秒数
     *
     * @param date  日期
     * @param value 正数增加，负数减少
     * @return Date
     */
    public static Date addSeconds(Date date, int value) {
        return new Date(date.getTime() + value * PER_SECOND);
    }

    /**
     * 判断after相差before多少天
     *
     * @param before
     * @param after
     * @return
     */
    public static int differentDays(long before, long after) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(before);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(after);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) { // 非同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) { // 闰年
                    timeDistance += 366;
                } else { //不是闰年
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        }
        return day2 - day1;
    }

    /**
     * 判断after相差before多少周
     *
     * @param before
     * @param after
     * @return
     */
    public static int differentWeeks(long before, long after) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(before);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(after);
        int week1 = cal1.get(Calendar.WEEK_OF_YEAR);
        int week2 = cal2.get(Calendar.WEEK_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        int weeks = week2 - week1;
        for (int i = 0; i < year2 - year1; i++) {
            weeks += cal1.getActualMaximum(Calendar.WEEK_OF_YEAR);
            cal1.add(Calendar.YEAR, 1);
        }
        return weeks;
    }

    /**
     * 判断after相差before多少月
     *
     * @param before
     * @param after
     * @return
     */
    public static int differentMonths(long before, long after) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(before);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(after);
        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        return 12 * (year2 - year1) + (month2 - month1);
    }
}
