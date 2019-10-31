package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;

public class TextCalculate implements Calculate {
	private String value;
	
	@Override
	public Object GetValue(EntityObject entity) {
		return value;
	}
	
	public int Parse(String str, int index) {
		int temp = ++index;
		while (index < str.length()) {
			if (str.charAt(index) == '\'') {
				value = str.substring(temp, index);
				return ++index;
			}
			++index;
		}

		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return String.class;
	}
}
