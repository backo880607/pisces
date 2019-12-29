package com.pisces.platform.core.primary.expression.function;

import com.pisces.platform.core.annotation.ELFunction;
import com.pisces.platform.core.annotation.ELParm;
import com.pisces.platform.core.entity.DateDur;
import com.pisces.platform.core.utils.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

class DateTimeFunction {

    protected DateTimeFunction() {}

    static void register(FunctionManager manager) {
    }

    /**
     * 日期函数，年，月，日
     *
     * @param param
     * @return
     * @throws ParseException
     */
    @ELFunction()
    static Date funDate(@ELParm(clazz = {String.class, Date.class}) Object param) throws ParseException {
        Date date = param.getClass() == String.class ? DateUtils.parse((String) param) : (Date) param;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将时分秒,毫秒域清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 时间函数，时，分，秒
     *
     * @param param
     * @return
     * @throws ParseException
     */
    @ELFunction
    static Date funTime(@ELParm(clazz = {String.class, Date.class}) Object param) throws ParseException {
        Date date = param.getClass() == String.class ? DateUtils.parse((String) param) : (Date) param;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将年月日域清零
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 返回日期格式的当前日期
     *
     * @param date
     * @return
     */
    @ELFunction
    static Long funGetDateTime(Date date) {
        return date.getTime();
    }

    /**
     * 返回日期的年份值，介于1970-9999之间。
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funYear(Date date) {
        return DateUtils.year(date);
    }

    /**
     * 返回月份值，介于1（一月）到12（十二月）之间。
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funMonth(Date date) {
        return DateUtils.month(date);
    }

    /**
     * 返回一个月中的第几天的数值，介于1到31之间。
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funDay(Date date) {
        return DateUtils.day(date);
    }

    /**
     * 返回小时数值，介于0到23之间
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funHour(Date date) {
        return DateUtils.hour(date);
    }

    /**
     * 返回分钟数值，介于0到59之间。
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funMinute(Date date) {
        return DateUtils.minute(date);
    }

    /**
     * 返回秒数值，介于0到59之间。
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funSecond(Date date) {
        return DateUtils.second(date);
    }

    /**
     * 返回日期时间格式的当前日期和时间
     *
     * @return
     */
    @ELFunction
    static Date funNow() {
        return new Date();
    }

    /**
     * 返回代表一周中的第几天的数值，介于1到7之间
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funDayOfWeek(Date date) {
        return DateUtils.dayOfWeek(date);
    }

    /**
     * 返回一年中的周数
     *
     * @param date
     * @return
     */
    @ELFunction
    static Integer funWeekOfYear(Date date) {
        return DateUtils.weekOfYear(date);
    }

    /**
     * 将时间段格式化成可读的样式，比如1H30S
     *
     * @param dur
     * @return
     */
    @ELFunction
    static String funFormatDuration(int dur) {
        StringBuffer strExtraString = new StringBuffer();
        int i = dur / 86400;
        if (i > 0) {
            strExtraString.append(i).append("D");
        }
        dur -= i * 86400;
        i = dur / 3600;
        if (i > 0) {
            strExtraString.append(i).append("H");
        }
        dur -= i * 3600;
        i = dur / 60;
        if (i > 0) {
            strExtraString.append(i).append("M");
        }
        dur -= i * 60;
        if (dur > 0) {
            strExtraString.append(dur).append("S");
        }
        return strExtraString.toString();
    }

    @ELFunction
    static DateDur funDuration(String param) {
        return new DateDur(param);
    }
}
