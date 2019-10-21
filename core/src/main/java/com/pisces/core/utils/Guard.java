package com.pisces.core.utils;

import java.util.Date;

import com.pisces.core.entity.DateDur;

public class Guard {
	public static byte value(Byte arg) {
		return arg != null ? arg : 0;
	}
	
	public static byte value(Byte arg, byte def) {
		return arg != null ? arg : def;
	}
	
	public static boolean value(Boolean arg) {
		return arg != null ? arg : false;
	}
	
	public static boolean value(Boolean arg, boolean def) {
		return arg != null ? arg : def;
	}
	
	public static short value(Short arg) {
		return arg != null ? arg : 0;
	}
	
	public static short value(Short arg, short def) {
		return arg != null ? arg : def;
	}
	
	public static int value(Integer arg) {
		return arg != null ? arg : 0;
	}
	
	public static int value(Integer arg, int def) {
		return arg != null ? arg : def;
	}
	
	public static long value(Long arg) {
		return arg != null ? arg : 0;
	}
	
	public static long value(Long arg, long def) {
		return arg != null ? arg : def;
	}
	
	public static float value(Float arg) {
		return arg != null ? arg : 0.0f;
	}
	
	public static float value(Float arg, float def) {
		return arg != null ? arg : def;
	}
	
	public static double value(Double arg) {
		return arg != null ? arg : 0.0f;
	}
	
	public static double value(Double arg, double def) {
		return arg != null ? arg : def;
	}
	
	public static char value(Character arg) {
		return arg != null ? arg.charValue() : 0;
	}
	
	public static char value(Character arg, char def) {
		return arg != null ? arg.charValue() : def;
	}
	
	public static String value(String arg) {
		return arg != null ? arg : "";
	}
	
	public static String value(String arg, String def) {
		return arg != null ? arg : def;
	}
	
	public static long value(Date arg) {
		return arg != null ? arg.getTime() : DateUtils.INVALID.getTime();
	}
	
	public static long value(Date arg, long def) {
		return arg != null ? arg.getTime() : def;
	}
	
	public static Object value(Object arg, Class<?> cls) {
		if (arg != null) {
			return arg;
		}
		
		if (cls == Integer.class) {
			return (Integer)0;
		} else if (cls == Long.class) {
			return (Long)0l;
		} else if (cls == Float.class) {
			return (Float)0.0f;
		} else if (cls == Double.class) {
			return (Double)0.0d;
		} else if (cls == String.class) {
			return "";
		} else if (cls == Date.class) {
			return DateUtils.INVALID;
		} else if (cls == Short.class) {
			Short result = 0;
			return result;
		} else if (cls == Byte.class) {
			Byte result = 0;
			return result;
		} else if (cls == Character.class) {
			return Character.valueOf((char)0);
		} else if (cls == DateDur.class) {
			return DateDur.INVALID;
		}
		
		return arg;
	}
}
