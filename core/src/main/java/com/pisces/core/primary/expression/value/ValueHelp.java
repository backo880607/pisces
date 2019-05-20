package com.pisces.core.primary.expression.value;

public class ValueHelp {
	
	public static ValueAbstract get(Object value) {
		return new ValueNull(Type.None);
	}
}
