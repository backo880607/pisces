package com.pisces.core.primary.expression.calculate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueDateTime;

public class DateTimeCalculate implements Calculate {
	private static final SimpleDateFormat DATETIME_FORMATOR = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private Date value;
	
	@Override
	public ValueAbstract GetValue(EntityObject entity) {
		return new ValueDateTime(this.value);
	}
	
	@Override
	public int Parse(String str, int index) {
		int temp = ++index;
		while (index < str.length()) {
			if (str.charAt(index) == '#') {
				try {
					value = DATETIME_FORMATOR.parse(str.substring(temp, index));
				} catch (ParseException e) {
					e.printStackTrace();
				}
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
