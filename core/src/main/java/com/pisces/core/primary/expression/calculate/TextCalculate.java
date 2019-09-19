package com.pisces.core.primary.expression.calculate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueText;

public class TextCalculate implements Calculate {
	private ValueText value;
	
	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		return value;
	}
	
	public int Parse(String str, int index) {
		int temp = ++index;
		while (index < str.length()) {
			if (str.charAt(index) == '\'') {
				value = new ValueText(str.substring(temp, index));
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
