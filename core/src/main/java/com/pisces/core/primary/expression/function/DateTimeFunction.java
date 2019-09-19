package com.pisces.core.primary.expression.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.pisces.core.entity.DateDur;
import com.pisces.core.primary.expression.value.ValueDateTime;
import com.pisces.core.utils.DateUtils;

/**
 * 日期与时间类函数
 * @author niuhaitao
 *
 */
class DateTimeFunction {
	static void register(FunctionManager manager) {
	}
	/**
	 * 日期函数，年，月，日
	 * @param params
	 * @return
	 */
	static Date funDate(Object param) {
		Date date = null;
		if (param.getClass() == String.class) {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = formater.parse((String)param);
			} catch (ParseException e) {
				return null;
			}
		} else {
			date = ((ValueDateTime)param).value;
		}
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 将时分秒,毫秒域清零
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
		
		/*int year = (int)((ValueInt)params.get(0)).value;
		int month = (int)((ValueInt)params.get(1)).value;
		int day = (int)((ValueInt)params.get(2)).value;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return new ValueDateTime(calendar.getTime());*/
	}
	
	/**
	 * 时间函数，时，分，秒
	 * @param params
	 * @return
	 */
	static Date funTime(Object param) {
		Date date = null;
		if (param.getClass() == String.class) {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = formater.parse((String)param);
			} catch (ParseException e) {
				return null;
			}
		} else {
			date = (Date)param;
		}
		
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 将年月日域清零
		calendar.set(Calendar.YEAR, 0);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
		
		/*int hour = (int)((ValueInt)params.get(0)).value;
		int minute = (int)((ValueInt)params.get(1)).value;
		int second = (int)((ValueInt)params.get(2)).value;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 0, 1, hour, minute, second);
		return new ValueDateTime(calendar.getTime());*/
	}
	
	/**
	 * 返回日期格式的当前日期
	 * @param params
	 * @return
	 */
	static long funGetDateTime(Date date) {
		return date.getTime();
	}
	
	/**
	 * 返回日期的年份值，介于1970-9999之间。
	 * @param params
	 * @return
	 */
	static long funYear(Date date) {
		return DateUtils.year(date);
	}
	
	/**
	 * 返回月份值，介于1（一月）到12（十二月）之间。
	 * @param params
	 * @return
	 */
	static long funMonth(Date date) {
		return DateUtils.month(date);
	}
	
	/**
	 * 返回一个月中的第几天的数值，介于1到31之间。
	 * @param params
	 * @return
	 */
	static long funDay(Date date) {
		return DateUtils.day(date);
	}
	
	/**
	 * 返回小时数值，介于0到23之间
	 * @param params
	 * @return
	 */
	static long funHour(Date date) {
		return DateUtils.hour(date);
	}
	
	/**
	 * 返回分钟数值，介于0到59之间。
	 * @param params
	 * @return
	 */
	static long funMinute(Date date) {
		return DateUtils.minute(date);
	}
	
	/**
	 * 返回秒数值，介于0到59之间。
	 * @param params
	 * @return
	 */
	static long funSecond(Date date) {
		return DateUtils.second(date);
	}
	
	/**
	 * 返回日期时间格式的当前日期和时间
	 * @param params
	 * @return
	 */
	static Date funNow() {
		return new Date();
	}
	
	/**
	 * 返回代表一周中的第几天的数值，介于1到7之间
	 * @param params
	 * @return
	 */
	static long funDayOfWeek(Date date) {
		return DateUtils.dayOfWeek(date);
	}
	
	/**
	 * 返回一年中的周数
	 * @param params
	 * @return
	 */
	static long funWeekOfYear(Date date) {
		return DateUtils.weekOfYear(date);
	}
	
	/**
	 * 返回指定的天数
	 * @param params
	 * @return
	 */
	static Date funYears(long days) {
		return new Date(days * DateUtils.PER_DAY);
	}
	
	/**
	 * 返回指定的天数
	 * @param params
	 * @return
	 */
	static Date funDays(long days) {
		return new Date(days * DateUtils.PER_DAY);
	}
	
	/**
	 * 将时间段格式化成可读的样式，比如1H30S
	 * @param params
	 * @return
	 */
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
	
	static DateDur funDuration(String param) {
		return new DateDur(param);
	}
}
