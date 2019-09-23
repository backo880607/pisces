package com.pisces.core.primary.expression.value;

import java.util.Date;

public class ValueHelp {
	public static ValueAbstract get(Object value) {
		return null;
	}
	
	public static ValueAbstract get(Class<?> clazz) {
		if (clazz == Long.class) {
			return new ValueInt(0);
		} else if (clazz == Double.class) {
			return new ValueDouble(0.0);
		} else if (clazz == String.class) {
			return new ValueText("");
		} else if (clazz == Boolean.class) {
			return new ValueBoolean(false);
		} else if (clazz == Date.class) {
			return new ValueDateTime("");
		} else if (clazz == Enum.class) {
			return new ValueEnum(null);
		}
		throw new UnsupportedOperationException();
	}
}
