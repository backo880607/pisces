package com.pisces.core.primary.expression.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.expression.ExpressionException;
import org.springframework.util.StringUtils;

import com.pisces.core.annotation.ELFunction;
import com.pisces.core.annotation.ELParm;
import com.pisces.core.entity.DateDur;
import com.pisces.core.entity.MultiEnum;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.utils.DateUtils;
import com.pisces.core.utils.DoubleUtils;
import com.pisces.core.utils.EntityUtils;

import ch.qos.logback.core.util.Duration;

public class BaseFunction {
	public static PROPERTY_TYPE getType(Object arg) {
		return EntityUtils.getPropertyType(arg.getClass());
	}
	
	public static Class<?> plusClass(Class<?> arg1, Class<?> arg2) {
		switch (EntityUtils.getPropertyType(arg1)) {
		case LONG: return longPlusClass(arg2);
		case DOUBLE: return doublePlusClass(arg2);
		case DATE_TIME: return dateTimePlusClass(arg2);
		case DURATION: return durationPlusClass(arg2);
		case STRING: return stringPlusClass(arg2);
		default: break;
		}
		throw new ExpressionException("");
	}
	private static Class<?> longPlusClass(Class<?> arg) {
		switch (EntityUtils.getPropertyType(arg)) {
		case LONG: return Long.class;
		case DOUBLE: return Double.class;
		default: break;
		}
		throw new ExpressionException("");
	}
	private static Class<?> doublePlusClass(Class<?> arg) {
		switch (EntityUtils.getPropertyType(arg)) {
		case LONG:
		case DOUBLE: return Double.class;
		default: break;
		}
		throw new ExpressionException("");
	}
	private static Class<?> dateTimePlusClass(Class<?> arg) {
		switch (EntityUtils.getPropertyType(arg)) {
		case DURATION: return Date.class;
		default: break;
		}
		throw new ExpressionException("");
	}
	private static Class<?> durationPlusClass(Class<?> arg) {
		switch (EntityUtils.getPropertyType(arg)) {
		case DURATION: return DateDur.class;
		case DATE_TIME: return Date.class;
		default: break;
		}
		
		throw new ExpressionException("");
	}
	private static Class<?> stringPlusClass(Class<?> arg) {
		switch (EntityUtils.getPropertyType(arg)) {
		case STRING: return String.class;
		default: break;
		}
		throw new ExpressionException("");
	}
	
	public static Object plus(Object arg1, Object arg2) {
        switch (getType(arg1)) {
        case LONG: return longPlus((Long)arg1, arg2);
        case DOUBLE: return doublePlus((Double) arg1, arg2);
        case DATE_TIME: return dateTimePlus((Date) arg1, arg2);
        case DURATION: return durationPlus((DateDur)arg1, arg2);
        case STRING: return stringPlus((String)arg1, arg2);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Object longPlus(Long arg1, Object arg2) {
        switch (getType(arg2)) {
        case LONG: return arg1 + (Long) arg2;
        case DOUBLE: return arg1 + (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Double doublePlus(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 + (Long) arg2;
        case DOUBLE: return arg1 + (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Date dateTimePlus(Date arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case DURATION: return new Date(arg1.getTime() + ((DateDur)arg2).getTime() * DateUtils.PER_SECOND);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Object durationPlus(DateDur arg1, Object arg2) {
    	switch (getType(arg2.getClass())) {
        case DURATION: return new DateDur(arg1.getTime() + ((DateDur)arg2).getTime());
        case DATE_TIME: return new Date(arg1.getTime() * DateUtils.PER_SECOND + ((Date)arg2).getTime());
        default: break;
        }
    	throw new ExpressionException("");
    }
    private static String stringPlus(String arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
            case STRING: return arg1 + (String) arg2;
            default: break;
        }
        throw new ExpressionException("");
    }
    
    public static Class<?> minusClass(Class<?> arg1, Class<?> arg2) {
    	switch (getType(arg1.getClass())) {
        case LONG: return longMinusClass(arg2);
        case DOUBLE: return doubleMinusClass(arg2);
        case DATE_TIME: return dateTimeMinusClass(arg2);
        case DURATION: return durationMinusClass(arg2);
        default: break;
        }
    	throw new ExpressionException("");
    }
    private static Class<?> longMinusClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case LONG: return Long.class;
    	case DOUBLE: return Double.class;
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    private static Class<?> doubleMinusClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
		case LONG:
		case DOUBLE: return Double.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> dateTimeMinusClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case DURATION: return Date.class;
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    private static Class<?> durationMinusClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case DURATION: return DateDur.class;
    	case DATE_TIME: return Date.class;
    	default: break;
    	}
    	throw new ExpressionException("");
    }

    public static Object minus(Object arg1, Object arg2) {        
        switch (getType(arg1.getClass())) {
        case LONG: return longMinus((Long)arg1, arg2);
        case DOUBLE: return doubleMinus((Double)arg1, arg2);
        case DATE_TIME: return dateTimeMinus((Date)arg1, arg2);
        case DURATION: return durationMinus((DateDur)arg1, arg2);
        default: break;
        }

        throw new ExpressionException("");
    }
    private static Object longMinus(Long arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 - (Long) arg2;
        case DOUBLE: return arg1 - (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Double doubleMinus(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 - (Long) arg2;
        case DOUBLE: return arg1 - (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Object dateTimeMinus(Date arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case DURATION: return new Date(arg1.getTime() - ((DateDur) arg2).getTime() * DateUtils.PER_SECOND);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Object durationMinus(DateDur arg1, Object arg2) {
    	switch (getType(arg2.getClass())) {
    	case DURATION: return new DateDur(arg1.getTime() - ((DateDur) arg2).getTime());
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    
    public static Class<?> multiplyClass(Class<?> arg1, Class<?> arg2) {
    	switch (EntityUtils.getPropertyType(arg1)) {
		case LONG: return longMultiplyClass(arg2);
		case DOUBLE: return doubleMultiplyClass(arg2);
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> longMultiplyClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
		case LONG: return Long.class;
		case DOUBLE: return Double.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> doubleMultiplyClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case LONG:
    	case DOUBLE: return Double.class;
    	default: break;
    	}
    	throw new ExpressionException("");
    }

    public static Object multiply(Object arg1, Object arg2) {
        switch (getType(arg1.getClass())) {
        case LONG: return longMultiply((Long)arg1, arg2);
        case DOUBLE: return doubleMultiply((Double) arg1, arg2);
        default: break;
        }

        throw new ExpressionException("");
    }
    private static Object longMultiply(Long arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 * (Long) arg2;
        case DOUBLE: return arg1 * (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Object doubleMultiply(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 * (Long) arg2;
        case DOUBLE: return arg1 * (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    
    public static Class<?> divisionClass(Class<?> arg1, Class<?> arg2) {
    	switch (EntityUtils.getPropertyType(arg1)) {
		case LONG: return longDivisionClass(arg2);
		case DOUBLE: return doubleDivisionClass(arg2);
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> longDivisionClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
		case LONG: return Long.class;
		case DOUBLE: return Double.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> doubleDivisionClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
		case LONG:
		case DOUBLE: return Double.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    
    public static Object division(Object arg1, Object arg2) {
        switch (getType(arg1.getClass())) {
        case LONG: return longDivision((Long)arg1, arg2);
        case DOUBLE: return doubleDivision((Double) arg1, arg2);
        default: break;
        }

        throw new ExpressionException("");
    }
    private static Object longDivision(Long arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 / (Long) arg2;
        case DOUBLE: return arg1 / (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static double doubleDivision(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 / (Long) arg2;
        case DOUBLE: return arg1 / (Double)arg2;
        default: break;
        }
        throw new ExpressionException("");
    }
    
    public static Class<?> greaterClass(Class<?> arg1, Class<?> arg2) {
    	switch (EntityUtils.getPropertyType(arg1)) {
    	case LONG: return longGreaterClass(arg2);
    	case DOUBLE: return doubleGreaterClass(arg2);
    	case DATE_TIME: return dateTimeGreaterClass(arg2);
    	case DURATION: return durationGreaterClass(arg2);
    	case ENUM: return enumGreaterClass(arg2);
    	case STRING: return  stringGreaterClass(arg2);
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    private static Class<?> longGreaterClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case LONG:
        case DOUBLE:
        case DURATION:
        case ENUM:
        case STRING: return Boolean.class;
        default: break;
    	}
    	throw new ExpressionException("");
    }
    private static Class<?> doubleGreaterClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case LONG:
        case DOUBLE:
        case DURATION:
        case ENUM:
        case STRING: return Boolean.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> dateTimeGreaterClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case DATE_TIME:
    	case STRING: return Boolean.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> durationGreaterClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case LONG:
    	case DOUBLE:
    	case DURATION:
    	case STRING: return Boolean.class;
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    private static Class<?> enumGreaterClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
        case LONG:
        case DOUBLE:
        case ENUM:
        case STRING: return Boolean.class;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Class<?> stringGreaterClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
        case LONG:
        case DOUBLE:
        case DATE_TIME:
        case DURATION:
        case ENUM:
        case STRING: return Boolean.class;
        default: break;
        }
        throw new ExpressionException("");
    }

    public static Object greater(Object arg1, Object arg2) {
        switch (getType(arg1.getClass())) {
        case LONG: return longGreater((Long)arg1, arg2);
        case DOUBLE: return doubleGreater((Double) arg1, arg2);
        case DATE_TIME: return dateTimeGreater((Date) arg1, arg2);
        case DURATION: return durationGreater((DateDur)arg1, arg2);
        case ENUM: return enumGreater((Enum<?>) arg1, arg2);
        case STRING: return stringGreater((String) arg1, arg2);
        default: break;
        }

        throw new ExpressionException("");
    }
    private static boolean longGreater(Long arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 > (Long) arg2;
        case DOUBLE: return DoubleUtils.isLess((Double) arg2, arg1);
        case DURATION: return arg1 > ((DateDur)arg2).getTime();
        case ENUM: return arg1 > ((Enum<?>)arg2).ordinal();
        case STRING: return arg1 > Long.valueOf((String) arg2);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean doubleGreater(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return DoubleUtils.isLess((Long) arg2, arg1);
        case DOUBLE: return DoubleUtils.isLess((Double) arg2, arg1);
        case DURATION: return DoubleUtils.isGreater(arg1, ((DateDur)arg2).getTime());
        case ENUM: return DoubleUtils.isGreater(arg1, ((Enum<?>)arg2).ordinal());
        case STRING: return DoubleUtils.isLess(Double.valueOf((String) arg2), arg1);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean dateTimeGreater(Date arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case DATE_TIME: return arg1.getTime() > ((Date) arg2).getTime();
        case STRING:
            try {
                Date temp = DateUtils.parse((String)arg2);
                return arg1.getTime() > temp.getTime();
            } catch (ParseException e) {
            }
            break;
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean durationGreater(DateDur dur, Object arg2) {
    	switch (getType(arg2)) {
    	case LONG: return dur.getTime() > (long)arg2;
    	case DOUBLE: return DoubleUtils.isGreater(dur.getTime(), (double)arg2);
    	case DURATION: return dur.getTime() > ((DateDur)arg2).getTime();
    	case STRING: return dur.getTime() > new DateDur((String)arg2).getTime();
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    @SuppressWarnings("unchecked")
	private static boolean enumGreater(Enum<?> arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1.ordinal() > (Long)arg2;
        case DOUBLE: return DoubleUtils.isGreater(arg1.ordinal(), (double)arg2);
        case ENUM: return arg1.ordinal() > ((Enum<?>) arg2).ordinal();
        case STRING:
        	Enum<?> target = Enum.valueOf(arg1.getClass(), (String)arg2);
        	return arg1.ordinal() > target.ordinal();
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean stringGreater(String arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return Long.valueOf(arg1) > (long)arg2;
        case DOUBLE: return DoubleUtils.isGreater(Double.valueOf(arg1), (double)arg2);
        case DATE_TIME: break;
        case DURATION: return new DateDur(arg1).getTime() > ((DateDur)arg2).getTime();
        case ENUM: break;
        case STRING: return arg1.compareTo((String) arg2) > 0;
        default: break;
        }
        throw new ExpressionException("");
    }
    
    public static Class<?> greaterEqualClass(Class<?> arg1, Class<?> arg2) {
    	return greaterClass(arg1, arg2);
    }

    public static Object greaterEqual(Object arg1, Object arg2) {
        switch (getType(arg1.getClass())) {
        case LONG: return longGreaterEqual((Long)arg1, arg2);
        case DOUBLE: return doubleGreaterEqual((Double) arg1, arg2);
        case DATE_TIME: return dateTimeGreaterEqual((Date) arg1, arg2);
        case ENUM: return enumGreaterEqual((Enum<?>) arg1, arg2);
        case STRING: return stringGreaterEqual((String) arg1, arg2);
        default: break;
        }

        throw new ExpressionException("");
    }
    private static boolean longGreaterEqual(Long arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1 >= (Long) arg2;
        case DOUBLE: return DoubleUtils.isLessEqual((Double) arg2, arg1);
        case STRING: return arg1 >= Long.valueOf((String)arg2);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean doubleGreaterEqual(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return DoubleUtils.isLessEqual((Long) arg2, arg1);
        case DOUBLE: return DoubleUtils.isLessEqual((Double) arg2, arg1);
        case STRING: return DoubleUtils.isLessEqual(Double.valueOf((String)arg2), arg1);
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean dateTimeGreaterEqual(Date arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case DATE_TIME: return arg1.getTime() >= ((Date) arg2).getTime();
        case STRING:
        	try {
        		Date temp = DateUtils.parse((String)arg2);
        		return arg1.getTime() >= temp.getTime();
        	} catch (ParseException e) {
        		
        	}
        	break;
        default: break;
        }
        throw new ExpressionException("");
    }
    @SuppressWarnings("unchecked")
	private static boolean enumGreaterEqual(Enum<?> arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1.ordinal() >= (Long)arg2;
        case ENUM: return arg1.ordinal() >= ((Enum<?>) arg2).ordinal();
        case STRING:
        	Enum<?> target = Enum.valueOf(arg1.getClass(), (String)arg2);
        	return arg1.ordinal() >= target.ordinal();
        default: break;
        }
        throw new ExpressionException("");
    }
    private static Object stringGreaterEqual(String arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case STRING: return arg1.compareTo((String) arg2) >= 0;
        default: break;
        }
        throw new ExpressionException("");
    }
    
    public static Class<?> lessClass(Class<?> arg1, Class<?> arg2) {
    	return greaterClass(arg1, arg2);
    }

    public static Object less(Object arg1, Object arg2) {
    	return !(boolean)greaterEqual(arg1, arg2);
    }
    
    public static Class<?> lessEqualClass(Class<?> arg1, Class<?> arg2) {
    	return greaterClass(arg1, arg2);
    }

    public static Object lessEqual(Object arg1, Object arg2) {
    	return !(boolean)greater(arg1, arg2);
    }
    
    public static Class<?> equalClass(Class<?> arg1, Class<?> arg2) {
    	switch (EntityUtils.getPropertyType(arg1)) {
		default:
			break;
		}
    	throw new ExpressionException("");
    }

    public static Object equal(Object arg1, Object arg2) {
        switch (getType(arg1.getClass())) {
        case LONG: return longEqual((Long)arg1, arg2);
        case DOUBLE: return doubleEqual((Double) arg1, arg2);
        case DATE_TIME: return dateTimeEqual((Date) arg1, arg2);
        case ENUM: return enumEqual((Enum<?>) arg1, arg2);
        case STRING: return stringEqual((String) arg1, arg2);
        default: break;
        }

        throw new ExpressionException("");
    }
    private static boolean longEqual(Long arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1.equals(arg2);
        case DOUBLE: return DoubleUtils.isEqual(arg1, (Double) arg2);
        case DURATION: return arg1 == ((DateDur)arg2).getTime();
        case ENUM: return arg1 == ((Enum<?>)arg2).ordinal();
        case MULTI_ENUM: return arg1 == ((MultiEnum<?>)arg2).getValue();
        case STRING: return arg1.equals(Long.valueOf((String)arg2));
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean doubleEqual(Double arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return DoubleUtils.isEqual(arg1, (Long) arg2);
        case DOUBLE: return DoubleUtils.isEqual(arg1, (Double) arg2);
        case DURATION: return DoubleUtils.isEqual(arg1, ((DateDur)arg2).getTime());
        case ENUM: return DoubleUtils.isEqual(arg1, ((Enum<?>)arg2).ordinal());
        case MULTI_ENUM: return DoubleUtils.isEqual(arg1, ((MultiEnum<?>)arg2).getValue());
        case STRING: return DoubleUtils.isEqual(arg1, Double.valueOf((String)arg2));
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean dateTimeEqual(Date arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case DATE_TIME: return arg1.getTime() == ((Date) arg2).getTime();
        case STRING:
                try {
                    Date temp = DateUtils.parse((String)arg2);
                    return arg1.getTime() == temp.getTime();
                } catch (ParseException e) {
                }
                break;
        default: break;
        }
        throw new ExpressionException("");
    }
    @SuppressWarnings("unchecked")
	private static boolean enumEqual(Enum<?> arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return arg1.ordinal() == (Long)arg2;
        case DOUBLE: return DoubleUtils.isEqual(arg1.ordinal(), (double)arg2);
        case ENUM: return arg1.ordinal() == ((Enum<?>) arg2).ordinal();
        case STRING:
        	Enum<?> target = Enum.valueOf(arg1.getClass(), (String)arg2);
        	return arg1.ordinal() == target.ordinal();
        default: break;
        }
        throw new ExpressionException("");
    }
    private static boolean stringEqual(String arg1, Object arg2) {
        switch (getType(arg2.getClass())) {
        case LONG: return Long.valueOf(arg1) == (long)arg2;
        case DOUBLE: return DoubleUtils.isEqual(Double.valueOf(arg1), (double)arg2);
        case ENUM: break;
        case STRING: return arg1.compareTo((String) arg2) == 0;
        default: break;
        }
        throw new ExpressionException("");
    }
    
    public static Class<?> notEqualClass(Class<?> arg1, Class<?> arg2) {
    	return equalClass(arg1, arg2);
    }
    
    public static Object notEqual(Object arg1, Object arg2) {
    	return !(boolean)equal(arg1, arg2);
    }
    
    public static Class<?> notClass(Class<?> arg1, Class<?> arg2) {
    	throw new ExpressionException("");
    }
    
    public static Object not(Object arg1, Object arg2) {
    	return false;
    }
    
    public static Class<?> andClass(Class<?> arg1, Class<?> arg2) {
    	switch (EntityUtils.getPropertyType(arg1)) {
		case BOOLEAN: return boolAndClass(arg2);
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> boolAndClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
		case BOOLEAN: return Boolean.class;
		default: break;
		}
    	throw new ExpressionException("");
    }
    
    public static Object and(Object arg1, Object arg2) {
    	return true;
    }
    
    public static Class<?> orClass(Class<?> arg1, Class<?> arg2) {
    	switch (EntityUtils.getPropertyType(arg1)) {
		case BOOLEAN: return boolOrClass(arg2);
		default: break;
		}
    	throw new ExpressionException("");
    }
    private static Class<?> boolOrClass(Class<?> arg) {
    	switch (EntityUtils.getPropertyType(arg)) {
    	case BOOLEAN: return Boolean.class;
    	default: break;
    	}
    	throw new ExpressionException("");
    }
    
    public static Object or(Object arg1, Object arg2) {
    	return true;
    }

    @ELFunction
	@SuppressWarnings("unchecked")
	public static String funToStr(Object value, String format) {
		String result = null;
		if (StringUtils.isEmpty(format)) {
			switch (getType(value)) {
			case BOOLEAN:
				result = (Boolean)value ? "1" : "0";
				break;
			case LONG:
			case DOUBLE:
				result = value.toString();
				break;
			case DATE_TIME:
				result = DateUtils.format((Date)value);
				break;
			case ENUM:
				result = ((Enum<?>)value).name();
				break;
			case MULTI_ENUM:
				result = ((MultiEnum<? extends Enum<?>>)value).toString();
			case STRING:
				result = (String) value;
				break;
			default:
				break;
			}
		} else if (getType(value) == PROPERTY_TYPE.DATE_TIME) {
			SimpleDateFormat formater = new SimpleDateFormat(format);
			return formater.format((String)value);
		}
		
		return result;
	}
	
    @ELFunction
	@SuppressWarnings("unchecked")
	public static Long funToInt(@ELParm(clazz = {Boolean.class, Date.class, Double.class, Duration.class, Enum.class, Long.class, String.class}) Object value) {
		Long result = null;
		switch (getType(value)) {
			case BOOLEAN:
			case LONG:
			case DOUBLE:
				result = (Long)value;
				break;
			case DATE_TIME:
				result = ((Date)value).getTime();
				break;
			case ENUM:
				result = (long)((Enum<?>)value).ordinal();
				break;
			case MULTI_ENUM:
				result = (long) ((MultiEnum<? extends Enum<?>>)value).getValue();
				break;
			case STRING:
				result = Long.valueOf((String) value);
				break;
			default:
				break;
		}
		return result;
	}
	
    @ELFunction
	@SuppressWarnings("unchecked")
	public static Double funToDouble(@ELParm(clazz = {Boolean.class, Date.class, Double.class, Duration.class, Enum.class, Long.class, String.class}) Object value) {
		Double result = null;
		switch (getType(value)) {
		case BOOLEAN:
		case LONG:
		case DOUBLE:
			result = (Double)value;
			break;
		case DATE_TIME:
			result = (double)((Date)value).getTime();
			break;
		case ENUM:
			result = (double)((Enum<?>)value).ordinal();
			break;
		case MULTI_ENUM:
			result = (double) ((MultiEnum<? extends Enum<?>>)value).getValue();
			break;
		case STRING:
			result = Double.valueOf((String) value);
			break;
		default:
			break;
		}
		return result;
	}
}
