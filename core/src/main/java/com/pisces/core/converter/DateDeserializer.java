package com.pisces.core.converter;

import java.text.ParseException;
import java.util.Date;

import com.pisces.core.entity.BaseDeserializer;
import com.pisces.core.entity.Property;
import com.pisces.core.utils.DateUtils;

public class DateDeserializer extends BaseDeserializer<Date> {
	
	@Override
	public Date deserialize(Property property, String value) {
		try {
			return DateUtils.parse(value, DateUtils.DATE_FORMAT);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
