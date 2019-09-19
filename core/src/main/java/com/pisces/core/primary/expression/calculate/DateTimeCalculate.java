package com.pisces.core.primary.expression.calculate;

import java.util.Date;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueDateTime;

public class DateTimeCalculate implements Calculate {
	private ValueDateTime value;
	
	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		return this.value;
	}
	
	@Override
	public int Parse(String str, int index) {
		int temp = ++index;
		while (index < str.length()) {
			if (str.charAt(index) == '#') {
				value = new ValueDateTime(str.substring(temp, index));
				return ++index;
			}
			++index;
		}
		
		return -1;
	}

	@Override
	public Class<?> getReturnClass() {
		return Date.class;
	}
}
